package com.fadong.service;


import com.fadong.domain.AccessToken;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
public interface AccessTokenService {
    AccessToken getAccessToken(String code);
    AccessToken refreshAccessToken();
}
