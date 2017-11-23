/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arko.javaproject;

import java.util.HashMap;


public class CountWords {
     public HashMap<String,Integer> count=new HashMap<>();
     int totalFreq;
    
    public void addWords(String word){
        word=word.toLowerCase();
        count.merge(word,1, Integer::sum); //add 1 to the frequency of the word whose id is repeated
        totalFreq=retTotalFreq();//update total frequency
    }
    public  int retTotalFreq(){
        totalFreq=0;
        for(int i:count.values()){
            totalFreq+=i;
        }
        return totalFreq;
    }
    
    public  int count(String word){   //returns the frequency of the sent word
        word=word.toLowerCase();
        return count.getOrDefault(word,0);
    }
}
