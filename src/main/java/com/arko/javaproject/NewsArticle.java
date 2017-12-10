
package com.arko.javaproject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class NewsArticle implements Serializable{
     public String article;
     public String articleId;
     String[] tokens;
     ArrayList<String> impWords; 
     int totalFreq;
     public String sentiment;
     
     public NewsArticle(String article,String articleId) throws IOException{
         
         this.article=article;
         this.sentiment=SentimentAnalysisClass.sentiment(this.article);
         this.articleId=articleId;
         tokens=WordRating.retTokens(this.article);
         FreqImpWords f=WordRating.rate(tokens);
         impWords=f.impWords;
         totalFreq=f.totalFreq;
          for(String name:FindNamesAndOrganisation.getNames(FindNamesAndOrganisation.getKeyWords(this.article)))
          impWords.add(name);
         for(String org:FindNamesAndOrganisation.getOrganisation(FindNamesAndOrganisation.getKeyWords(this.article)))
          impWords.add(org);
        for(String amt:FindNamesAndOrganisation.getAmount(FindNamesAndOrganisation.getKeyWords(this.article)))
          impWords.add(amt);
        for(String loc:FindNamesAndOrganisation.getLocation(FindNamesAndOrganisation.getKeyWords(this.article)))
          impWords.add(loc);
         
     }
     
}
