/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;

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
