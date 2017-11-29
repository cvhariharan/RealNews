/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author asus
 */
public class addarticles {
     Connection connection;
     public addarticles(){
         Connection conn = getConnection();
         this.connection=conn;
     }
    
    public Connection getConnection(){
        try 
         {
        	 String driver = "com.mysql.jdbc.Driver";
        	 String url = "jdbc:mysql://localhost/database";
        	 String username = "root";
        	 String password = "Ayudrag11@";
        	 Class.forName(driver);
        	 Connection mycon = DriverManager.getConnection(url, username, password);
        	 System.out.println("Connected");
        	 return mycon;
         }
        	 catch(Exception e)
                 {
        		 System.out.println(e);
                 }
         return null;
    }
    public  void createTable() throws Exception{
		try {
		Connection con = connection;
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Articlesf(id int NOT NULL AUTO_INCREMENT, Timestamp TEXT, Title TEXT, Author TEXT, Category TEXT, Content TEXT, URL TEXT, PRIMARY KEY(id))");
                create.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("FUCNTION COMPLETED");
		}
	}
    public  void post() throws Exception{
		 //String BOOK1 = "CASEBOOK";
		 //String AUTHOR = "ARTHUR";
		 //query = "INSERT INTO NewsArticles(Title , Author) VALUES ('BOOK1','AUTHOR')";
                 int i;
                 for(i=0;i<SiteScraper.newlist.size();i++){
                     String content = SiteScraper.newlist.get(i).content;
                     String timestamp       = SiteScraper.newlist.get(i).timestamp;
                     String url        = SiteScraper.newlist.get(i).url;
                     String title        = SiteScraper.newlist.get(i).title;
                     String authorName        = SiteScraper.newlist.get(i).authorName;
                     String category = SiteScraper.newlist.get(i).tags;
                     String qu = "INSERT INTO ARTICLESf(Content,Timestamp,URL,Title,Author,Category) VALUES('"+content+"','"+timestamp+"','"+url+"','"+title+"','"+authorName+"','"+category+"')";
		try {
			Connection con = connection;
			PreparedStatement post = con.prepareStatement(qu);
                        post.executeUpdate();
		}catch(Exception e) {
                    System.out.println(e.getMessage());
			}
		finally {
			System.out.println("INSERT COMPLETED");
		}
	}
    }
    public ResultSet select(String table_name,String param1,String param2, String param3) throws SQLException{
        ResultSet res = null;
        String sql;
        try

        {
            sql = "select * from "+table_name+" where "+param1+" "+param2+" "+param3;
            Statement stm = connection.createStatement();
            res = stm.executeQuery(sql);

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
       return res;
    }
}
            

