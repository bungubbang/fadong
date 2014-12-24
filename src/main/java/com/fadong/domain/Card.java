package com.fadong.domain;

import com.fadong.service.dto.CardDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 3/9/14
 */
@Entity
public class Card {

    @Id
    private String id;

    private String source;
    private String picture;
    private String description;

    private Integer comments_size;
    private Integer likes_size;

    /*
        Page 관련 도메인
    */
    private String name;
    private String category;
    private String profile_image;

    private String updated_time;
    private String created_time;

    public Card() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getComments_size() {
        return comments_size;
    }
    public void setComments_size(Integer comments_size) {
        this.comments_size = comments_size;
    }

    public Integer getLikes_size() {
        return likes_size;
    }
    public void setLikes_size(Integer likes_size) {
        this.likes_size = likes_size;
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

    public String getUpdated_time() {
        if(created_time != null) {
            return updated_time.split("T")[0];
        }
        return updated_time;
    }
    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getCreated_time() {
        if(created_time != null) {
            return created_time.split("T")[0];
        }
        return created_time;
    }
    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public Card updateByDto(CardDto.CardDataDto dto) {

//        dto.getFormat().

        setId(dto.getId());
        setSource(dto.getSource());
        setPicture(dto.get);
        setDescription();
        setComments_size();
        setLikes_size();
        setName();
        setCategory();
        setProfile_image();
        setUpdated_time();
        setCreated_time();

        return this;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", comments_size=" + comments_size +
                ", likes_size=" + likes_size +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", created_time='" + created_time + '\'' +
                '}';
    }
}
