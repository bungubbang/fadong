package com.fadong.domain;

import com.fadong.service.dto.PageDto;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 3/9/14
 */
@Entity
public class Page {

    @Id
    private String id;

    private String name;
    private String category;
    private String profile_image;
    private String updateDate;


    public Page() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
