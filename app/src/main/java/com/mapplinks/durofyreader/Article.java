package com.mapplinks.durofyreader;

import java.io.Serializable;


/**
 * Created by Aditya Vikram on 9/16/2015.
 */
public class Article implements Serializable {
    private String Title;
    private String Author;
    private String timeStamp;
    private String category;
    private String imageUrl;
    private String link;
    private String description;

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getCategory() {
        return category;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {        return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

