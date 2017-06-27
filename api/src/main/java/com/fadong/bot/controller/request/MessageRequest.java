package com.fadong.bot.controller.request;

import lombok.Data;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Data
public class MessageRequest {
    private String user_key;
    private String type;
    private String content;
}
