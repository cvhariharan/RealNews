/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakenews;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
/**
 *
 * @author thero
 */
public class NewScraper {
    
    public NewScraper(ArrayList<String> urls) throws IOException
    {
        BufferedWriter br = null;
        FileWriter articleTxt = null;
        for(String url: urls)
        {
            System.out.println(url);
            url = "https://timesofindia.indiatimes.com"+url;
            Document doc = Jsoup.connect(url).get();
            articleTxt = new FileWriter("timesofindia/"+doc.title().replaceAll("[^\\w]", " ")+".txt");
            br = new BufferedWriter(articleTxt);
            Elements article = doc.getElementsByClass("article_content clearfix");
            String content = article.text();
            /*for(Element para: article)
            {
                content += para.text();
            }*/
            System.out.println(content);
            br.write(content);
            br.close();
        }
    }
}
