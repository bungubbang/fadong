package com.fadong.bot.domain;

import com.fadong.bot.controller.response.MessageResponse;
import lombok.Data;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Data
public class Message {
    private String text;
    private Photo photo;
    private MessageButton message_button;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Photo photo, MessageButton message_button) {
        this.text = text;
        this.photo = photo;
        this.message_button = message_button;
    }
}
