package com.fadong.controller.dto;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 1. 12.
 */
public class PageUpdateDto {
    private String id;
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PageUpdateDto{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
