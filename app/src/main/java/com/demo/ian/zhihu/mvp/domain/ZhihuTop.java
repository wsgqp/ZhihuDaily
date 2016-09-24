package com.demo.ian.zhihu.mvp.domain;

/*
        {
            "image": "http://pic3.zhimg.com/240166125517e34c1ee3b925ed011332.jpg",
            "type": 0,
            "id": 8773413,
            "title": "刚刚结束的苹果秋季发布会，要知道的都在这儿了"
        }
 */
public class ZhihuTop {
    private int id;
    private int type;
    private String image;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
