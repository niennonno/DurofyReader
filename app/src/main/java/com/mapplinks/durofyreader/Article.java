package com.mapplinks.durofyreader;

import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by Aditya Vikram on 9/16/2015.
 */
public class Article implements Serializable{
    private String Title;
    private String Author;
    private String timeStamp;
    private String category;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

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

    public void setCategory(String category) {
        this.category = category;
    }
}

