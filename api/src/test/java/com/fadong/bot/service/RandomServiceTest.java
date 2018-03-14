package com.fadong.bot.service;

import org.junit.Test;

/**
 * Created by bungubbang on 06/06/2017.
 */
public class RandomServiceTest {


    @Test
    public void randomServiceTest() {
        RandomService randomService = new RandomService();
        String text = randomService.text(null);
        System.out.println("text = " + text);
    }

}