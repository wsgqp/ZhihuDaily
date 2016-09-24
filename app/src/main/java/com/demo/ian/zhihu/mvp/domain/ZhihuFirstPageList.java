package com.demo.ian.zhihu.mvp.domain;

import java.util.List;

/*
{
    "date": "20160914",
    "stories": [
        {
            "images": ["http://pic3.zhimg.com/39c4bdb58b37807690ade38f0976457e.jpg"],
            "type": 0,
            "id": 8792262,
            "ga_prefix": "091414",
            "title": "你看到的射进建筑里的光，可能是建筑师的「心机」"
        },
        {
            "images": ["http://pic1.zhimg.com/52d55a897ca2cb0fc195d70b96b03910.jpg"],
            "type": 0,
            "id": 8794294,
            "ga_prefix": "091413",
            "title": "用脑洞大开的方式，让你明白残障人士的困境"
        }
    ],
    "top_stories": [
        {
            "image": "http://pic2.zhimg.com/1474e1373a06112b324a69b9b320aed1.jpg",
            "type": 0,
            "id": 8793107,
            "ga_prefix": "091408",
            "title": "直播行业等来了牌照，「某某 TV 」可能都要改名了"
        },
        {
            "image": "http://pic3.zhimg.com/11ce34567571640258eef4c976b6b50e.jpg",
            "type": 0,
            "id": 8792199,
            "ga_prefix": "091407",
            "title": "iPhone 7 实际用起来怎么样？这是上手一周后的评测"
        }
    ]
}
 */
public class ZhihuFirstPageList {

    private String date;

    private List<ZhihuStory> stories;

    private List<ZhihuTop> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuStory> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuStory> stories) {
        this.stories = stories;
    }

    public List<ZhihuTop> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuTop> top_stories) {
        this.top_stories = top_stories;
    }
}
