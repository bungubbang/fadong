package com.fadong.bot.domain;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Getter
public class Keyboard {
    private String type;
    private List<String> buttons;

    public Keyboard() {
        this.type = "buttons";
        this.buttons = Lists.newArrayList(BotMenu.values()).stream().map(BotMenu::getDescription).collect(Collectors.toList());
    }
}
