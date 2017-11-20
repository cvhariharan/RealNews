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
    Collector() throws IOException
    {
        Document doc = Jsoup.connect("http://teekhimirchi.in/world-en/").get(); //Satire news site. Change the last part of url to scrape other pages
        Elements newsLinks = doc.getElementsByClass("entry-title mh-posts-grid-title"); //Change the class ID for each website
        for(Element link: newsLinks)
        {
            Element t = link.select("a").first();
            System.out.println(t.attr("href"));
            urls.add(t.attr("href"));
        }
        NewScraper newScraper = new NewScraper(urls);
    }
    public static void main(String[] args) throws IOException
    {
        Collector c = new Collector();
    }
}
