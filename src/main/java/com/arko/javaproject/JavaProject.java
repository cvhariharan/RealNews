
package com.arko.javaproject;

import java.io.IOException;
import java.util.ArrayList;


public class JavaProject {
    
    
     public static void main(String[] args) throws IOException {
         
         String news="I appreciate the instinct to hold back, to follow the keep-quiet-about-the-new-guy tradition of former presidents. But these are not traditional times. They are unprecedented, and frankly, unpresidential.You deserve a break, but we are out here in this handbasket. There has been a press secretary hiding among bushes, an F.B.I. director who learned from television reports that he had been fired and Russians laughing in the Oval Office. Something called “the Mooch” happened for about 10 days, and back in May a man body slammed a reporter and then got elected to Congress Days ago, a tiki torch maker had to issue a serious political statement disavowing white supremacists. Somehow this is happening in The New York Times, not The Onion. All of which gives me the hopeful audacity to beg you once more unto the breach, to help unpack this madness.You have held the highest office in the land for the maximum time it can be occupied. You are an expert on constitutional law and an embodiment of the ideals expounded by the so-called American dream. Mr. Obama, you are the president who got up and sang “Amazing Grace” after the Charleston, S.C., killings. You are the president who shed tears in public after Sandy Hook. Now we are a country troubled by the looming possibility of a constitutional crisis, and hate groups are claiming the president as theirs. We need your voice. There is not a saner, more trustworthy opinion that many of us would rather hear.Continue reading the main story I recognize and respect your deliberate approach to navigating these fraught times, but this relentless subtlety has become wearisome. Mr. Obama, now is not the time to follow the keep-quiet rules while the new administration plays moral equivocator to a much aghast nation.It’s time for you to come back.I love that, after you posted on Twitter about the violence in Charlottesville, Va., you set a record for the most-liked tweet. But my joy at the news of your weighing in was complicated by your using a quotation, even one from Nelson Mandela. I looked to you for your good words. I’ll keep waiting because I know they will be worth it. But where are they In April, you spoke to students at the University of Chicago and identified your post-presidential calling to help “prepare the next generation of leadership to take up the baton” as “the single most important thing I can do.” I entirely agree. But your distance remains a weight on my mind.";
       // String news="A boy named Max Jones founded the company CEP which was later sold to Google in California  for 10,000,000 pounds";
        //String news="I dont want to live anymore!";
        // use the read distinct words as tokens after adding to count hashmap then use thier frequency to add to tf_idf hashmap 
         ArrayList<String> impWords;

         
          NewsArticle w=new NewsArticle(news,"1");
          impWords=w.impWords;
          Recommend r=new Recommend();
          ArrayList<NewsArticle> a=new ArrayList<>();
         a=r.recommend(w);
                   ArrayList<String> temp;
          temp=FindNamesAndOrganisation.getNames(FindNamesAndOrganisation.getKeyWords(news));
          
          for(String n:temp){
              impWords.add("NAME "+n);
          }
          
          temp=FindNamesAndOrganisation.getOrganisation(FindNamesAndOrganisation.getKeyWords(news));
           for(String n:temp){
              impWords.add("ORGANIZATION "+n);
          
           }
           temp=FindNamesAndOrganisation.getLocation(FindNamesAndOrganisation.getKeyWords(news));
           for(String n:temp){
              impWords.add("LOCATION "+n);
          
           }
           temp=FindNamesAndOrganisation.getAmount(FindNamesAndOrganisation.getKeyWords(news));
           for(String n:temp){
              impWords.add("AMOUNT "+n);
          
           }
           
           
           for(String ac:impWords){
              System.out.println(ac+" ");
          }
       
         
      String sentiment=SentimentAnalysisClass.sentiment(news);
      System.out.println(sentiment);
    }
}
