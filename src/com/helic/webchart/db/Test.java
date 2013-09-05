package com.helic.webchart.db;

import java.io.*;
import java.util.*;

public class Test {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.print("Input a customer name>");
		String customer = scan.next();
		System.out.println("Loading database.....");
		
		MySQLAccess dao = new MySQLAccess();

	    dao.readTransactions(customer);
	}
	
}
