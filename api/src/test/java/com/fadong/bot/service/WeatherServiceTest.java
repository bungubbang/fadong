package com.fadong.bot.service;

import com.fadong.FadongApplication;
import com.fadong.bot.controller.request.MessageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Created by bungubbang on 04/06/2017.
 */
public class WeatherServiceTest {

    private WeatherService weatherService;

    @Before
    public void init() {
        weatherService = new WeatherService();
        weatherService.setRestTemplate(new RestTemplate());
    }


    @Test
    public void weatherServiceTodayTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("오늘 날씨는 어때?");
        String text = weatherService.text(messageRequest);
        System.out.println("text = " + text);
    }

    @Test
    public void weatherServiceTomorrowTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("내일 날씨는 어때?");
        String text = weatherService.text(messageRequest);
        System.out.println("text = " + text);
    }

    @Test
    public void weatherServiceTest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("날씨 좀 알려줘 어때?");
        String text = weatherService.text(messageRequest);
        System.out.println("text = " + text);
    }

}