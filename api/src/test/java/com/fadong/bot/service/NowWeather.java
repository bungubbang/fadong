package com.fadong.bot.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bungubbang on 02/07/2017.
 */
@Ignore
public class NowWeather {

    @Test
    public void now() {
        String url = "http://apis.skplanetx.com/weather/airquality/current?version={version}&lat={lat}&lon={lon}";
        UriTemplate uriTemplate = new UriTemplate(url);
        Map<String, String> param = new HashMap<>();
        param.put("version", "1");
        param.put("lat", "37.1901066896");
        param.put("lon", "127.0866092541");
        URI uri = uriTemplate.expand(param);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("appKey", "ad74282e-6af1-394b-8d2f-89335dae58de");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String body = exchange.getBody();
        System.out.println("body = " + body);
    }
}
