/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;
import java.sql.Connection;
import java.sql.*;
/**
 *
 * @author asus
 */
public class Jdbc {

	public Connection connection;
	public Jdbc(){
		
        try {
            Connection connection1 = getConnection();
            this.connection=connection1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }	

	public  void update(String query)throws Exception{
		try {
			Connection con = connection;
			//query = "update NewsArticles set 'Title' = ? where id = ?";
			PreparedStatement statement = con.prepareStatement(query);
			//statement.setString(1, "JOKER");
			//statement.setInt(2, 1);
			statement.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("UPDATE COMPLETED");
		}
	}
	public  ResultSet get(String query)throws Exception{
		try {
			
			//query = "SELECT Title,Author FROM NewsArticles";
			Connection con = connection;
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery();
                        while(result.next())                                    //display the records
			{
				System.out.print(result.getString("Title"));
				System.out.print(" ");
				System.out.println(result.getString("Author"));
			}
			System.out.println("ALL RECORS HAVE BEEN SELECTED");
			return result;  
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
		
	}
	public  void post(String query) throws Exception{
		 //String BOOK1 = "CASEBOOK";
		 //String AUTHOR = "ARTHUR";
		 //query = "INSERT INTO NewsArticles(Title , Author) VALUES ('BOOK1','AUTHOR')";
		 
		try {
			Connection con = connection;
			PreparedStatement post = con.prepareStatement(query);
		    post.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("INSERT COMPLETED");
		}
	}
	public  void createTable() throws Exception{
		try {
		Connection con = connection;
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS NewsArticles(id int NOT NULL AUTO_INCREMENT, Title MEDIUMTEXT, Content LONGTEXT, Author MEDIUMTEXT, Date MEDIUMTEXT, Link MEDIUMTEXT, fakevotes int, realvotes int, Guess BOOLEAN, RealClassification LINESTRING, PRIMARY KEY(id))");
	    create.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("FUCNTION COMPLETED");
		}
	}
	public  Connection getConnection() throws Exception{
         try {
        	 String driver = "com.mysql.jdbc.Driver";
        	 String url = "jdbc:mysql://sql12.freemysqlhosting.net/sql12206575";
        	 String username = "sql12206575";
        	 String password = "";
        	 Class.forName(driver);
        	 Connection mycon = DriverManager.getConnection(url, username, password);
        	 System.out.println("Connected");
        	 return mycon;
         }
        	 catch(Exception e){
        		 System.out.println(e);
        		 }
         return null;
	}
	}




