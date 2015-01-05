package com.fadong.service;

import com.fadong.domain.AccessToken;
import com.fadong.domain.Card;
import com.fadong.domain.Page;
import com.fadong.repository.AccessTokenRepository;
import com.fadong.repository.CardRepository;
import com.fadong.repository.PageRepository;
import com.fadong.repository.UserRepository;
import com.fadong.service.dto.CardDto;
import com.fadong.service.dto.PageDto;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static com.fadong.service.dto.CardDto.CardDataDto;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@Service
@EnableScheduling
public class FeviBatchService implements BatchService {

    Logger log = LoggerFactory.getLogger(FeviBatchService.class);


    @Autowired
    private AccessTokenRepository tokenRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    @Scheduled(cron = "0 0 4 * * *")
    public void updatePage() {
        AccessToken accessToken = accessTokenService.refreshAccessToken();
        List<Page> pages = pageRepository.findAll();
        for (Page page : pages) {
            StringBuilder profile = new StringBuilder();
            profile.append("https://graph.facebook.com");
            profile.append("/" + page.getId());
            profile.append("?fields=picture.height(200).type(normal).width(200),id,name");
            profile.append("&access_token=" + accessToken.getAccessToken());

            try {
                PageDto updatePage = restTemplate.getForObject(profile.toString(), PageDto.class);
                page.setName(updatePage.getName());
                page.setProfile_image(updatePage.getPicture().getData().getUrl());
                page.setUpdateDate(new Date().toString());

                pageRepository.save(page);
            } catch (HttpClientErrorException e) {
                if(e.getStatusCode().is4xxClientError()) {
                    log.info("deletePage : {} ", page);
                    pageRepository.delete(page);
                }
            }

        }

        log.info("pageUpdate : {} ", DateTime.now().toString("yyyy-MM-SS HH:mm"));
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void updateCardRecently() {
        AccessToken accessToken = accessTokenService.refreshAccessToken();
        List<Page> pages = pageRepository.findAll();
        for (Page page : pages) {
            StringBuilder profile = new StringBuilder();
            profile.append("https://graph.facebook.com");
            profile.append("/" + page.getId());
            profile.append("/videos?fields=id,created_time,updated_time,description,source,format&limit=10");
            profile.append("&access_token=" + accessToken.getAccessToken());

            CardDto updateCard = restTemplate.getForObject(profile.toString(), CardDto.class);

            for (CardDataDto cardDataDto : updateCard.getData()) {
                Card card = cardRepository.findOne(cardDataDto.getId());
                if(card == null) {
                    card = new Card();
                }
                card.updateByDto(cardDataDto, page);
                cardRepository.save(card);

            }
        }

        log.info("cardUpdate : {} ", DateTime.now().toString("yyyy-MM-SS HH:mm"));
    }

    @Override
    @Scheduled(cron = "0 0 5 * * *")
    public void updateCardAll() {

        AccessToken accessToken = accessTokenService.refreshAccessToken();

        int i = 0;
        org.springframework.data.domain.Page<Card> cards = cardInfoUpdate(accessToken, i);

        while (cards.hasNext()) {
            cards = cardInfoUpdate(accessToken, i);
            i++;
        }

        log.info("cardUpdateAll : {} , count : {}", DateTime.now().toString("yyyy-MM-SS HH:mm"), cards.getTotalElements());
    }

    private org.springframework.data.domain.Page<Card> cardInfoUpdate(AccessToken accessToken, int i) {
        org.springframework.data.domain.Page<Card> cards = cardRepository.findAll(new PageRequest(i, 1000));
        for (Card card : cards) {
            try {
                StringBuilder builder = new StringBuilder();
                builder.append("https://graph.facebook.com");
                builder.append("/" + card.getId());
                builder.append("?access_token=" + accessToken.getAccessToken());

                CardDataDto updateCard = restTemplate.getForObject(builder.toString(), CardDataDto.class);

                card.updateByDto(updateCard);
                cardRepository.save(card);
            }catch (HttpClientErrorException e) {
                if(e.getStatusCode().is4xxClientError()) {
                    log.info("deleteCard : {} ", card.getId());
                    cardRepository.delete(card);
                }
            }
        }
        return cards;
    }
}
