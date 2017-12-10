
import com.arko.javaproject.NewsArticle;
import com.arko.javaproject.ObjectString;
import com.arko.javaproject.Recommend;
import com.ayush.jdbc.AddArticles;
import com.ayush.jdbc.SQLiteJDBC;
import com.ayush.jdbc.XmlParse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Test {
    public static void main(String[] args) throws SAXException,SAXParseException, IOException, MalformedURLException, ParserConfigurationException, Exception{
        //SQLiteJDBC sq = new SQLiteJDBC();
        //sq.createTable();
        //sq.insertTable();
        //sq.login("soorya", "pass");
        XmlParse xm = new XmlParse();
        //xm.startadd("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms");
        //AddArticles add = new AddArticles("World");
        //add.createTable();
        //add.post();
        xm.startadd(" http://www.espn.com/espn/rss/news");
        AddArticles add1 = new AddArticles("Sports");
        add1.createTable();
        add1.post();
        AddArticles add2 = new AddArticles("SciTech");
        xm.startadd("http://www.anandtech.com/rss/");
        add2.createTable();
        add2.post();
        
        
        
        ArrayList<NewsArticle> arnews  = new ArrayList<NewsArticle>();
        
        arnews= add1.getnewsObject(add1);
        
       
       
        Recommend r=new Recommend(arnews);
        try{
            System.out.println(arnews.get(0).articleId);
        ArrayList<String> recommend=r.recommend(arnews.get(0));
        for(String n1:recommend){
           
            System.out.println(n1);
        }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        
        
        }
        
    }
