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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@Service
public class FeviBatchService implements BatchService {

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
    public Date updatePage() {
        AccessToken accessToken = accessTokenService.refreshAccessToken();
        List<Page> pages = pageRepository.findAll();
        for (Page page : pages) {
            StringBuilder profile = new StringBuilder();
            profile.append("https://graph.facebook.com");
            profile.append("/" + page.getId());
            profile.append("?fields=picture.height(200).type(normal).width(200),id,name");
            profile.append("&access_token=" + accessToken.getAccessToken());

            PageDto updatePage = restTemplate.getForObject(profile.toString(), PageDto.class);
            page.setName(updatePage.getName());
            page.setProfile_image(updatePage.getPicture().getData().getUrl());
            page.setUpdateDate(new Date().toString());

            pageRepository.save(page);

        }
        return new Date();
    }

    @Override
    public Date updateCard() {
        AccessToken accessToken = accessTokenService.refreshAccessToken();
        List<Page> pages = pageRepository.findAll();
        for (Page page : pages) {
            StringBuilder profile = new StringBuilder();
            profile.append("https://graph.facebook.com");
            profile.append("/" + page.getId());
            profile.append("/videos?fields=id,created_time,updated_time,description,source,format&limit=10");
            profile.append("&access_token=" + accessToken.getAccessToken());

            CardDto updateCard = restTemplate.getForObject(profile.toString(), CardDto.class);

            for (CardDto.CardDataDto cardDataDto : updateCard.getData()) {
                Card card = cardRepository.findOne(cardDataDto.getId());
                if(card == null) {
                    card = new Card();
                }
            }
        }


        return new Date();
    }
}
