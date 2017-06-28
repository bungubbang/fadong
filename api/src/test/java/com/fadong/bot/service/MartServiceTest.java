package com.fadong.bot.service;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Created by bungubbang on 07/06/2017.
 */
public class MartServiceTest {
    @Test
    public void restDay() throws Exception {
        MartService martService = new MartService();
        String text = martService.text(null);
        System.out.println("text = " + text);

    }

}