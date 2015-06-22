package com.fadong.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 3/12/14
 */
@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String uid;
    private String favorite;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFavorite() {
        return favorite;
    }
    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }
}
