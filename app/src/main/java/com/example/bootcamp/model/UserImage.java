package com.example.bootcamp.model;

public class UserImage {
    private Long user_id;
    private String avatar;

    public UserImage() {
    }

    public UserImage(Long user_id, String avatar) {
        this.user_id = user_id;
        this.avatar = avatar;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "user_id='" + user_id + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
