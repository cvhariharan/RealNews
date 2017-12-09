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

/**
 *
 * @author asus
 */
public class AddArticles {
    
    
    
     final String tbname= "Artq";
     
     Connection connection;
     public AddArticles(){
         try{
         Connection conn = getConnection();
         this.connection=conn;
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
		 //String BOOK1 = "CASEBOOK";
		 //String AUTHOR = "ARTHUR";
		 //query = "INSERT INTO NewsArticles(Title , Author) VALUES ('BOOK1','AUTHOR')";
                 int i;
                 int id=readId();
                 
                 for(i=1;i<SiteScraper.newlist.size();i++){
                     String content = SiteScraper.newlist.get(i-1).content;
                     String sentiment=SentimentAnalysisClass.sentiment(content);
                     String timestamp       = SiteScraper.newlist.get(i-1).timestamp;
                     String url        = SiteScraper.newlist.get(i-1).url;
                     String title        = SiteScraper.newlist.get(i-1).title;
                     String authorName        = SiteScraper.newlist.get(i-1).authorName;
                     String category = SiteScraper.newlist.get(i-1).tags;
                     System.out.println(content);
                     //int likes = SiteScraper.newlist.get(i-1).likes;
                     //int fakes = SiteScraper.newlist.get(i-1).fakes;
                     int likes =1;
                     int fakes =1;
                    
                    try{ 
                        //String sentiment=SentimentAnalysisClass.sentiment(content);
                        Connection con = connection;
                        String qu1 = "SELECT MAX(id) FROM "+tbname+"";
                        
                        String qu = "INSERT INTO "+tbname+" (Content,Timestamp,URL,Title,Author,Category,Sentiment,NewsArticle,Likes,Fakes) VALUES('"+content+"','"+timestamp+"','"+url+"','"+title+"','"+authorName+"','"+category+"','"+sentiment+"',?,'"+likes+"','"+fakes+"')";
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
                 writeId(id);
    }
    public void update(String qry) throws SQLException{
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
            sql = "select * from "+tbname+" where 1=1";
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
            sql = "select * from "+tbname+" where '"+param+"'";
            Statement stm = connection.createStatement();
            res = stm.executeQuery(sql);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
       return res;
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
       
       
//    public static void writeObj(NewsArticle n)
//    {
//        OutputStream ops = null;
//		ObjectOutputStream objOps = null;
//		try {
//			ops = new FileOutputStream("temp.txt",false);//to overrride
//			objOps = new ObjectOutputStream(ops);
//			objOps.writeObject(n);
//			//objOps.flush();
//                        objOps.close();
//		} catch (FileNotFoundException e) {
//			System.out.println(e);
//		} catch (IOException e) {
//                        System.out.println(e);
//		} 
//		
//     }
      
//       public static ObjectInputStream readObj(){
//             InputStream fileIs = null;
//        ObjectInputStream objIs = null;
//        try {
//            fileIs = new FileInputStream("temp.txt");
//            objIs = new ObjectInputStream(fileIs);
//            //NewsArticle n = (NewsArticle) objIs.readObject();
//            return objIs;
//        } catch (FileNotFoundException e) {
//           System.out.println(e);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//             return null;
//     }
   
}
            

