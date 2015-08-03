package com.fadong.domain;

import com.fadong.service.dto.CardDto;

import javax.persistence.*;

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

    @Lob
    @Column(length = 100000)
    private String description;

    /*
        Page 관련 도메인
    */
    private String name;
    private String category;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "updated_time")
    private String updatedTime;

    @Column(name = "created_time")
    private String createdTime;

    private Integer width;
    private Integer height;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    public Card() {
        this.status = STATUS.ENABLE;
    }

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

    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getUpdatedTime() {
        if(createdTime != null) {
            return updatedTime.split("T")[0];
        }
        return updatedTime;
    }
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedTime() {
        if(createdTime != null) {
            return createdTime.split("T")[0];
        }
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreated_time() {
        return getCreatedTime();
    }

    public String getUpdated_time() {
        return getUpdatedTime();
    }

    public String getProfile_image() {
        return getProfileImage();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Card updateByDto(CardDto.CardDataDto dto) {
        CardDto.CardDataDto.CardFormatDto cardFormatDto = dto.getFormat().get(dto.getFormat().size() - 1);

        setId(dto.getId());
        setSource(dto.getSource());
        setPicture(cardFormatDto.getPicture());
        setDescription(dto.getDescription());
        setUpdatedTime(dto.getUpdated_time());
        setCreatedTime(dto.getCreated_time());
        setWidth(cardFormatDto.getWidth());
        setHeight(cardFormatDto.getHeight());

        return this;
    }

    public Card updateByDto(CardDto.CardDataDto dto, Page page) {
        CardDto.CardDataDto.CardFormatDto cardFormatDto = dto.getFormat().get(dto.getFormat().size() - 1);

        setId(dto.getId());
        setSource(dto.getSource());
        setPicture(cardFormatDto.getPicture());
        setDescription(dto.getDescription());
        setName(page.getName());
        setCategory(page.getCategory());
        setProfileImage(page.getProfile_image());
        setUpdatedTime(dto.getUpdated_time());
        setCreatedTime(dto.getCreated_time());
        setWidth(cardFormatDto.getWidth());
        setHeight(cardFormatDto.getHeight());

        return this;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", status=" + status +
                '}';
    }

    public static enum STATUS {
        ENABLE, DISABLE
    }
}
