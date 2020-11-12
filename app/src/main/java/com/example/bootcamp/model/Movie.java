package com.example.bootcamp.model;

public class Movie {
    private long id;
    private String judul;
    private String rating;
    private String sinopsis;
    private String sutradara;
    private String cast;
    private String image;

    public Movie() {
    }

    public Movie(long id, String judul, String rating, String sinopsis, String sutradara, String cast, String image) {
        this.id = id;
        this.judul = judul;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.sutradara = sutradara;
        this.cast = cast;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) {
        this.sutradara = sutradara;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", rating='" + rating + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", sutradara='" + sutradara + '\'' +
                ", cast='" + cast + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
