
package com.arko.javaproject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;



public class WordRating implements Serializable{
    

     public static FreqImpWords rate(String[] tokens) throws FileNotFoundException, IOException{
        CountWords c=new CountWords();  
        ArrayList<String> distinctWords=new ArrayList<>();
        ArrayList<String> stopWords=new ArrayList<>();
         
             //READ STOP WORDS FROM DATASET
             BufferedReader br=new BufferedReader(new FileReader("stop-word-list.csv"));
             String line;
             while((line=br.readLine())!=null){
                 for(String word:line.split(",")){
                     stopWords.add(word);
                     //System.out.println(word +" ");
                 }
             }
             
        int flag;
        for(String word:tokens){    //add the words to hashmap
            word=word.toLowerCase();
            flag=0;
            for(String sw:stopWords){
                sw=sw.trim();       
                if(word.equalsIgnoreCase(sw)){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
            c.addWords(word);
            if(!distinctWords.contains(word)){  //to check if a word is hashmap
                distinctWords.add(word);
            }
            }
        }
        
        //get frequency of distinct words and add them to hashmap
        HashMap<String,Double> rt_idfRating =new HashMap<>();
        
        for(String word:distinctWords){
           double freq=c.count(word);
           double rt_idf=Math.log10(c.totalFreq/freq);
           rt_idfRating.put(word,rt_idf);
        }
       // for(Map.Entry<String,Double> entry:rt_idfRating.entrySet()){
         //   System.out.println(entry.getKey()+" "+entry.getValue());
       // }
        //get the words with maximum rating
        ArrayList<String> mostFreq=new ArrayList<>();
        
        //double maxValue= Collections.max(rt_idfRating.values());
        List<Map.Entry<String, Double>> greatest = findGreatest(rt_idfRating,20);
        
        for(Map.Entry<String,Double> entry:greatest){
            for(Map.Entry<String,Double> e:rt_idfRating.entrySet()){
                if(Double.compare(entry.getValue(),e.getValue())==0){
                    if(mostFreq.contains(e.getKey())){
                        continue;
          
                    }
                    else{
                    mostFreq.add(e.getKey());
                    break;
                    }
                }
            }
        }
        FreqImpWords f=new FreqImpWords(c.totalFreq,mostFreq);
       return f;  
    }
     public static String[] retTokens(String article) throws FileNotFoundException, IOException{
         String[] tokens; 
        FileInputStream file= new FileInputStream("en-token.bin");
        TokenizerModel tokenizerModel=new TokenizerModel(file);
        TokenizerME tokenizer=new TokenizerME(tokenizerModel);
        tokens=tokenizer.tokenize(article);
        return tokens;
     }
     
     
    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> findGreatest(HashMap<K, V> map, int n){ //method will work for all class refernces that implements the Comparable class
        
        Comparator<? super Map.Entry<K, V>> comparator = new Comparator<Map.Entry<K, V>>()
        {   //override the compare method to compare two HashMaps
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2)
            {
                V v0 = e1.getValue();
                V v1 = e2.getValue();
                return v0.compareTo(v1);
            }
        };
        PriorityQueue<Map.Entry<K, V>> highest = new PriorityQueue<>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet())
        {
            highest.offer(entry);
            while (highest.size() > n)
            {
                highest.poll();
            }
        }
        List<Map.Entry<K, V>> result = new ArrayList<>();
        while (highest.size() > 0)
        {
            result.add(highest.poll());
        }
        return result;
        
    }
}
