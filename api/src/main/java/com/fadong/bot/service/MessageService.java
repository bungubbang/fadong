package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.Keyboard;
import com.fadong.bot.controller.response.MessageResponse;
import com.fadong.bot.domain.Message;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import com.google.common.collect.Lists;

/**
 * Created by bungubbang on 06/06/2017.
 */
public abstract class MessageService {
    public abstract String text(MessageRequest request);
    public abstract Photo photo();
    public abstract MessageButton messageButton();

    public Keyboard keyboardResponse() {
        return new Keyboard();
    }

    public MessageResponse messageResponse(MessageRequest request) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(new Message(text(request), photo(), messageButton()));
        messageResponse.setKeyboard(new Keyboard());
        return messageResponse;
    }

}
