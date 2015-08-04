package com.fadong.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 15. 8. 4.
 */
@Entity
public class PageToken {
    @Id
    private long id;
    private String accessToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "PageToken{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
