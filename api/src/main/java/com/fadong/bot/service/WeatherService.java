package com.fadong.bot.service;

/**
 * Created by bungubbang on 04/06/2017.
 */

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                text = "날씨예보를 알려드릴께요.\n";
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
