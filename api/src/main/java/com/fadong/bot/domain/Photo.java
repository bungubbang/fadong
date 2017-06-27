package com.fadong.bot.domain;

import lombok.Data;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Data
public class Photo {
    private String url;
    private String width;
    private String height;

    public Photo() {
    }

    public Photo(String url) {
        this.url = url;
    }

    public Photo(String url, String width, String height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }
}
