/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hariharan.textsummarizer;
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
    public static void main(String[] args) throws IOException
    {
       IdfCounter counter = new IdfCounter();
       
       String news = "The Food and Drug Administration has approved the first digital pill for the US which tracks if patients have taken their medication. The pill called Abilify MyCite, is fitted with a tiny ingestible sensor that communicates with a patch worn by the patient — the patch then transmits medication data to a smartphone app which the patient can voluntarily upload to a database for their doctor and other authorized persons to see. Abilify is a drug that treats schizophrenia, bipolar disorder, and is an add-on treatment for depression.\n" +
"\n" +
"The Abilify MyCite features a sensor the size of a grain of sand made of silicon, copper, and magnesium. An electrical signal is activated when the sensor comes into contact with stomach acid — the sensor then passes through the body naturally. A patch the patient wears on their left rib cage receives the signal several minutes after the pill is ingested. The patch then sends data like the time the pill was taken and the dosage to a smartphone app over Bluetooth. The patch also records activity levels, sleeping patterns, steps taken, activity, and heart rate, and must be replaced every seven days. The patient’s doctor and up to four other people chosen by the patient, including family members, can access the information. The patient can revoke access at any time."
               + "";
       counter.setText(news);
       for(String s: news.split("\\."))
            System.out.println(s+": "+counter.computeCosine(s));
       //counter.showSentences();
    }
}
