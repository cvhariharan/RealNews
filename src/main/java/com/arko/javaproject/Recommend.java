/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arko.javaproject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Recommend {
    
    ArrayList<NewsArticle>  articles;      //NewsArticle type object of articles
    String a;
            
    Recommend(){
       articles=new ArrayList<>();
       a="Donald Trump to Brigitte Macron, wife of French president Emmanuel Macron. You're in such good shape. She's in such good physical shape. Beautiful,the American president told the French first couple, as U.S. first lady Melania Trump stood by. The reaction from predictable sources was predictable. Creepy, the Twitterverse declared. Feminists rolled their collective eyes at a president who can't seem, at time, to make the transition from beauty pageant owner to nominal leader of the free world. The Australian foreign minister said she'd be taken aback if it was said to her, then wondered aloud if the same could be said to the non-exercising, fast food-consuming Trump.";
    
    }
    
//we will get the articles from database
    
    public ArrayList<NewsArticle> recommend(NewsArticle likedArticle) throws IOException{      //send the NewsArticle object of the article liked by the user
        //articles=......      read all the articles which match the sentiment and the topic of discussion 
        NewsArticle s=new NewsArticle(a,"2");
        articles.add(s);
        ArrayList<String> impWords;
        impWords=likedArticle.impWords;
        
      
         
        HashMap<String,Integer> freq=new HashMap<String,Integer>();
        int flag=0;
        for(NewsArticle n:articles){
            for(String word:impWords){
               for(String token:n.tokens){
                   if(token.equalsIgnoreCase(word)){
                       freq.merge(n.articleId,1, Integer::sum);
                       flag=1;
                   }
               }
           }
        
        }
        
        if(flag==0){
            return null;
        }
        
        
        HashMap<NewsArticle,Double> rt_idf=new HashMap<>();
        
        for(NewsArticle na:articles){
            System.out.println(na.articleId);
            int f=freq.get(na.articleId.trim());
            double val=Math.log10(na.totalFreq/f);
            rt_idf.put(na, val);
        }
        
        ArrayList<NewsArticle> recommendList=new ArrayList<>();
        List<Map.Entry<NewsArticle, Double>> greatest = WordRating.findGreatest(rt_idf,5);
         
         for(Map.Entry<NewsArticle,Double> entry:greatest){
            for(Map.Entry<NewsArticle,Double> e:rt_idf.entrySet()){
                if(Double.compare(entry.getValue(),e.getValue())==0){
                    recommendList.add(e.getKey());
                    break;
                    
                }
            }
        }
        return recommendList;
    }
    
  }
