package com.fadong.bot.controller;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.Keyboard;
import com.fadong.bot.controller.response.MessageResponse;
import com.fadong.bot.service.MenuService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bungubbang on 04/06/2017.
 */
@RestController
@RequestMapping("bot")
public class BotController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "keyboard", method = RequestMethod.GET)
    public Keyboard keyboard() {
        return new Keyboard();
    }

    @RequestMapping(value = "message", method = RequestMethod.POST)
    public MessageResponse message(@RequestBody MessageRequest messageRequest) {
        return menuService.getMessage(messageRequest);
    }

}
