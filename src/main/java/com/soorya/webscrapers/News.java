package com.soorya.webscrapers;

public class News
{
    String timestamp;
    String title;
    String authorName;
    String content;
    String tags;

    News(String timestamp,String title,String author,String content,String tags)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.tags = tags;
    }

    News(String timestamp,String title,String author,String content)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
    }
}
