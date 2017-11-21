/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hariharan.textsummarizer;
import fakenews.Collector;
import fakenews.Detector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author thero
 */
public class IdfCounter {
    
    public String text; //Holds the normalize text
    private String[] allWords;
    private HashMap<String, Integer> wordMap; //Stores the frequency of each word
    private HashMap<String, Double> sentences; //Holds all the sentences
    public void setText(String text)
    {
        this.text = normalize(text);
        this.sentences = new HashMap<>();
        for(String s: text.split("\\.")) //Escape for literal dot.
        {
            //System.out.println(s);
            this.sentences.put(normalize(s),1.0); //Initialize the score of each sentence as 1
        }
        this.allWords = this.text.split(" ");
        this.wordMap = count(allWords);
        
    }
    
    public String normalize(String text)
    {
        String normalized = text.replaceAll("[^a-zA-Z]", " ").replaceAll(" +", " ").toLowerCase().trim();
        return normalized;
    }
    
    public void printText()
    {
        System.out.println(this.text);
    }
    
    public HashMap<String, Integer> count(String[] words)
    {
        HashMap<String, Integer> tempMap = new HashMap<>();
        for(String s: words)
        {
            if(tempMap.get(s) == null)
                tempMap.put(s, 1);
            else
                tempMap.put(s, tempMap.get(s) + 1);
        }
        return tempMap;
    }
    
    public void showFreq()
    {
        Set s = wordMap.keySet();
        Iterator i = s.iterator();
        while(i.hasNext())
        {
            String key = (String)i.next();
            System.out.println(key+":"+wordMap.get(key));
        }
    }
    
    public double computeCosine(String input)
    {
        //Finds the cosine between the input and all the other sentences in the document
        input = normalize(input);
        HashSet<String> inputVector = (HashSet)vectorize(input);
        HashSet<String> sentenceVector = null;
        Set<String> allSentences = this.sentences.keySet();
        double cosineSum = 0; //Sum of cosines of the input with all sentences.
        for(String eachSentence: allSentences)
        {
            if(!input.equals(eachSentence))
            {
            double cosine = 0;
            int count = 0;
            sentenceVector = (HashSet)vectorize(eachSentence);
            count = sentenceVector.size() + inputVector.size();
            sentenceVector.retainAll(inputVector);
            for(String t: sentenceVector)
            {
                int freq = wordMap.get(t);
                if(freq == 0)
                    freq = 1;
                cosine += Math.abs(Math.log10((double)count/(double)freq));
            }
            cosineSum += cosine;
            
        if(this.sentences.containsKey(input))
        {
            double score = this.sentences.get(input);
            this.sentences.put(input, score+cosineSum);
        }
        
        return cosineSum;
        }
        }
        
        return 0;
    }
    
    public Set<String> vectorize(String text)
    {
        Set<String> vector = new HashSet<>();
        for(String s: text.split(" "))
        {
            vector.add(s);
        }
        return vector;
    }
    
