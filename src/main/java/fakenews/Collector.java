/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakenews;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author thero
 */
public class Collector {
    
    private ArrayList<String> urls = new ArrayList<String>();
    public Collector() throws IOException
    {
        Document doc = Jsoup.connect("https://timesofindia.indiatimes.com/india").get(); //Change the last part of url to scrape other pages
        Elements newsLinks = doc.select("#c_wdt_list_1 > ul.list5.clearfix"); //Change the class ID for each website
        Elements li = newsLinks.select("li");
        for(Element link: li)
        {
            Element t = link.select("a").first();
            System.out.println(t.attr("href"));
            urls.add(t.attr("href"));
        }
        NewScraper newScraper = new NewScraper(urls);
    }
    public static void main(String[] args) throws IOException
    {
        
    }
}
