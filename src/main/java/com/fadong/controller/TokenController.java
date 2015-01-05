package com.fadong.controller;

import com.fadong.domain.AccessToken;
import com.fadong.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
@Controller
@RequestMapping("token")
public class TokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Value("${FACEBOOK_APP_ID}") private String fbAppId;
    @Value("${FACEBOOK_APP_SECRET}") private String fbAppSecret;
    @Value("${FACEBOOK_REDIRECT_URI}") private String fbRedirectUrl;
    @Value("${FACEBOOK_SCOPE}") private String fbScope;


    @RequestMapping(value = "login", method = GET)
    public RedirectView login() {
        StringBuilder oauthUrl = new StringBuilder();
        oauthUrl.append("https://www.facebook.com/dialog/oauth");
        oauthUrl.append("?client_id=" + fbAppId);
        oauthUrl.append("&redirect_uri=" + fbRedirectUrl);
        oauthUrl.append("&scope=" + fbScope);

        return new RedirectView(oauthUrl.toString());
    }

    @ResponseBody
    @RequestMapping(value = "login/callback", method = GET)
    public AccessToken callback(@RequestParam String code) {
        return accessTokenService.getAccessToken(code);
    }

    @ResponseBody
    @RequestMapping(value = "login/token/refresh", method = GET)
    public AccessToken tokenRefresh() {
        return accessTokenService.refreshAccessToken();
    }
}
