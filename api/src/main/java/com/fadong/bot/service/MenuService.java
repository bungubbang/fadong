package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.controller.response.MessageResponse;
import com.fadong.bot.domain.BotMenu;
import com.fadong.controller.ApiController;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fadong.bot.domain.BotMenu.*;
import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Created by bungubbang on 06/06/2017.
 */
@Service
public class MenuService {

    private static final Log log = getLog(MenuService.class);

    public MessageResponse getMessage(MessageRequest request) {
        log.info(request.toString());

        if(request.getType().equals("text")) {
            return getTextRequestMessage(request);
        } else {
            return randomService.messageResponse(request);
        }
    }

    private MessageResponse getTextRequestMessage(MessageRequest request) {
        String menu = request.getContent();
        if(menu.contains(WEATHER.getDescription())) {
            return weatherService.messageResponse(request);
        } else if(menu.contains(MART.getDescription())) {
            return martService.messageResponse(request);
        } else if(menu.contains(MANGER.getDescription())) {
            return manageService.messageResponse(request);
        } else if(menu.contains(PLACE.getDescription())) {
            return placeService.messageResponse(request);
        } else if(menu.contains(BUS.getDescription())) {
            return busService.messageResponse(request);
        } else {
            return randomService.messageResponse(request);
        }
    }

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private RandomService randomService;

    @Autowired
    private MartService martService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ManageService manageService;

    @Autowired
    private BusService busService;
}
