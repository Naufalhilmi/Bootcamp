package com.example.bootcamp.Interface;

import com.example.bootcamp.model.Movie;
import com.example.bootcamp.model.User;

public class AppService {
    private static String token;
    private static User user;
    private static Movie movie;


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppService.token = token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppService.user = user;
    }

    public static Movie getMovie() {
        return movie;
    }

    public static void setMovie(Movie movie) {
        AppService.movie = movie;
    }
}
