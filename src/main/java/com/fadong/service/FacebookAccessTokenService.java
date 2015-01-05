package com.fadong.service;

import com.fadong.domain.AccessToken;
import com.fadong.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class FacebookAccessTokenService implements AccessTokenService {

    @Autowired
    private AccessTokenRepository tokenRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${FACEBOOK_APP_ID}") private String fbAppId;
    @Value("${FACEBOOK_APP_SECRET}") private String fbAppSecret;
    @Value("${FACEBOOK_REDIRECT_URI}") private String fbRedirectUrl;
    @Value("${FACEBOOK_SCOPE}") private String fbScope;

    @Override
    public AccessToken getAccessToken(String code) {
        StringBuilder tokenUrl = new StringBuilder();
        tokenUrl.append("https://graph.facebook.com/oauth/access_token");
        tokenUrl.append("?client_id=" + fbAppId);
        tokenUrl.append("&redirect_uri=" + fbRedirectUrl);
        tokenUrl.append("&client_secret=" + fbAppSecret);
        tokenUrl.append("&code=" + code);

        String tokenParam = restTemplate.getForObject(tokenUrl.toString(), String.class);
        AccessToken accessToken = generateToken(tokenParam);
        return tokenRepository.save(accessToken);
    }

    @Override
    public AccessToken refreshAccessToken() {
        AccessToken accessToken = tokenRepository.findAll().get(0);

        StringBuilder tokenUrl = new StringBuilder();
        tokenUrl.append("https://graph.facebook.com/oauth/access_token");
        tokenUrl.append("?client_id=" + fbAppId);
        tokenUrl.append("&client_secret=" + fbAppSecret);
        tokenUrl.append("&grant_type=" + "fb_exchange_token");
        tokenUrl.append("&fb_exchange_token=" + accessToken.getAccessToken());

        String tokenParam = restTemplate.getForObject(tokenUrl.toString(), String.class);
        accessToken = generateToken(tokenParam);

        return tokenRepository.save(accessToken);
    }


    private AccessToken generateToken(String tokenParam) {
        String[] params = tokenParam.split("&");
        String[] token = params[0].split("=");
        String[] expires = params[1].split("=");
        List<AccessToken> all = tokenRepository.findAll();
        AccessToken accessToken;

        if(all.isEmpty()) {
            accessToken = new AccessToken();
        } else {
            accessToken = all.get(0);
        }

        accessToken.setAccessToken(token[1]);
        accessToken.setExpires(expires[1]);
        accessToken.setCreateTime(new Date().toString());
        return accessToken;
    }


}
