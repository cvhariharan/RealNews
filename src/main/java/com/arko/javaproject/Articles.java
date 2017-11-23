/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arko.javaproject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Integer;
import java.util.List;
import java.util.Map;


public class Articles {
    
    ArrayList<WordRating>  articles;      //WordRating type object of articles
    
            
    Articles(){
       articles=new ArrayList<>();
    }
    
    //we will get the articles from database
    
    public ArrayList<WordRating> recommend(WordRating likedArticle) throws IOException{      //send the WordRating object of the article liked by the user
        //articles=......      read all the articles which match the sentiment and the topic of discussion 
        
        ArrayList<String> impWords;
        impWords=likedArticle.rate();
        
        for(String name:FindNamesAndOrganisation.getNames(FindNamesAndOrganisation.getKeyWords(likedArticle.article)))
          impWords.add(name);
        for(String org:FindNamesAndOrganisation.getOrganisation(FindNamesAndOrganisation.getKeyWords(likedArticle.article)))
          impWords.add(org);
        for(String amt:FindNamesAndOrganisation.getAmount(FindNamesAndOrganisation.getKeyWords(likedArticle.article)))
          impWords.add(amt);
        for(String loc:FindNamesAndOrganisation.getLocation(FindNamesAndOrganisation.getKeyWords(likedArticle.article)))
          impWords.add(loc);
        
        
        HashMap<String,Integer> freq=new HashMap<>();
        
        for(WordRating wr:articles){
            for(String word:impWords){
               for(String token:wr.tokens){
                   if(token.equalsIgnoreCase(word)){
                       freq.merge(wr.articleId,1, Integer::sum);
                   }
               }
           }
        
        }
        
        HashMap<WordRating,Double> rt_idf=new HashMap<>();
        
        for(WordRating wr:articles){
            int f=freq.get(wr.articleId);
            double val=Math.log10(likedArticle.c.totalFreq/f);
            rt_idf.put(wr, val);
        }
        
        ArrayList<WordRating> recommendList=new ArrayList<>();
         List<Map.Entry<WordRating, Double>> greatest = WordRating.findGreatest(rt_idf,5);
         
         for(Map.Entry<WordRating,Double> entry:greatest){
            for(Map.Entry<WordRating,Double> e:rt_idf.entrySet()){
                if(Double.compare(entry.getValue(),e.getValue())==0){
                    recommendList.add(e.getKey());
                    break;
                    
                }
            }
        }
        return recommendList;
    }
    
  }
