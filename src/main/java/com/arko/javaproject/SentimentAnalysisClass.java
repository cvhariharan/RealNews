
package com.arko.javaproject;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;



public class SentimentAnalysisClass {
    
    public  static String sentiment(String news){   //used aylien api which is a good api and returns sentment with rating
        TextAPIClient client = new TextAPIClient("85cb56bf", "8c593d912708430f1da18bb0bb815980");
        SentimentParams.Builder builder=SentimentParams.newBuilder();
        builder.setText(news);
        try {
            Sentiment s=client.sentiment(builder.build());
            return s.toString();
        } catch (TextAPIException ex) {
            ex.printStackTrace();
        }
        return null;
    }
       
}
