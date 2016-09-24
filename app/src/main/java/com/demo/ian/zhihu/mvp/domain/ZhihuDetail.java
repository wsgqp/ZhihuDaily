package com.demo.ian.zhihu.mvp.domain;

/*
{
    "body": "<div class=\"main-wrap content-wrap\">........</div>",
    "image_source": "OnInnovation / CC BY-ND",
    "title": "想用基因技术消灭「坏」物种，比尔 · 盖茨引起巨大争议",
    "image": "http://pic3.zhimg.com/e7d9c0649c416e4064e85128628c315a.jpg",
    "share_url": "http://daily.zhihu.com/story/8787377",
    "js": [],
    "ga_prefix": "091316",
    "images": ["http://pic4.zhimg.com/3a14d1c4381cd0558026cd4abdc77d4f.jpg"],
    "type": 0,
    "id": 8787377,
    "css": ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
}
 */
public class ZhihuDetail {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private int id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
