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
    private HashMap<String, Integer> sentences; //Holds all the sentences
    public void setText(String text)
    {
        this.text = normalize(text);
        this.sentences = new HashMap<>();
        for(String s: text.split("\\.")) //Escape for literal dot.
        {
            //System.out.println(s);
            this.sentences.put(normalize(s),1); //Initialize the score of each sentence as 1
        }
        this.allWords = this.text.split(" ");
        this.wordMap = count(allWords);
        
    }
    
    public String normalize(String text)
    {
        String normalized = text.replaceAll("[^a-zA-Z]", " ").replaceAll(" +", " ").toLowerCase();
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
        HashSet<String> inputVector = (HashSet)vectorize(input);
        HashSet<String> sentenceVector = null;
        Set<String> allSentences = this.sentences.keySet();
        double cosineSum = 0; //Sum of cosines of the input with all sentences.
        for(String eachSentence: allSentences)
        {
            //System.out.println(eachSentence);
            double cosine = 0;
            int count = 0;
            sentenceVector = (HashSet)vectorize(eachSentence);
            count = sentenceVector.size() + inputVector.size();
            sentenceVector.retainAll(inputVector);
            for(String t: sentenceVector)
            {
                //System.out.println(t);
                int freq = wordMap.get(t);
                cosine += Math.log10(count/freq);
            }
            cosineSum += cosine;
        }
        return cosineSum;
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
    public static void main(String[] args) throws IOException
    {
       IdfCounter counter = new IdfCounter();
       counter.setText("This is a foo bar sentence. This sentence is similar to a foo bar sentence.");
       System.out.println(counter.computeCosine(""));
    }
}
