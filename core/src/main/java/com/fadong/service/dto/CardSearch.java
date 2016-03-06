package com.fadong.service.dto;

import org.springframework.data.domain.Pageable;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 6. 23.
 */
public class CardSearch {
    private String id;
    private String category;
    private Pageable pageable;

    public CardSearch(String id, String category, Pageable pageable) {
        this.id = id;
        this.category = category;
        this.pageable = pageable;
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

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
