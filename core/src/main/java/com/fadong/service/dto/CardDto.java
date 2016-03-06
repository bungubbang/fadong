package com.fadong.service.dto;

import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/17/14
 */
public class CardDto {

    private List<CardDataDto> data;

    private Object paging;

    public List<CardDataDto> getData() {
        return data;
    }

    public void setData(List<CardDataDto> data) {
        this.data = data;
    }

    public Object getPaging() {
        return paging;
    }

    public void setPaging(Object paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "data=" + data +
                '}';
    }

    public static class CardDataDto {
        private String id;
        private String created_time;
        private String updated_time;
        private String description;
        private String source;
        private List<CardFormatDto> format;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdated_time() {
            return updated_time;
        }

        public void setUpdated_time(String updated_time) {
            this.updated_time = updated_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<CardFormatDto> getFormat() {
            return format;
        }

        public void setFormat(List<CardFormatDto> format) {
            this.format = format;
        }

        @Override
        public String toString() {
            return "CardDataDto{" +
                    "id='" + id + '\'' +
                    ", created_time='" + created_time + '\'' +
                    ", updated_time='" + updated_time + '\'' +
                    ", description='" + description + '\'' +
                    ", source='" + source + '\'' +
                    ", format=" + format +
                    '}';
        }

        public static class CardFormatDto {
            private String embed_html;
            private Integer width;
            private Integer height;
            private String filter;
            private String picture;

            public String getEmbed_html() {
                return embed_html;
            }

            public void setEmbed_html(String embed_html) {
                this.embed_html = embed_html;
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

            public String getFilter() {
                return filter;
            }

            public void setFilter(String filter) {
                this.filter = filter;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            @Override
            public String toString() {
                return "CardFormatDto{" +
                        "embed_html='" + embed_html + '\'' +
                        ", width=" + width +
                        ", height=" + height +
                        ", filter='" + filter + '\'' +
                        ", picture='" + picture + '\'' +
                        '}';
            }
        }
    }
}
