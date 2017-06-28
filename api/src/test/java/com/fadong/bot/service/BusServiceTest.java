package com.fadong.bot.service;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sungyong.jung (sungyong.jung@navercorp.com) on 2017-06-27.
 */
public class BusServiceTest {

    @Test
    public void busTest() throws IOException, SAXException, ParserConfigurationException {
        BusService busService = new BusService();
        busService.setRestTemplate(new RestTemplate());
        System.out.println("busService.text(null) = " + busService.text(null));
    }

}