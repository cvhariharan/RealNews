
package com.arko.javaproject;



import com.ayush.jdbc.AddArticles;
import com.ayush.jdbc.Jdbc;
import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Recommend {
    
    ArrayList<NewsArticle>  articles;      //NewsArticle type object of articles
    String a;
            
    Recommend(){
       articles=new ArrayList<>();
      
    }
    
//we will get the articles from database
    
    public ArrayList<NewsArticle> recommend(NewsArticle likedArticle) throws IOException{      //send the NewsArticle object of the article liked by the user
        //articles=......      read all the articles which match the sentiment and the topic of discussion 
        
        ArrayList<String> impWords;
        impWords=likedArticle.impWords;
        
        AddArticles art=new AddArticles();
        try {
            ResultSet res=art.select("ARTICLESf","id='"+likedArticle.articleId+"'");
            String category=res.getString("category");
            String sentiment=res.getString("Sentiment");
            String[] a=sentiment.split(" ");
            
            
            ResultSet rs=art.select("ARTICLESf","Category='"+category+"',Sentiment like \"%"+a[0]+"%\"");
            
         do{
            NewsArticle n=ObjectString.getObject((byte[])rs.getBytes("NewsArticle"));
            articles.add(n);
         } while (rs.next());

            rs.getBinaryStream("NewsArticle");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        
         
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
