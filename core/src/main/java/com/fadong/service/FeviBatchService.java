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

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@Service
public class FeviBatchService implements BatchService {

    Log log = LogFactory.getLog(FeviBatchService.class);

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
            StringBuilder profile = new StringBuilder();
            profile.append("https://graph.facebook.com");
            profile.append("/" + page.getId());
            profile.append("?fields=picture.height(200).type(normal).width(200),id,name");
            profile.append("&access_token=" + accessTokenService.getAccessToken());

            String result = restTemplate.getForObject(profile.toString(), String.class);
            JsonElement gson = new JsonParser().parse(result);
            JsonElement name = gson.getAsJsonObject().get("name");
            JsonElement url = gson.getAsJsonObject().get("picture").getAsJsonObject().get("data").getAsJsonObject().get("url");

            page.setName(name.getAsString());
            page.setProfile_image(url.getAsString());
            page.setUpdateDate(new Date().toString());
            pageRepository.save(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[Page Update ERROR] : " + page);
            pageRepository.delete(page);
        }


        return page;
    }

    @Override
    public Page updateCardRecently(Page page) {
        StringBuilder profile = new StringBuilder();
        profile.append("https://graph.facebook.com");
        profile.append("/" + page.getId());
        profile.append("/videos?fields=id,created_time,updated_time,description,source,format&limit=10");
        profile.append("&access_token=" + accessTokenService.getAccessToken());

        CardDto updateCard = restTemplate.getForObject(profile.toString(), CardDto.class);

        for (CardDto.CardDataDto cardDataDto : updateCard.getData()) {
            Card card = cardRepository.findOne(cardDataDto.getId());
            if (card == null) {
                card = new Card();
            }
            card.updateByDto(cardDataDto, page);
            cardRepository.save(card);
            log.info("update card : " + card.getId());

        }
        return page;
    }

    @Override
    public Card updateCardAll(Card card) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("https://graph.facebook.com");
            builder.append("/" + card.getId());
            builder.append("?access_token=" + accessTokenService.getAccessToken());

            CardDto.CardDataDto updateCard = restTemplate.getForObject(builder.toString(), CardDto.CardDataDto.class);
            Card saveCard = card.updateByDto(updateCard);
            cardRepository.save(saveCard);
            return saveCard;
        } catch (Exception e) {
            e.printStackTrace();
            cardRepository.delete(card);
        }
        return card;
    }

    @Override
    public void removeCard(Card card) {
        cardRepository.delete(card);
    }
}
