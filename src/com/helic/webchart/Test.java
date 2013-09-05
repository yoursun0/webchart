package com.helic.webchart;

import java.io.FileWriter;
import java.io.*;
import java.util.*;

public class Test {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.print("Input a customer name>");
		String name = scan.next();
		
		Customer client = new Customer(name,"0");
		System.out.println(client.writeJSON());
		try {
			 
			FileWriter file = new FileWriter("c:\\test.json");
			file.write(client.writeJSON());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
