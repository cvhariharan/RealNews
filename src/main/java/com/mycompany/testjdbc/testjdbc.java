/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testjdbc;

import com.ayush.jdbc.Jdbc;

/**
 *
 * @author asus
 */
public class testjdbc {
    public static void main(String arg[]) throws Exception{
        Jdbc jd = new Jdbc();
        jd.createTable();
    }
}
