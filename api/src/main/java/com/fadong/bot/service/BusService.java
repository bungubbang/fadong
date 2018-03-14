package com.fadong.bot.service;

import com.fadong.bot.controller.request.MessageRequest;
import com.fadong.bot.domain.MessageButton;
import com.fadong.bot.domain.Photo;
import com.google.gson.Gson;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

/**
 * Created by sungyong.jung (sungyong.jung@navercorp.com) on 2017-06-27.
 */
@Service
public class BusService extends MessageService {

    @Autowired
    @Setter
    private RestTemplate restTemplate;

    @Override
    public String text(MessageRequest request)  {
        StringBuilder sb = new StringBuilder();
        try {
            String url = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey=NyESZ5Y42DoorxXdquqBmgTR9e%2B2rteCoLtF2AxQEQ00kTdDUFY5BkU3dSlFna%2FKfb%2BSXq%2B%2FPJTc3gjGPSiLkg%3D%3D&stationId=233002498";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> exchange = restTemplate.exchange(new URL(url).toURI(), HttpMethod.GET, entity, String.class);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(exchange.getBody()));

            Document document = db.parse(is);
            NodeList busArrivalList = document.getElementsByTagName("busArrivalList");

            sb.append("버스 도착 정보를 알려드릴께요.\n");
            for (int i = 0; i < busArrivalList.getLength(); i++) {
                Element item = (Element) busArrivalList.item(i);
                Integer routeId = Integer.valueOf(item.getElementsByTagName("routeId").item(0).getTextContent());
                String predictTime = item.getElementsByTagName("predictTime1").item(0).getTextContent();
                sb.append(getBusNumber(routeId)).append("번 버스 : ").append(predictTime).append(" 분후 도착\n");
            }
            sb.append("\n").append("( 마을버스 추후 지원예정 )");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return sb.toString();
    }

    @Override
    public Photo photo() {
        return null;
    }

    @Override
    public MessageButton messageButton() {
        MessageButton messageButton = new MessageButton();
        messageButton.setLabel("자세히보기");
        messageButton.setUrl("https://m.map.naver.com/bus/station.nhn?stationID=90504");
        return messageButton;
    }

    public String getBusNumber(Integer routeId) {
        switch (routeId) {
            case 200000105: return "15";
            case 233000145: return "702";
            case 233000257: return "703";
            case 200000172: return "99-1";
        }
        return "";
    }
}
