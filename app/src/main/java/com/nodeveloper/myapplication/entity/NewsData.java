package com.nodeveloper.myapplication.entity;

public class NewsData {
    private String title;
    private String date;
    private String newsUrl;
    private String author;

    private String imgUrlOne;
    private String imgUrlTwo;
    private String imgUrlThree;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", author='" + author + '\'' +
                ", imgUrlOne='" + imgUrlOne + '\'' +
                ", imgUrlTwo='" + imgUrlTwo + '\'' +
                ", imgUrlThree='" + imgUrlThree + '\'' +
                '}';
    }

    public String getImgUrlOne() {
        return imgUrlOne;
    }

    public void setImgUrlOne(String imgUrlOne) {
        this.imgUrlOne = imgUrlOne;
    }

    public String getImgUrlTwo() {
        return imgUrlTwo;
    }

    public void setImgUrlTwo(String imgUrlTwo) {
        this.imgUrlTwo = imgUrlTwo;
    }

    public String getImgUrlThree() {
        return imgUrlThree;
    }

    public void setImgUrlThree(String imgUrlThree) {
        this.imgUrlThree = imgUrlThree;
    }
}
