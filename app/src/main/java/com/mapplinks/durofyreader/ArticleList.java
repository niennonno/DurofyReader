package com.mapplinks.durofyreader;

import java.util.ArrayList;

/**
 * Created by Aditya Vikram on 9/16/2015.
 */
public class ArticleList extends ArrayList<Article> {
    private static ArticleList sInstance = null;

    private ArticleList() {
    }

    public static ArticleList getInstance() {
        if (sInstance == null) {
            sInstance = new ArticleList();
        }
        return sInstance;
    }
}
