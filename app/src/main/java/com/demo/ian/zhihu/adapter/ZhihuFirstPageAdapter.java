package com.demo.ian.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ian.zhihu.R;
import com.demo.ian.zhihu.mvp.domain.ZhihuFirstPageList;
import com.demo.ian.zhihu.mvp.domain.ZhihuStory;
import com.demo.ian.zhihu.mvp.domain.ZhihuTop;
import com.demo.ian.zhihu.mvp.interf.OnListFragmentInteract;
import com.demo.ian.zhihu.utils.DateUtil;
import com.demo.ian.zhihu.utils.Imager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016-9-8.
 */
public class ZhihuFirstPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_FOOTER = 3;

    public static int textGrey;
    public static int textDark;
    public List<Object> zhihuStories;
    public List<ZhihuTop> zhihuTops;
    private OnListFragmentInteract listener;
    private ArrayList<View> pages = new ArrayList();

    public ZhihuFirstPageAdapter(OnListFragmentInteract listener) {
        zhihuStories = new ArrayList();
        zhihuTops = new ArrayList();
        this.listener = listener;
    }

    public void OnUpdate(ZhihuFirstPageList json, boolean refresh) {
        if (json == null) return;

        if (refresh) {
            zhihuStories.clear();
            zhihuTops.clear();
        }

        if (json.getStories() != null) {
            this.zhihuStories.add(DateUtil.parseChineseDate(json.getDate()));
            for (ZhihuStory story : json.getStories()) {
                this.zhihuStories.add(story);
            }
        }

        if (json.getTop_stories() != null) {
            this.zhihuTops.clear();
            for (ZhihuTop top : json.getTop_stories()) {
                this.zhihuTops.add(top);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case TYPE_BANNER:
                view = inflater.inflate(R.layout.fragment_news_banner, parent, false);
                return new BannerHolder(view);
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.fragment_news_item, parent, false);
                return new ViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        textGrey = ContextCompat.getColor(context, R.color.darker_gray);
        textDark = ContextCompat.getColor(context, android.support.design.R.color.abc_primary_text_material_light);

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if (zhihuStories.get(position - 1) instanceof String) {
                if (position == 1)
                    viewHolder.header.setText("今日热闻");
                else
                    viewHolder.header.setText((String) zhihuStories.get(position - 1));
                viewHolder.header.setVisibility(View.VISIBLE);
                viewHolder.mItem.setVisibility(View.GONE);
            } else if (zhihuStories.get(position - 1) instanceof ZhihuStory) {
                viewHolder.header.setVisibility(View.GONE);
                viewHolder.mItem.setVisibility(View.VISIBLE);

                viewHolder.zhihuStory = (ZhihuStory) zhihuStories.get(position - 1);
                Imager.load(context, viewHolder.zhihuStory.getImages().get(0), viewHolder.mImage);
                viewHolder.mTitle.setText(viewHolder.zhihuStory.getTitle());
                viewHolder.mTitle.setTextColor(textDark);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onListFragmentInteraction(viewHolder);
                    }
                });
            }
        } else if (holder instanceof BannerHolder) {
            pages.clear();
            for (ZhihuTop top : zhihuTops) {
                View page = LayoutInflater.from(context).inflate(R.layout.card_item_big, null);

                ImageView img = (ImageView) page.findViewById(R.id.story_img);
                Imager.load(context, top.getImage(), img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onListFragmentInteraction(holder);
                    }
                });
                ((BannerHolder) holder).imgs.add(img);

                TextView title = (TextView) page.findViewById(R.id.news_title);
                ((BannerHolder) holder).news_title.add(title);
                title.setText(top.getTitle());

                pages.add(page);
            }

            final BannerHolder bannerHolder = (BannerHolder) holder;
            ((BannerHolder) holder).zhihuTops = zhihuTops;
            bannerHolder.banner.setAdapter(new BannerPagerAdapter(pages));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_BANNER;
            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (zhihuStories == null) {
            return 0;
        } else {
            return zhihuStories.size() + 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView header;
        public final ImageView mImage;
        public final TextView mTitle;
        public final View mItem;
        public ZhihuStory zhihuStory;

        public ViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.story_header);
            mImage = (ImageView) view.findViewById(R.id.story_img);
            mTitle = (TextView) view.findViewById(R.id.news_title);
            mItem = view.findViewById(R.id.news_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

    public class BannerHolder extends RecyclerView.ViewHolder {
        public ViewPager banner;
        public List<ZhihuTop> zhihuTops;
        public List<TextView> news_title = new ArrayList();
        public List<ImageView> imgs = new ArrayList();

        public BannerHolder(View view) {
            super(view);
            banner = (ViewPager) view.findViewById(R.id.vp_banner);
        }
    }

    public class BannerPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public BannerPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
    }
}
