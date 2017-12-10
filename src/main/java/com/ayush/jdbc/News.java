/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;

import java.sql.SQLException;

/**
 *
 * @author asus
 */
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

    public void incrementLikes() throws SQLException
    {
        this.likes++;
        AddArticles addr = new AddArticles("root","Ayudrag11@","jdbc:mysql://localhost/database");
        addr.update("UPDATE Articlesf SET likes = '"+likes+"' WHERE ID = '"+this.ID+"'");
    }

    public int getFakes()
    {
        return fakes;
        
    }

    public void incrementFakes() throws SQLException
    {
        this.fakes++;
        AddArticles addr = new AddArticles("root","Ayudrag11@","jdbc:mysql://localhost/database");
        addr.update("UPDATE Articlesf SET fakes = '"+fakes+"' WHERE ID = '"+this.ID+"'");
        
    }

}
