package com.fadong.service;

import com.fadong.domain.Card;
import com.fadong.domain.Page;
import com.fadong.repository.CardRepository;
import com.fadong.repository.PageRepository;
import com.fadong.service.dto.CardDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@Service
public class FeviBatchService implements BatchService {

    private Log log = LogFactory.getLog(FeviBatchService.class);

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public Page updatePage(Page page) {
        try {
            String profile = "https://graph.facebook.com/" + page.getId() +
                                "?fields=picture.height(200).type(normal).width(200),id,name" +
                                "&access_token=" + accessTokenService.getAccessToken();

            String result = restTemplate.getForObject(profile, String.class);
            JsonElement gson = new JsonParser().parse(result);
            JsonElement name = gson.getAsJsonObject().get("name");
            JsonElement url = gson.getAsJsonObject().get("picture").getAsJsonObject().get("data").getAsJsonObject().get("url");

            page.setName(name.getAsString());
            page.setProfile_image(url.getAsString());
            page.setUpdateDate(new Date().toString());
            pageRepository.save(page);
        } catch (Exception e) {
            log.error("[Page Update ERROR] : " + page);
//            pageRepository.delete(page);
        }


        return page;
    }

    @Override
    public Page updateCardRecently(Page page) {
        String profile = "https://graph.facebook.com/" + page.getId() +
                            "/videos?fields=id,created_time,updated_time,description,source,format&limit=10" +
                            "&access_token=" + accessTokenService.getAccessToken();

        CardDto updateCard = restTemplate.getForObject(profile, CardDto.class);

        for (CardDto.CardDataDto cardDataDto : updateCard.getData()) {
            try{
                Card card = cardRepository.findOne(cardDataDto.getId());
                if (card == null) {
                    card = new Card();
                }
                card.updateByDto(cardDataDto, page);
                cardRepository.save(card);
                log.info("update card : " + card.getId());
            } catch (Exception e) {
                if(!Objects.isNull(cardDataDto)) {
                    log.info("ERROR card : " + cardDataDto.getId());
                }
            }


        }
        return page;
    }

    @Override
    public Card updateCardAll(Card card) {
        CardDto.CardDataDto updateCard = null;
        try {
            String builder = "https://graph.facebook.com/" + card.getId() + "?access_token=" + accessTokenService.getAccessToken();

            updateCard = restTemplate.getForObject(builder, CardDto.CardDataDto.class);
            Card saveCard = card.updateByDto(updateCard);
            cardRepository.save(saveCard);
            return saveCard;
        } catch (Exception e) {
            e.printStackTrace();
            if(!Objects.isNull(updateCard)) {
                log.info("ERROR card : " + updateCard.getId());
//            cardRepository.delete(card);
            }
        }

        return card;
    }

    @Override
    public void removeCard(Card card) {
        cardRepository.delete(card);
    }
}
