
package com.arko.javaproject;



import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Recommend implements Serializable {
    
    ArrayList<NewsArticle>  articles;      //NewsArticle type object of articles
    String a;
            
    public Recommend(ArrayList<NewsArticle> articles){
       this.articles=articles;
    }
    
//we will get the articles from database
    
    public ArrayList<String> recommend(NewsArticle likedArticle) throws IOException{      //send the NewsArticle object of the article liked by the user
        //articles=......      read all the articles which match the sentiment and the topic of discussion 
        
        ArrayList<String> impWords;
        impWords=likedArticle.impWords;
                 
        HashMap<String,Integer> freq=new HashMap<>();
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
          
            int f=freq.get(na.articleId);
            double val=Math.log10(na.totalFreq/f);
            rt_idf.put(na, val);
        }
        
        ArrayList<String> recommendList=new ArrayList<>();
        List<Map.Entry<NewsArticle, Double>> greatest = WordRating.findGreatest(rt_idf,5);
         
         for(Map.Entry<NewsArticle,Double> entry:greatest){
            for(Map.Entry<NewsArticle,Double> e:rt_idf.entrySet()){
                if(Double.compare(entry.getValue(),e.getValue())==0){
                    NewsArticle n=(NewsArticle)e.getKey();
                    if(!n.articleId.equalsIgnoreCase(likedArticle.articleId)){
                    recommendList.add(n.articleId);
                    }
                    break;
                    
                }
            }
        }
        return recommendList;
    }
    
  }
