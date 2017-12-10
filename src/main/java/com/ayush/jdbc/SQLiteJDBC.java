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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.Scanner;
import sun.security.util.Password;


//import org.mindrot
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
   
    public int getlikedArticle(String username) throws SQLException{
        Statement stmt = c.createStatement();
        String sq = "SELECT FROM USERnew where NAME LIKE '" + username +"';";
        
        ResultSet rs = stmt.executeQuery(sq);
        return rs.getInt("Likedarticle");
    }
 
    public void createTable(){
      try {
         Statement stmt2 = null;
         
         stmt2 = c.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS USERSnew (NAME TEXT , PASSWORD TEXT , Likedarticle int)"; 
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
    public void insertTable(String name,String password){
       
        try { 
         //Connection con = database();
         Statement stmt = c.createStatement();
         /*String name,password;
         System.out.println("Enter Name");
         Scanner input = new Scanner(System.in);
         name = input.nextLine();
        
          System.out.println("Enter Password");
         password = input.nextLine();*/
         
         password = BCrypt.hashpw(password,BCrypt.gensalt());
         String sql = "INSERT INTO USERSnew (NAME,PASSWORD) " +
                        "VALUES ('" + name + "','" + password +"');";
         stmt.executeUpdate(sql);
         stmt.close();
         //con.commit();
         c.close();
      } catch ( Exception e ) 
      {
          e.printStackTrace();
         //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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
         
         String  password = rs.getString("PASSWORD");
         
         System.out.println( "NAME = " + name );
        
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

    public boolean login(String username, String password)
    {
        ResultSet rs=null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:users.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            Statement stmt;
            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT * FROM USERSnew where NAME LIKE '" + username +"';" );

            while(rs.next())
            {
                String dbPassword = rs.getString("PASSWORD");
                String dbUsername = rs.getString("NAME");
                if(username.equals(dbUsername) && BCrypt.checkpw(password,dbPassword))
                {
                    System.out.println("login successful");
                    return true;
                }
                else if(username.equals(dbUsername))
                {
                    System.out.println("login failed");
                    return false;
                }
            }

                insertTable(username, password);
                System.out.println("user created and logged in");

            //System.out.println("returning false");
            rs.close();
            stmt.close();
            c.close();
            return true;
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } catch (ClassNotFoundException c)
        {
            c.printStackTrace();
        }

        System.out.println("Operation done successfully");

        return false;
    }
  }

    