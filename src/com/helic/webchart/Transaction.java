package com.helic.webchart;

import java.util.Date;

public class Transaction {
	private Date datetime;
	private String product;
	private int quantity;
	private double totalvalue;
	
	public Transaction(Date datetime, String product, int quantity,
			double totalvalue) {
		this.datetime = datetime;
		this.product = product;
		this.quantity = quantity;
		this.totalvalue = totalvalue;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(double totalvalue) {
		this.totalvalue = totalvalue;
	}
	
}
