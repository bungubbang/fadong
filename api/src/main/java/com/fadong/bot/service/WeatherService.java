package com.fadong.bot.service;

/**
 * Created by bungubbang on 04/06/2017.
 */

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
public class WeatherService extends MessageService{

    // 에뜰 위도
    private String lat = "37.1891428";
    // 에뜰 경도
    private String lon = "127.0868397";

    private String key = "ad74282e-6af1-394b-8d2f-89335dae58de";

    @Autowired @Setter
    private RestTemplate restTemplate;

    @Override
    public String text(MessageRequest request) {

        String text = "";
        try {
            if(request.getContent().contains("오늘")) {
                text = "오늘 날씨를 알려드릴께요.\n";
                text += getWeatherApi(new TodayFilter());
            } else if(request.getContent().contains("내일")) {
                text = "내일 날씨를 알려드릴께요.\n";
                text += getWeatherApi(new TomorrowFilter());
            } else {
                text += "날씨예보를 알려드릴께요.\n";
                text += getDustApi();
                text += getWeatherApi(new CommonFilter());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        MessageButton messageButton = new MessageButton();
        messageButton.setLabel("자세히보기");
        messageButton.setUrl("https://m.weather.naver.com/m/main.nhn?regionCode=02590588");
        return messageButton;
    }

    public String getDustApi() {
        String url = "http://apis.skplanetx.com/weather/dust?version=1&lat=" + lat + "&lon=" + lon;

        HttpHeaders headers = new HttpHeaders();
        headers.set("appKey", key);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(response.getBody()).getAsJsonObject();
            JsonObject weather = root.getAsJsonObject("weather");
            JsonArray dust = weather.getAsJsonArray("dust");
            if(dust.size() > 0) {
                JsonObject object = dust.get(0).getAsJsonObject();
                JsonObject pm10 = object.getAsJsonObject("pm10");
                String grade = pm10.getAsJsonPrimitive("grade").getAsString();
                String value = pm10.getAsJsonPrimitive("value").getAsString();


                return "\n지금 미세먼지는\n" + grade+ " (pm10 : " + value + ") 이예요!\n\n";
            }
        }
        return "";
    }

    private String getWeatherApi(Filter filter) throws ParserConfigurationException, IOException, SAXException {
        String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4159058800";

        String forObject = restTemplate.getForObject(url, String.class);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(forObject));

        Document document = db.parse(is);
        NodeList body = document.getElementsByTagName("data");

        Integer index = 0;
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < body.getLength(); i++) {
            Element item = (Element) body.item(i);
            Integer hour = Integer.valueOf(item.getElementsByTagName("hour").item(0).getTextContent());
            String wfKor = item.getElementsByTagName("wfKor").item(0).getTextContent();
            Integer day = Integer.valueOf(item.getElementsByTagName("day").item(0).getTextContent());
            String temp = item.getElementsByTagName("temp").item(0).getTextContent();


            if(filter.getFilter(hour, day, index)) {
                DateTime now = DateTime.now().plusDays(day);
                text
                        .append(now.getDayOfMonth())
                        .append("일 ")
                        .append(hour).append("시 : ")
                        .append(wfKor)
                        .append(" (").append(temp).append("도)")
                        .append("\n");
                index++;
            }

        }

        return text.toString();
    }


    interface Filter {
        boolean getFilter(Integer hour, Integer day, Integer index);
    }

    class CommonFilter implements Filter {

        @Override
        public boolean getFilter(Integer hour, Integer day, Integer index) {
            return (hour == 9 || hour == 12 || hour == 15 || hour == 18 || hour == 21)
                    && (day == 0 || day == 1) && index < 5;
        }
    }

    class TodayFilter implements Filter {

        @Override
        public boolean getFilter(Integer hour, Integer day, Integer index) {
            return (hour == 9 || hour == 12 || hour == 15 || hour == 18 || hour == 21)
                    && (day == 0) && index < 4;
        }
    }

    class TomorrowFilter implements Filter {

        @Override
        public boolean getFilter(Integer hour, Integer day, Integer index) {
            return (hour == 9 || hour == 12 || hour == 15 || hour == 18 || hour == 21)
                    && (day == 1) && index < 4;
        }
    }
}
