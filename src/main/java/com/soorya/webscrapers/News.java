package com.soorya.webscrapers;

public class News
{
    String timestamp;
    String title;
    String authorName;
    String content;
    String tags;
    String url;
    int ID,likes,fakes;

    News(String timestamp, String title, String author, String content, String tags, String url)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.tags = tags;
        this.url = url;
        this.likes = 0;
        this.fakes = 0;
        this.ID = title.hashCode();
    }

    News(String timestamp,String title,String author,String content,String url)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.url = url;
        this.likes = 0;
        this.fakes = 0;
        this.ID = title.hashCode();
    }

    public int getLikes()
    {
        return likes;
    }

    public void incrementLikes()
    {
        this.likes++;
    }

    public int getFakes()
    {
        return fakes;
    }

    public void incrementFakes()
    {
        this.fakes++;
    }
}
