package com.fadong.service.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 23.
 */
public class CardSearch {
    private String id;
    private String category;
    private String keyword;

    public CardSearch() {
    }

    public CardSearch(String id, String category, String keyword) {
        this.id = id;
        this.category = category;
        this.keyword = keyword;
    }

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "CardSearch{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
