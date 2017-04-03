package com.fadong.domain;

import lombok.Data;

/**
 * Created by 1000742 on 2017. 4. 3..
 */
@Data
public class TokenResponse {
    private String access_token;
    private String expires_in;
    private String machine_id;
}
