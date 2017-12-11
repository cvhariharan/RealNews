/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;

/**
 *
 * @author asus
 */
import java.io.*;
import java.net.MalformedURLException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

//import org.apache.commons.io.IOUtils;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import org.apache.commons.lang3.SystemUtils;
import javax.xml.parsers.DocumentBuilder;
import org.jsoup.HttpStatusException;
import org.xml.sax.SAXParseException;

//import static org.apache.commons.lang3.StringEscapeUtils.escapeXml;


public class XmlParse
{

    //gets URLs from the RSS link
    //these URLs get passed to another function to get content from the returned links

    public void getURLfromXML(URL url) throws SAXException,SAXParseException, IOException, ParserConfigurationException
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        File file = new File("toi.xml");
        /*InputStream in = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        FileWriter writer = new FileWriter(file);
        String lineReadWithEscapes;
        while((lineReadWithEscapes = reader.readLine()) != null)
        {
            //lineReadWithEscapes = lineReadWithEscapes.replaceAll("\"","&quot;");
            //lineReadWithEscapes = lineReadWithEscapes.replaceAll("&","&amp;");
            //lineReadWithEscapes = lineReadWithEscapes.replaceAll("'","&apos;");

            //lineReadWithEscapes = escapeXml(lineReadWithEscapes);
            //lineReadWithEscapes = lineReadWithEscapes.replaceAll("&lt;","<");
            //lineReadWithEscapes = lineReadWithEscapes.replaceAll("&gt;",">");
            System.out.println(lineReadWithEscapes);
            writer.write(lineReadWithEscapes);
            writer.write(System.lineSeparator());
        }

        /*String urlWithEscapes = IOUtils.toString(url.openStream());
        urlWithEscapes = escapeXml(urlWithEscapes);
        System.out.println(urlWithEscapes);*/

        //InputStream inStream = new ByteArrayInputStream(urlWithEscapes.getBytes());
        //return;

        Document document = documentBuilder.parse(url.openStream());

        document.getDocumentElement().normalize();



        String htmlUrl = "";

        NodeList nodeList=document.getElementsByTagName("*");

        for (int i=0; i<nodeList.getLength(); i++)

        {

            Element element = (Element)nodeList.item(i);

            if(element.getTagName().equals("link"))

            {

                htmlUrl = element.getTextContent();

                URL urlHtml = new URL(htmlUrl);
                try
                {
                    new SiteScraper().siteScrape(urlHtml);
                } catch(HttpStatusException he)
                {
                    //null statement to avoid crashing the program in case of http errors like 404
                }
            }

        }

    }



    //public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException
    public void startadd(String URL) throws MalformedURLException, SAXException,SAXParseException, IOException, ParserConfigurationException
    {

        URL url = new URL(URL);

        getURLfromXML(url);



        //URL url = new URL("http://www.espn.com/blog/okc-thunder/post/_/id/2594/russell-westbrook-savors-sweet-win-after-string-of-losses-to-warriors");

        //new SiteScraper().siteScrape(url);

    }

}
