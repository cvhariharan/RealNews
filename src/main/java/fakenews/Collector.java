/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fakenews;
import com.hariharan.textsummarizer.TextSum;
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
        Document doc = Jsoup.connect("https://timesofindia.indiatimes.com/world/rest-of-world").get(); //Change the last part of url to scrape other pages
        Elements newsLinks = doc.select("#c_articlelist_stories_2 > ul.list5.clearfix"); //Change the class ID for each website
        Elements li = newsLinks.select("li");
        for(Element link: li)
        {
            Element t = link.select("a").first();
            System.out.println(t.attr("href"));
            urls.add(t.attr("href"));
        }
        NewScraper newScraper = new NewScraper(urls);
    }
    public static void main(String[] args) throws IOException, Exception
    {
        TextSum c = new TextSum();
        Detector d = new Detector(c.normalize("The Tsukuba Express was scheduled to depart Tokyo for Tsukuba at 9:44:40. Instead, it left at 9:44:20. If you’re thinking, “the horror!” then you’ll be glad to hear that management for the Japanese rail company has formally apologized for the train’s 20-seconds-early departure.\n" +
"\n" +
"“The crew did not sufficiently check the departure time and performed the departure operation,” it said in an official statement and “sincere” apology that led to bemusement on the internet.\n" +
"\n" +
"“I once had an Israeli bus driver laugh at me after he closed the door on my hips and drove off with my legs hanging out of the bus. I am so envious of Japan right now,” one person tweeted. The BBC reports that it’s “rare” for trains in Japan to be off-schedule, and another tweet highlights that fact, noting, “Have been on a train in Japan where I heard an apology for the train running one minute late.”\n" +
"\n" +
"The statement notes that there were no official customer complaints lodged about the early departure from a station just north of Tokyo, but apologizes if any customers missed the train by 20 seconds and were forced to wait for the next one—four minutes later, reports Bloomberg.\n" +
"\n" +
"Yahoo News UK notes that Japan’s trains are “among the most reliable in the world,” offering a mournful comparison to Britain, where “commuters are only too acquainted with the daily frustration of late trains on the way to work.” And the New York Daily News notes things aren’t much better in that newspaper’s fair city, where subway delays collectively add up to 34,900 hours on the average weekday.\n" +
"\n" +
"The Japanese company’s apology “is exactly what Japan is about,” sums up one Twitter user. (Meanwhile, in NYC’s transit department, leaking corpses on the job.)"));
        System.out.println(d.detect());
    }
}
