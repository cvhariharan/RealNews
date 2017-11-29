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
import java.sql.*;

public class SQLiteJDBC {
  private Connection c;
  public SQLiteJDBC() {
    Connection conn = database();
    this.c=conn;
}
  public Connection database() {
       Connection mycon=null;
      try {
         Class.forName("org.sqlite.JDBC");
        
         mycon = DriverManager.getConnection("jdbc:sqlite:users.db");
      } catch ( Exception e ) {
         System.out.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }finally{
      System.out.println("Opened database successfully");
      }
      return mycon;
   }
 
    public void createTable(){
      try {
         Statement stmt2 = null;
         
         stmt2 = c.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS USERSnew (NAME TEXT , EMAILID TEXT , PASSWORD TEXT )"; 
         System.out.println("test");
         stmt2.executeUpdate(sql);
          System.out.println("test2");
         stmt2.close();
          System.out.println("test3");
         c.close();
          System.out.println("test4");
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
    public void insertTable(){
       
        try { 
         Connection con = database();
         Statement stmt = con.createStatement();
         String sql = "INSERT INTO USERSnew (NAME,EMAILID,PASSWORD) " +
                        "VALUES ('AYUSH', 'am@gmail.com','hehe');"; 
         stmt.executeUpdate(sql);
         stmt.close();
         //con.commit();
         con.close();
      } catch ( Exception e ) 
      {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Records created successfully");
   }
    public ResultSet select(){
         ResultSet rs=null;
        try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:users.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");
      Statement stmt;
      stmt = c.createStatement();
      rs = stmt.executeQuery( "SELECT * FROM USERSnew;" );
      
      while ( rs.next() ) {
      
         String  name = rs.getString("NAME");
         String  email  = rs.getString("EMAILID");
         String  password = rs.getString("PASSWORD");
         
         System.out.println( "NAME = " + name );
         System.out.println( "EMAIL = " + email );
         System.out.println( "PASSWORD = " + password );
        
         System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
      
   } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
   }
   System.out.println("Operation done successfully");
   return rs;
    }

    }
    

   

