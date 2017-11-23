package com.soorya.webscrapers;

public class News
{
    String timestamp;
    String title;
    String authorName;
    String content;
    String tags;
    String url;

    News(String timestamp, String title, String author, String content, String tags, String url)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.tags = tags;
        this.url = url;
    }

    News(String timestamp,String title,String author,String content,String url)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.url = url;
    }
}
