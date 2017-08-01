package com.fadong.service;


import com.fadong.domain.AccessToken;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
public interface AccessTokenService {
    AccessToken getAccessToken(String code);
    String getAccessToken();
    AccessToken refreshAccessToken();
    AccessToken refreshAccessToken(AccessToken accessToken);
    String getPageAccessToken(String accessToken);
}
