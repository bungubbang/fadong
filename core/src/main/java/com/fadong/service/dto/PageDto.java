package com.fadong.service.dto;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 11/16/14
 */
public class PageDto {
    private String id;
    private String name;
    private Picture picture;

    public PageDto() {
    }

    public PageDto(String id, String name, Picture picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture=" + picture +
                '}';
    }


    public static class Picture {
        private PictureData data;

        public Picture() {
        }

        public Picture(PictureData data) {
            this.data = data;
        }

        public PictureData getData() {
            return data;
        }

        public void setData(PictureData data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Picture{" +
                    "data=" + data +
                    '}';
        }

        public static class PictureData {
            private int height;
            private int width;
            private boolean is_silhouette;
            private String url;

            public PictureData() {
            }

            public PictureData(int height, int width, boolean is_silhouette, String url) {
                this.height = height;
                this.width = width;
                this.is_silhouette = is_silhouette;
                this.url = url;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public boolean isIs_silhouette() {
                return is_silhouette;
            }

            public void setIs_silhouette(boolean is_silhouette) {
                this.is_silhouette = is_silhouette;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                return "PictureData{" +
                        "height=" + height +
                        ", width=" + width +
                        ", is_silhouette=" + is_silhouette +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }
}
