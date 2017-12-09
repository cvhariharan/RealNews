
import com.ayush.jdbc.AddArticles;
import com.ayush.jdbc.XmlParse;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class test {
    public static void main(String[] args) throws SAXException, IOException, MalformedURLException, ParserConfigurationException, Exception{
        
        XmlParse xm = new XmlParse();
        xm.startadd();
        AddArticles add = new AddArticles();
        add.createTable();
        add.post();
    }
}
