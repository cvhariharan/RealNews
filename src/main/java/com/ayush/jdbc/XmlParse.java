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
import java.io.IOException;
import java.net.MalformedURLException;

import java.net.URL;



import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

import org.w3c.dom.Document;

import org.xml.sax.SAXException;



import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import org.jsoup.HttpStatusException;
import org.xml.sax.SAXParseException;



public class XmlParse

{

    //gets URLs from the RSS link

    //these URLs get passed to another function to get content from the returned links

    public void getURLfromXML(URL url) throws SAXException,SAXParseException, IOException, ParserConfigurationException

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
