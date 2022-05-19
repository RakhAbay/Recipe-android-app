package com.example.recipeapp.entities;


import java.util.List;


public class LoggedInUser {
    private static LoggedInUser user;

    private String username = "";
    private String email = "";
    private Long userId = 0L;
    private List<Long> favorites;

    private LoggedInUser() {}

    public static LoggedInUser getInstance() {
        if (user == null) {
            user = new LoggedInUser();
        }
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }

}