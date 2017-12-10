
package com.arko.javaproject;

import java.io.IOException;
import java.util.ArrayList;


public class JavaProject {
    
    
     public static void main(String[] args) throws IOException {
        String n2="hey there";
        String news="Yesterday (Friday), Modi gave a speech on Modi. He spoke only about himself,” Rahul said, referring to Modi’s speech in Himmatnagar, where he accused suspended Congress leader Mani Shankar Aiyar of giving a supari in Pakistan to get him removed.He continued the attack on Twitter and wrote, “90% % samay unhone apne aap pe bhashan dia, Narendra Modi ne Narendra Modi ke baare mein bhashan dia, aur kuch bacha hi nahi (90% time, he gave a speech on himself. Narendra Modi gave a speech on Narendra Modi. There is nothing else left.";
        String n1="Congress Vice President Rahul Gandhi on Saturday attacked Prime Minister Narendra Modi saying that the issue of 'development' has been missing from his speeches.Vikas (development) is missing from the Prime Minister's speeches this time. What is the reason,\" Gandhi asked on the day of the first phase of polling in Gujarat.He said the BJP government has been in power in Gujarat for the last 22 years.There was no manifesto till the campaigning for the first phase ended. Is rhetoric the new government in Gujarat? Gandhi said the Prime Minister has also not answered any of his last questions.I asked him ten questions on the Gujarat report cards, he did not reply. ";
        NewsArticle n=new NewsArticle(news,1+"");
        NewsArticle newss=new NewsArticle(n1,2+"");
        NewsArticle n2w=new NewsArticle(n2,3+"");
        ArrayList<NewsArticle> art=new ArrayList<>();
        art.add(n);
        art.add(newss);
        art.add(n2w);
        Recommend r=new Recommend(art);
        ArrayList<String> a=r.recommend(n);
        for(String s:a){
        System.out.println(s);
        
                }
        for(NewsArticle as:art){
            if(a.get(0).equalsIgnoreCase(as.articleId)){
                System.out.println(as.article);
            }
        }
     }
}

