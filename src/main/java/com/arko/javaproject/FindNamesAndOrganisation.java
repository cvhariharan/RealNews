/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arko.javaproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;


public class FindNamesAndOrganisation implements Serializable{
   
   
    
    
    public static ArrayList<String> getNames(String a[]){
         TokenNameFinderModel nameModel;
         NameFinderME nameFinder;
         FileInputStream file;
         try {
            file= new FileInputStream("en-ner-person.bin");
            nameModel=new TokenNameFinderModel(file);
            nameFinder=new NameFinderME(nameModel);
            
            Span n[]=nameFinder.find(a);
            //String names[]=Span.spansToStrings(n,a);
            ArrayList<String> names=new ArrayList<>();
            for(String name:Span.spansToStrings(n,a)){
                names.add(name);
            }
            
            return names;
            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
         return null;
    }
    
     public static ArrayList<String> getOrganisation(String a[]){
         TokenNameFinderModel nameModel;
         NameFinderME nameFinder;
         FileInputStream file;
         try {
            file= new FileInputStream("en-ner-organization.bin");
            nameModel=new TokenNameFinderModel(file);
            nameFinder=new NameFinderME(nameModel);
            
            Span n[]=nameFinder.find(a);
            //String names[]=Span.spansToStrings(n,a);
            ArrayList<String> names=new ArrayList<>();
            for(String name:Span.spansToStrings(n,a)){
                names.add(name);
            }
            
            return names;
            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
         return null;
    }
     public static ArrayList<String> getAmount(String a[]){
         TokenNameFinderModel nameModel;
         NameFinderME nameFinder;
         FileInputStream file;
         try {
            file= new FileInputStream("en-ner-money.bin");
            nameModel=new TokenNameFinderModel(file);
            nameFinder=new NameFinderME(nameModel);
            
            Span n[]=nameFinder.find(a);
            //String names[]=Span.spansToStrings(n,a);
            ArrayList<String> names=new ArrayList<>();
            for(String name:Span.spansToStrings(n,a)){
                names.add(name);
            }
            
            return names;
            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
         return null;
    }
     public static ArrayList<String> getLocation(String a[]){
         TokenNameFinderModel nameModel;
         NameFinderME nameFinder;
         FileInputStream file;
         try {
            file= new FileInputStream("en-ner-location.bin");
            nameModel=new TokenNameFinderModel(file);
            nameFinder=new NameFinderME(nameModel);
            
            Span n[]=nameFinder.find(a);
            //String names[]=Span.spansToStrings(n,a);
            ArrayList<String> names=new ArrayList<>();
            for(String name:Span.spansToStrings(n,a)){
                names.add(name);
            }
            
            return names;
            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
         return null;
    }
     
     static public String[] getKeyWords(String news) throws FileNotFoundException, IOException{
         FileInputStream file= new FileInputStream("en-token.bin");
        TokenizerModel tokenizerModel=new TokenizerModel(file);
        TokenizerME tokenizer=new TokenizerME(tokenizerModel);
        String[] tokens=tokenizer.tokenize(news);
        return tokens;
     }
    
}
