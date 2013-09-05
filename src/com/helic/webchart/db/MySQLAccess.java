package com.helic.webchart.db;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.helic.webchart.Transaction;

/**
 * This Class acts as a Database Access Object for retrieving records from Database.
 * Assume the user uses MySQL database.
 */
public class MySQLAccess {
	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  
	  // Load DB info for Properites file
	  private static Properties prop = null;
	  private static final String PROP_FILE = "config.properties";
	  private static final String DB_HOST_KEY = "db_host_address";
	  private static final String DB_USER_KEY = "db_user";
	  private static final String DB_PASSWORD_KEY = "db_password";
	  
	  // Temp database properties values, will be overrided by settings in config.properties file
	  private static String DB_HOST = "jdbc:mysql://localhost:3306/webchart";
	  private static String DB_USER = "root";
	  private static String DB_PASSWORD = "password";
	  
	  /**
	   * Fetch useful data column in table transactions of a specific customer
	   */
	  public List<Transaction> readTransactions(String customer) throws Exception{
		 ResultSet resultSet = null;
		 List<Transaction> results = new LinkedList<Transaction>();
		 try {
		      connect();
	      // Get the list of transactions with the selected customer
		      preparedStatement = connect.prepareStatement("SELECT * FROM transactions WHERE customer = ?");
		      preparedStatement.setString(1, customer);
		      resultSet = preparedStatement.executeQuery();
		      
		      // Loop through all rows
		      while (resultSet.next()) {
		    	  Date datetime = resultSet.getDate("datetime");
		    	  String product = resultSet.getString("product");
				  int quantity = resultSet.getInt("quantity");
				  Double totalvalue = resultSet.getDouble("totalvalue");
				  
				  Transaction row = new Transaction(datetime,product,quantity,totalvalue);
				  results.add(row);
		      }
		 } catch (Exception e) {
		      throw e;
		 } finally {
		      close(null);
		 }
		 return results;
	  }
	  
	  /**
	   * For Chart Title display
	   */
	  public String printCustomerFullName(String customer) {
		 String message = null;
		 ResultSet resultSet = null;
		 try {
		  connect();
	      // Result set get the result of the SQL query
	      preparedStatement = connect.prepareStatement("SELECT * FROM customers WHERE name = ?");
	      // Parameters start with 1
	      preparedStatement.setString(1, customer);
	      resultSet = preparedStatement.executeQuery();
	      
	      if (resultSet.next()) {
	      	String firstname = resultSet.getString("firstname");
	      	String lastname = resultSet.getString("lastname");
	      	// Display as title of the chart
	      	message = "Transaction Records of " + firstname + " " + lastname + " in year 2013";
	      	
	      }else{
	    	  // If customer record not exists, print error message
	    	  message =  "Sorry! Records of customer '"+ customer +"' not exist. Please input again.";
	      }
	      
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
		     close(resultSet);   

		 }
		 return message;
	  }
	  
	  public List<String> loadCustomers() {
		  	 List<String> customerList = new ArrayList<String>();
			 ResultSet resultSet = null;
			 try {
			  connect();
		      // Result set get the result of the SQL query
		      preparedStatement = connect.prepareStatement("SELECT name FROM customers");
		      resultSet = preparedStatement.executeQuery();
		      
		      while (resultSet.next()) {
		      	String name = resultSet.getString("name");
		      	customerList.add(name);
		      }
		      
			 } catch (Exception e) {
				 e.printStackTrace();
			 } finally {
			     close(resultSet);   

			 }
			 return customerList;
		  }
	  
	  /**
	   * For Testing only
	   */
	  protected void printTransactionResults(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
		  Date datetime = resultSet.getDate("datetime");
		  String customer = resultSet.getString("customer");
		  String product = resultSet.getString("product");
		  int quantity = resultSet.getInt("quantity");
		  Double price = resultSet.getDouble("price");
		  Double totalvalue = resultSet.getDouble("totalvalue");
		  System.out.println("Date: " + datetime);
		  System.out.println("Customer: " + customer);
		  System.out.println("Product: " + product);
		  System.out.println("Quantity: " + quantity);
		  System.out.println("price: " + price);
		  System.out.println("totalvalue: " + totalvalue);
		  System.out.println();
		}
	  }
	  
	  /**
	   * Retrieve Database related info for Database access
	   */
	  protected void loadPropertiesFile() {
			InputStream is = null;
			
			try{			
				prop = new Properties();
				System.out.println("Path = " + PROP_FILE);
				is = MySQLAccess.class.getClassLoader().getResourceAsStream(PROP_FILE);
				
				prop.load(is);
				
				DB_HOST = prop.getProperty(DB_HOST_KEY);
				DB_USER = prop.getProperty(DB_USER_KEY);
				DB_PASSWORD = prop.getProperty(DB_PASSWORD_KEY);			
				
				is.close();  
			} catch(Exception e) {  
				System.out.println("Failed to read from " + PROP_FILE + " file.");  
			} finally {
				if (is !=null) {
					try {
						is.close();
						is = null;
					} catch (Exception e) {}
				}
			}
		}
	  
	  // Launch a MySQL DB connection
	  private void connect() throws Exception{
		  // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      loadPropertiesFile();
	      // Setup the connection with the DB
	      connect = DriverManager.getConnection(DB_HOST,DB_USER,DB_PASSWORD);
	      // Statements allow to issue SQL queries to the database
	      statement = connect.createStatement();
	  }

	  // Close the resultSet and connection
	  private void close(ResultSet resultSet) {
	    try {
		  if (resultSet != null) {
		    resultSet.close();
		  }	
	    	
	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }
} 
