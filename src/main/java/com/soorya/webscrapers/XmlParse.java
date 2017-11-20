package com.soorya.webscrapers;

import java.io.IOException;
import java.net.URL;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

public class XmlParse
{
    //gets URLs from the RSS link
    //these URLs get passed to another function to get content from the returned links
    public void getURLfromXML(URL url) throws SAXException, IOException, ParserConfigurationException
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
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
                new SiteScraper().siteScrape(urlHtml);
            }
        }


    }
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException
    {
        URL url = new URL("https://www.anandtech.com/rss/");
        new XmlParse().getURLfromXML(url);
        //new SiteScraper().siteScrape(url);
    }
}