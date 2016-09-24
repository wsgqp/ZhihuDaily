package com.demo.ian.zhihu.mvp.domain;

import java.util.List;

/*
        {
            "title": "被杨永信「成功改造」之后，这些孩子的人生彻底改变了",
            "images": ["http://pic4.zhimg.com/d4bdca2eb3c7034ef88989aad7983c6b.jpg"],
            "type": 0,
            "id": 8774553
        }
 */
public class ZhihuStory {
    private int id;
    private int type;
    private String title;
    private List<String> images;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
