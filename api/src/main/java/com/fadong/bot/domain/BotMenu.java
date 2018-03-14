package com.fadong.bot.domain;

/**
 * Created by bungubbang on 07/06/2017.
 */
public enum BotMenu {


    WEATHER("날씨"), BUS("버스"), MART("마트")
    , PLACE("도로명주소"), MANGER("관리사무소")
    ;


    private String description;
    public String getDescription() {
        return description;
    }

    private BotMenu(String description) {
        this.description = description;
    }
}
