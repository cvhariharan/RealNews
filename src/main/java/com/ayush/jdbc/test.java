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
public class test {
    
    public static void main(String arg[]) throws Exception
    {
        //Jdbc jd = new Jdbc();
        //jd.createTable();
        //jd.post("INSERT INTO NewsArticles(Title , Author) VALUES ('BOOK1','AUTHOR')");
        //SQLiteJDBC jd2 = new SQLiteJDBC();
        //jd2.database();
        //jd2.createTable();
        //jd2.insertTable();
        //jd2.select();
        addarticles art2 = new addarticles();
        art2.createTable();
        XmlParse xml = new XmlParse();
        xml.startadd();
        art2.post();
    }
}
