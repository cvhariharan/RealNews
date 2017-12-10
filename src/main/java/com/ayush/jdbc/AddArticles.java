/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayush.jdbc;


import com.arko.javaproject.NewsArticle;
import com.arko.javaproject.ObjectString;
import com.arko.javaproject.SentimentAnalysisClass;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class AddArticles {
    
    
    
     final String tbname= "NewArticles";
     String Category;
     Connection connection;
     public AddArticles(){
          
         try{
         Connection conn = getConnection();
         this.connection=conn;
        
         }
         catch(Exception e){
             System.out.println(e.getMessage());
         }
     }
     public AddArticles(String Category){
         try{
         Connection conn = getConnection();
         this.connection=conn;
         this.Category=Category;
         writeId(0);
         }
         catch(Exception e){
             System.out.println(e.getMessage());
         }
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
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS "+tbname+" (id int NOT NULL AUTO_INCREMENT, Timestamp TEXT, Title TEXT, Author TEXT, Category TEXT, Content TEXT, URL TEXT, Sentiment TEXT, NewsArticle BLOB, likes int, fakes int, PRIMARY KEY(id))");
                create.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("FUCNTION COMPLETED");              
		}
	}
    
    
    public  void post() throws Exception{
		
                 int i,count;
                 int id=readId();
                 ResultSet rss = selectall(tbname);
                 
                 for(i=0;i<SiteScraper.newlist.size();i++)
                 {
                     count=0;
                      rss.beforeFirst();
                      while(rss.next()){
                          //System.out.println(rss.getString("Title"));
                     if((rss.getString("Title").trim()).equalsIgnoreCase((SiteScraper.newlist.get(i).title).trim()))
                     {
                         count++;
                         break;
                     }
                      }
                     if(count==0){
                     String content = SiteScraper.newlist.get(i).content;
                     String sentiment= SentimentAnalysisClass.sentiment(content);
                     String timestamp       = SiteScraper.newlist.get(i).timestamp;
                     String url        = SiteScraper.newlist.get(i).url;
                     String title        = SiteScraper.newlist.get(i).title;
                     String authorName        = SiteScraper.newlist.get(i).authorName;
                     //String category = SiteScraper.newlist.get(i).tags;
                     System.out.println(content);
                     //int likes = SiteScraper.newlist.get(i-1).likes;
                     //int fakes = SiteScraper.newlist.get(i-1).fakes;
                     int likes =1;
                     int fakes =1;
                    
                    try{ 
                        //String sentiment=SentimentAnalysisClass.sentiment(content);
                        Connection con = connection;
                        String qu1 = "SELECT MAX(id) FROM "+tbname+"";
                        
                        String qu = "INSERT INTO "+tbname+" (Content,Timestamp,URL,Title,Author,Category,Sentiment,NewsArticle,Likes,Fakes) VALUES('"+content+"','"+timestamp+"','"+url+"','"+title+"','"+authorName+"','"+Category+"','"+sentiment+"',?,'"+likes+"','"+fakes+"')";
			PreparedStatement post1 = con.prepareStatement(qu1);
                        PreparedStatement post =  con.prepareStatement(qu);
                        //post.setObject(1, newsa);
                        ResultSet rs = post1.executeQuery();
                        ResultSet rs2 = post1.getResultSet();
                        int lastid=0;
        
                        while(rs2.next()){
                            
                             lastid = rs2.getInt(1);
                        }
                     
                     NewsArticle newsa = new NewsArticle(content,lastid+"");
                     byte[] data=ObjectString.objectBytes(newsa);
                     
                      post.setObject(1,data );
                      post.executeUpdate();
		}catch(Exception e) {
                    System.out.println(e.getMessage());
			}
		finally {
			System.out.println("INSERT COMPLETED");
		}
                
                     }
	}
                 writeId(id);
    }
    
    
    public void  update(String qry) throws SQLException{
        try{
            PreparedStatement stm = connection.prepareStatement(qry);
            stm.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("update completed");
        }
    }
    
    
    
    public ResultSet selectall(String tbname) throws SQLException{
        ResultSet res = null;
        String sql;
        
        try

        {
            sql = "SELECT * FROM "+tbname+" ORDER BY id DESC";
            Statement stm = connection.createStatement();
            res = stm.executeQuery(sql);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
       return res;
    }
    
    
    public ResultSet select(String tbname,String param) throws SQLException{
        ResultSet res = null;
        String sql;
        try
        {
            sql = "SELECT * FROM "+tbname+" WHERE '"+param+"'";
            Statement stm = connection.createStatement();
            res = stm.executeQuery(sql);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
       return res;
    }
    
    
    
    public void delete(String tabname) throws SQLException{
        String sql;
        try{
            sql = "DELETE FROM "+tabname+" ";
            Statement stm = connection.createStatement();
            stm.executeUpdate(sql);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("delete completed");
        }
        
    }
    
    public ArrayList<NewsArticle> getnewsObject(AddArticles add) throws SQLException, IOException, ClassNotFoundException{
         ArrayList<NewsArticle> arrs = new ArrayList<NewsArticle>();
         ResultSet res = add.selectall(tbname);
         while(res.next()){
         arrs.add(ObjectString.getObject(res.getBytes("NewsArticle")));
         }
         return arrs;
    }
    
    public static void writeId(int id)
    {
        OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream("id.txt",false);//to overrride
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(id);
			objOps.flush();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
                        System.out.println(e);
		} 
		
     }
      
       public static int readId(){
             InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream("id.txt");
            objIs = new ObjectInputStream(fileIs);
            int i = (int) objIs.readObject();
            return i;
            
        } catch (FileNotFoundException e) {
           System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        
        }
             return 0;
     }
       
       

   
}
            

