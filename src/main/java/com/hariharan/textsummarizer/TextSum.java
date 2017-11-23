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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import java.sql.*;
import static jdbcdemo.Driver.*;
/**
 *
 * @author thero
 */
public class TextSum {
    
    public String text; //Holds the normalize text
    private String[] allWords;
    private ArrayList<String> allNouns = new ArrayList<String>(); //Stores all tagged nouns
    private TreeMap<String, Integer> wordMap; //Stores the frequency of each word
    private HashMap<String, Double> sentences; //Holds all the sentences
    public HashMap<String, String> sentenceMap; //Holds normalized sentences with original sentences.
    private SentenceModel model = null;
    private POSModel pos = null;
    private static SentenceDetectorME sentBreak = null;
    private static  POSTaggerME posTagger = null;
    public TextSum() 
    {
        try
        {
            InputStream inputStream = new FileInputStream("model/en-sent.bin");
            InputStream inStream = new FileInputStream("model/en-pos-maxent.bin");
            model = new SentenceModel(inputStream);
            pos = new POSModel(inStream);
            sentBreak = new SentenceDetectorME(model);
            posTagger = new POSTaggerME(pos);
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(e.getCause());
        }
        
    }
    public void setText(String text)
    {
        this.text = normalize(text);
        this.sentences = new HashMap<>();
        this.sentenceMap = new HashMap<>();
        for(String s: sentBreak.sentDetect(text)) 
        {
            //System.out.println(s);
            this.sentences.put(normalize(s),1.0); //Initialize the score of each sentence as 1
            this.sentenceMap.put(normalize(s), s);
        }
        this.allWords = this.text.split(" ");
        String[] nouns = posTagger.tag(this.text.split(" "));
        for(int i = 0; i < nouns.length; i++)
        {
            if(nouns[i].equalsIgnoreCase("NN") || nouns[i].equalsIgnoreCase("NNP"))
            {
                this.allNouns.add(this.allWords[i]);
            }
        }
        this.wordMap = count(this.allWords);
        
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
    
    public TreeMap<String, Integer> count(String[] words)
    {
        TreeMap<String, Integer> tempMap = new TreeMap<>();
        for(String s: words)
        {
            if(s != null)
            {
            if(tempMap.get(s) == null)
                tempMap.put(s, 1);
            else
                tempMap.put(s, tempMap.get(s) + 1);
            }
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
                if(this.allNouns.contains(t))
                {
                    cosine += freq; 
                }
                
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
    
    public Map<String, Double> sortByValue(int count)
    {
        ValueComparator comp = new ValueComparator(this.sentences);
        Map<String, Double> sortedMap = new TreeMap(comp);
        sortedMap.putAll(this.sentences);
        System.out.println("SUMMARY...");
        int i = 0;
        /*for(String s: keys)
        {
            if(i!=count)
            {
                System.out.println(this.sentenceMap.get(s));
                i++;
            }
            else
                break;
        }*/
        for(String s: this.sentenceMap.keySet())
        {
            if( i <= count)
            {
                if(sortedMap.containsKey(s))
                {
                   System.out.println(this.sentenceMap.get(s));
                   i++;
                }
            }
            else
                break;
            
        }
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
        String news = "";
        TextSum c = new TextSum();
        Detector d = new Detector(c.normalize(news));
        System.out.println(d.detect());
        c.setText(news);
        for(String s: sentBreak.sentDetect(news))
        {
            c.computeCosine(s);
        }
        c.sortByValue(5);
        
       
    }
}
