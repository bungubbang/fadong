package com.fadong.bot.controller.response;

import com.fadong.bot.domain.Keyboard;
import com.fadong.bot.domain.Message;
import lombok.Data;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Data
public class MessageResponse {
    private Message message;
    private Keyboard keyboard;

    public MessageResponse() {
    }

    public MessageResponse(String text) {
        this.message = new Message(text);
    }

}
