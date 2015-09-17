package com.mapplinks.durofyreader;

import android.app.Application;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by Aditya Vikram on 9/17/2015.
 */
public class ParseArticles {
    private String xmlData;
    private ArticleList articleList;

    public ParseArticles(String xmlData) {
        this.xmlData = xmlData;
        articleList = ArticleList.getInstance();
    }

    public ArticleList getArticleList() {
        return articleList;
    }

    public boolean process(){
        boolean status=true;
        Article currentRecord;
        boolean inEntry=false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));

            int eventType=xpp.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT){
                String tagName =xpp.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            inEntry=true;
                            currentRecord=new Article();

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        //NOthing
                }
                eventType=xpp.next();
            }
        }catch (Exception e){
            status=false;
            e.printStackTrace();
        }
        return true;
    }
}
