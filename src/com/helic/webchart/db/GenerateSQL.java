package com.helic.webchart.db;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GenerateSQL {
	
	private static StringBuilder sql;
	
	// TODO Generate a single INSERT SQL statement
	
	public static void generate(String customer, String product, double price){
		Random rand = new Random();
		int qty = 10;
		for (int j=1;j<=4;j++){
			for (int i=1;i<=31;i++){
				if ((j==2)&&(i>28)) continue;
				if ((j==4)&&(i>28)) continue;	
				qty = qty - 2 + rand.nextInt(5);
				if (qty <= 0){
					qty = 0;
				}else{
					sql.append("insert into transactions (ID,datetime,customer,product,quantity,price,totalvalue,volume) ");
					sql.append("VALUES (default,'2013-0");
					sql.append(j);
					sql.append("-");
					if (i<10){
						sql.append("0");
					}
					sql.append(i);
					sql.append(" 00:01:00','");
					sql.append(customer);
					sql.append("','");
					sql.append(product);
					sql.append("','");
					sql.append(qty);
					sql.append("','");
					sql.append(price);
					sql.append("','");
					sql.append(qty*price);
					sql.append("','");				
					sql.append(qty);
					sql.append("');\n");	
				}
			}
		}
	}
	
	// TODO Generate INSERT SQL scripts for transaction tables
	
	public static void main(String[] args) {
			
		sql = new StringBuilder();
		
		generate("helic","iMac",8800.0);
		generate("helic","iPad",5100.0);
		generate("helic","iPhone",4000.0);
		generate("helic","iPod",2500.0);
		
		generate("remi","iMac",8800.0);
		generate("remi","iPad",5100.0);
		generate("remi","iPhone",4000.0);
		generate("remi","iPod",2500.0);
		
		generate("claire","iMac",8800.0);
		generate("claire","iPad",5100.0);
		generate("claire","iPhone",4000.0);
		generate("claire","iPod",2500.0);
		
		generate("james","iMac",8800.0);
		generate("james","iPad",5100.0);
		generate("james","iPhone",4000.0);
		generate("james","iPod",2500.0);
		
		generate("jane","iMac",8800.0);
		generate("jane","iPad",5100.0);
		generate("jane","iPhone",4000.0);
		generate("jane","iPod",2500.0);
		
		sql.append("COMMIT;\n");
		
		try {
			FileWriter file = new FileWriter("c:\\insert.sql");
			file.write(sql.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

}
