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
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author thero
 */
public class IdfCounter {
    
    private String text;
    private String[] allWords;
    private HashMap<String, Integer> wordMap;
    public void setText(String text)
    {
        this.text = normalize(text);
        this.allWords = this.text.split(" ");
        this.wordMap = count(allWords);
        
    }
    
    public String normalize(String text)
    {
        String normalized = text.replaceAll("[^a-zA-Z ]", " ").replaceAll(" +", " ").toLowerCase();
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
    public static void main(String[] args) throws IOException
    {
       IdfCounter counter = new IdfCounter();
       //counter.showFreq();
       File dir = new File("data/business");
       BufferedReader r;
       FileReader fr;
       String t;
       String s = "";
       File[] files = dir.listFiles();
       for(File f: files)
       {
    
           
           try
           {
               fr = new FileReader(f.getAbsolutePath());
               System.out.println(f.getAbsolutePath());
               r = new BufferedReader(fr);
               while((t = r.readLine()) != null)
               {
                   s += t;
               }
           }
           catch(FileNotFoundException e)
           {
               System.out.println(e.getMessage());
           }
           
           
       }
       counter.setText(s);
       counter.showFreq();
    }
}
