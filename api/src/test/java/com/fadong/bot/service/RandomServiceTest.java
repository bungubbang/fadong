package com.fadong.bot.service;

import com.fadong.FadongApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

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