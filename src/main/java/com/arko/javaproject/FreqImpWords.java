/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arko.javaproject;

import java.util.ArrayList;


public class FreqImpWords {   // used this class two encapsulate data so as to return two values from a function.
   int totalFreq;
   ArrayList<String> impWords;

    public FreqImpWords(int totalFreq, ArrayList<String> impWords) {
        this.totalFreq = totalFreq;
        this.impWords = impWords;
    }
   
}
