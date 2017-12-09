/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brocode.mainproject;
import  com.hariharan.textsummarizer.*;
import static com.hariharan.textsummarizer.TextSum.sentBreak;
import fakenews.*;
/**
 *
 * @author thero
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

    News(String timestamp, String title, String author, String content, String tags, String url,int id)
    {
        this.timestamp = timestamp;
        this.title = title;
        this.authorName = author;
        this.content = content;
        this.tags = tags;
        this.url = url;
        this.likes = 0;
        this.fakes = 0;
        this.ID = id;
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
    
    public String summarize(int lines)
    {
        TextSum sum = new TextSum();
        sum.setText(this.content);
        for(String s: sentBreak.sentDetect(this.content))
        {
            sum.computeCosine(s);
        }
       return sum.sortByValue(lines);
    }
    
    public boolean detect() throws Exception
    {
        //True - Real, False - fake
        Detector d = new Detector(this.content);
        boolean real = d.detect().equalsIgnoreCase("Real")?true:false;
        return real;
    }
}
