package com.helic.webchart;

import java.util.*;

/**
 * This Class store the Record of transaction of a customer in a specific Date
 * Act as a Java Bean for information storage
 */

public class Record {
	
	private Date datetime;
	private Set<String> products;
	private Map<String, Integer> quantities;
	private Map<String, Double> totalvalues;
	
	public Record(Date datetime){
		this.datetime = datetime;
		this.products = new LinkedHashSet<String>();
		this.quantities = new HashMap<String, Integer>();
		this.totalvalues = new HashMap<String, Double>();
	}
	
	public Date getDatetime() {
		return datetime;
	}

	public Set<String> getProducts() {
		return products;
	}
	
	protected void addProduct(String product) {
		this.products.add(product);
	}
	
	public int getQuantity(String key) {
		if (quantities.get(key) == null){
			return 0;
		}else{
			return quantities.get(key);
		}
	}
	
	protected void addQuantity(String key, int quantity) {
		this.quantities.put(key,(Integer) quantity);
	}

	public double getTotalValue(String key) {
		if (totalvalues.get(key) == null){
			return 0.0;
		}else{
			return totalvalues.get(key);
		}
	}
	
	protected void addTotalvalue(String key, Double totalvalue) {
		this.totalvalues.put(key,totalvalue);
	}
	
	public void addRecord(String key, int quantity, Double totalvalue) {
		addProduct(key);
		addQuantity(key,quantity);
		addTotalvalue(key,totalvalue);
	}
}
