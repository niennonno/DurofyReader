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

    public boolean process() {
        boolean status = true;
        Article currentRecord=null;
        boolean inEntry = false;
        String textValue = "";
        boolean firstCategory=true;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            inEntry = true;
                            currentRecord = new Article();
                            firstCategory=true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (inEntry){
                            if (tagName.equalsIgnoreCase("item")){
                                articleList.add(currentRecord);
                                inEntry=false;
                                firstCategory=true;
                            }else if (tagName.equalsIgnoreCase("title")){
                                currentRecord.setTitle(textValue);
                            }else if (tagName.equalsIgnoreCase("link")){
                                currentRecord.setLink(textValue);
                            }else if (tagName.equalsIgnoreCase("pubdate")){
                                currentRecord.setTimeStamp(textValue);
                            }else if (tagName.equalsIgnoreCase("author")){
                                currentRecord.setAuthor(textValue);
                            }else if (tagName.equalsIgnoreCase("category")){
                                if (firstCategory){
                                    currentRecord.setCategory(textValue);
                                    firstCategory=false;
                                }
                            }else if (tagName.equalsIgnoreCase("image")){
                                currentRecord.setImageUrl(textValue);
                            }else if (tagName.equalsIgnoreCase("link")){
                                currentRecord.setLink(textValue);
                            }else if (tagName.equalsIgnoreCase("description")){
                                currentRecord.setDescription(textValue);
                            }
                        }
                        break;

                    default:
                        //NOthing
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return true;
    }
}