    public Map<String, Double> sortByValue()
    {
        ValueComparator comp = new ValueComparator(this.sentences);
        Map<String, Double> sortedMap = new TreeMap(comp);
        sortedMap.putAll(this.sentences);
        Set<String> keys = sortedMap.keySet();
        System.out.println("SORTED...");
        for(String s: keys)
            System.out.println(keys);
        return sortedMap;
    }
    public void showSentences()
    {
        Set<String> keys = this.sentences.keySet();
        Iterator i = keys.iterator();
        while(i.hasNext())
        {
            String sentence = (String)i.next();
            System.out.println(sentence+": "+this.sentences.get(sentence));
        }
    }
    public static void main(String[] args) throws IOException, Exception
    {
       /*IdfCounter counter = new IdfCounter();
       
       String news = "One thing is assured any time the pope \\u2013 any pope \\u2013 talks about a \\u201ccontroversial\\u201d topic. People will listen. Even people who have eschewed the Catholic Church and all she stands for if for no other reason to ridicule and lecture Catholics. Well, Pope Francis made a change, again, in the absolution procedures for the sin of procuring, assisting in or causing an abortion, and all hell has broken loose. Unlike earlier in the year when a procedural convenience was made permanent, this change actually is a change.\\n\\nAllow me to explain.\\n\\nIn the full list of sins for Catholics, abortion is one of a handful that are so heinous, they are in a category that automatically incurs what is called latae sententiae excommunication. That means that \\u201cby the act\\u201d the human puts himself or herself OUTSIDE of the Christian Community. This is not a formal bull of excommunication that is posted for heresy (teaching or espousing that which is against the Church in one way or another), but a very personal sort of separation. However, in order to have the excommunication lifted \\u2013 this is a spiritual remedy, not a punishment \\u2013 which would allow for absolution (it is not granted if one is excommunicated), there is some administrative paperwork that needs to happen.\\n\\nEarlier in his pontificate, Pope Francis officially transferred the paperwork procedure to the parishes for abortion, rather than sending it through the bishops\\u2019 offices, a practice that had been in place in the United States and other western nations infested with modernism for decades. It just made things smoother. With Monday\\u2019s move, Canon Law actually will have to be changed as the formal procedure of lifting the excommunication has been dropped.\\n\\n\\u201cI wish to restate as firmly as I can that abortion is a grave sin, since it puts an end to an innocent life,\\u201d the pope wrote. \\u201cIn the same way, however, I can and must state that there is no sin that God\\u2019s mercy cannot reach and wipe away when it finds a repentant heart seeking to be reconciled with the Father.\\u201d Speaking to reporters during a Vatican news conference Nov. 21, Archbishop Rino Fisichella said procuring an abortion still results in automatic excommunication the very moment the procedure is carried out. Sacramental absolution, therefore, is not just forgiving the sin of abortion, but also means \\u201cthe excommunication is removed,\\u201d he said. Now that all priests have been given the faculty to lift the excommunication and grant absolution, the Code of Canon Law will have to be updated, said the archbishop, who is president of the Pontifical Council for Promoting New Evangelization, the office that organized events for the Year of Mercy.\\n\\nPope Francis, truly, has picked up the baton of a number of late 19th century and 20th century figures in Catholicism when it comes to his message on God\\u2019s mercy. The main proponent of the message was Pope Saint John Paul II, along with a Polish nun known to him during his life who was canonized during his pontificate. Her name is St. Faustina, the Divine Mercy image to the left is taken from an entry in her papers, and her diary speaks of Divine Mercy in terms many never considered before. (It\\u2019s a tough, but good read.) By declaring the just completed Year of Mercy, Pope Francis reminds the flock that returning to God is always possible, yes, even for those who are involved with abortion. The Church is a hospital for sinners, not an exclusive club.\\n\\nIn Catholicism, though, there is a catch to receiving absolution no matter what your sins. Confession must be\\n\\nmade, and the Penance (the real name of the Sacrament) must be carried out with a contrite heart. We are supposed to be sorry for our sins. That\\u2019s something a lot of us need to work on.\\n\\nComments"
               + "";
       counter.setText(news);
       for(String s: news.split("\\."))
            System.out.println(s+": "+counter.computeCosine(s));
       counter.sortByValue();
       //counter.showSentences();*/
        IdfCounter c = new IdfCounter();
        Detector d = new Detector(c.normalize("EW DELHI: HP has expanded its gaming laptop product portfolio with the launch of Omen 15 and Omen 17. The company claims that the new devices are designed for e-sports athletes and competitive gamers.\n" +
"The laptops come with 10 Series GTX graphics from Nvidia and high-resolution displays with optional G-Sync technology for fast refresh rates. The HP Omen 15 comprises of the CE070TX, CE071TX, CE072TX, CE073TX, and CE074TX models. The laptop runs Windows 10 operating system and is powered by seventh-generation Intel Core i5 processor. It offers 8GB of DDR4 RAM and 1TB Serial ATA HDD with Nvidia GeForce GTX 1050.\n" +
"The high-end version of the series is powered by seventh-generation Intel Core i7 processor paired with 16GB of RAM and 1TB HDD and the same Nvidia GeForce GTX 1060 graphics. The device sports a 15.6-inch LED display with 1920x1080 pixel resolution.\n" +
"On the other hand, the company has introduced two models under the Omen 17 series -- AN009TX and AN010 TX. The devices boast of a 17.3-inch LED backlit display with full HD resolution. The laptops are powered by seventh-generation Intel Core i7 coupled with 16GB of DDR4 RAM and 1TB of Serial ATA HDD with Nvidia GeForce GTX 1070 graphics.\n" +
"Both the gaming laptops sports red backlit keyboard with numeric keypad and multi-touch gesture support on the trackpad. The devices come equipped with Bang & Olufsen audio for an improved audio experience during the gaming sessions.\n" +
"Along with the gaming notebooks, the company has also launched Omen by HP Mouse 600 which offers an ergonomic right hand design and rubber grips for added control and comfort. The device offers full range of DPI levels from 800 -12, 000 and a new optical sensor designed for eSports.\n" +
"The company also introduced HP Headset 800 with 53 mm driver and it has universal compatibility with 3.5mm-to-single 4 pole adapters.\n" +
"Talking about the prices, the HP Omen 15 and Omen 17 comes with a starting price tag of Rs 80,990 and 150,990. While, the Omen Mouse 600 and Omen 800 Headset are priced at Rs 4,999 and Rs 6,999 respectively."));
        System.out.println(d.detect());
        Collector col = new Collector();
    }
}
