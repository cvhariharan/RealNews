package jdbcdemo;
import java.sql.*;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//getConnection();
		//createTable();
		//post("hey");
		//update("first");
		//get("first");
	}
	public static void update(String query)throws Exception{
		try {
			Connection con = getConnection();
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
	public static ResultSet get(String query)throws Exception{
		try {
			
			//query = "SELECT Title,Author FROM NewsArticles";
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
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
	public static void post(String query) throws Exception{
		 //String BOOK1 = "CASEBOOK";
		 //String AUTHOR = "ARTHUR";
		 //query = "INSERT INTO NewsArticles(Title , Author) VALUES ('BOOK1','AUTHOR')";
		 
		try {
			Connection con = getConnection();
			PreparedStatement post = con.prepareStatement(query);
		    post.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("INSERT COMPLETED");
		}
	}
	public static void 	createTable() throws Exception{
		try {
		Connection con = getConnection();
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS NewsArticles(id int NOT NULL AUTO_INCREMENT, Title MEDIUMTEXT, Content LONGTEXT, Author MEDIUMTEXT, Date MEDIUMTEXT, Link MEDIUMTEXT, fakevotes int, realvotes int, Guess BOOLEAN, RealClassification LINESTRING, PRIMARY KEY(id))");
	    create.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			}
		finally {
			System.out.println("FUCNTION COMPLETED");
		}
	}
	public static Connection getConnection() throws Exception{
         try {
        	 String driver = "com.mysql.jdbc.Driver";
        	 String url = "jdbc:mysql://localhost/database";
        	 String username = "root";
        	 String password = "Ayudrag11@";
        	 
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


