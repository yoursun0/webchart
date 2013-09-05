package com.helic.webchart;

import java.util.*;
import java.sql.ResultSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.helic.webchart.db.*;

/**
 * This Class is the main class for printing web chart based on a single customer
 * 
 * A Customer object is a main object for accessing all data related to the particular customer.
 * The key is customer's username.
 * 
 */
public class Customer {
	
	/** toggle status indicates to display the chart in quantity mode or total value mode **/
	private boolean toggle = false;
	
	/** This private String name acts as a key of the instance **/
	private String name;
	
	private Set<Date> dates;
	private Set<String> products;
	private Map<Date, Record> records;
	private JSONObject charttype;
	private JSONObject title;
	private JSONObject subtitle;
	private JSONObject xAxis;
	private JSONObject yAxis;
	private JSONObject xtitle;
	private JSONObject ytitle;
	private JSONArray series;
	private MySQLAccess dao;
	private final long oneday = 24 * 3600 * 1000;
	private Date startdate;
	private Date enddate;
	
	/** Constructor with parameters customer name and toggle status **/
	public Customer(String name, String toggleStatus) {
		this.name = name;
		this.toggle = ("1".equals(toggleStatus))? true : false;
		Calendar cal = Calendar.getInstance();
		
		// Temp date value, will be overrided later by Database data
		cal.set(2013,2,2,0,0,0);
		this.startdate = cal.getTime();
		this.enddate = startdate;
		this.dates = new TreeSet<Date>();
		this.records = new TreeMap<Date, Record>();
		
		// Database Access Object
		this.dao = new MySQLAccess();
	
		// Title of the chart
		String titletext = dao.printCustomerFullName(name);
		title = new JSONObject();
		title.put("text", titletext);
		
		// set other option values
		chartSetting();
		
		// fetch database records to TreeMap of Record objects
		try {
			fetchDatabase(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}

	public void changeToggle() {
		if (toggle){
			toggle = false;
		}else{
			toggle = true;
		}
	}
	
	private Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	private void chartSetting(){
		// Chart Spacing
		charttype = new JSONObject();
		charttype.put("zoomType", "x");
		charttype.put("spacingRight", 20);
				
		// Subtitle description
		subtitle = new JSONObject();
		subtitle.put("text", "Drag in the plot area to zoom in");
						
		// Text on the Axis
		xtitle = new JSONObject();
		xtitle.put("text", "Date");
		ytitle = new JSONObject();
		if (toggle){
			ytitle.put("text", "Total Value ($)");
		}else{
			ytitle.put("text", "Quantity");
		}

				
		// X-Axis setting
		xAxis = new JSONObject();
		xAxis.put("type", "datetime");
		xAxis.put("maxZoom", 7 * 24 * 3600000); // seven days
		xAxis.put("title", xtitle);
				
		// Y-Axis setting
		yAxis = new JSONObject();
		yAxis.put("title", ytitle);	
		yAxis.put("min", 0);
		
		// Series
		series = new JSONArray();
	}
	
	private void fetchDatabase(String name) throws Exception{
		List<Transaction> results = dao.readTransactions(name);
		this.dates = new TreeSet<Date>();
		this.products = new TreeSet<String>();
		this.records = new TreeMap<Date, Record>();
		
		for(Transaction result : results) {
			  Date datetime = result.getDatetime();
			  if (datetime.compareTo(startdate)<0){
				  startdate = datetime;
			  }
			  if (datetime.compareTo(enddate)>0){
				  enddate = datetime;
			  }
			  Record rec;
			  
			  if (dates.contains(datetime)){
				  rec = records.get(datetime);
			  }else{
				  dates.add(datetime);
				  rec = new Record(datetime);
			  }
			  
			  // fetch the product name, quantity and total value from the result row
			  String product = result.getProduct();
			  int quantity = result.getQuantity();
			  Double totalvalue = result.getTotalvalue();
			  
			  rec.addRecord(product,quantity,totalvalue);
			  records.put(datetime, rec);
			  products.add(product);
		}

	}
	
	public String writeJSON() {
		
		for (String item : products){
			JSONObject object = new JSONObject();
			object.put("name", item);
			object.put("type", "line");
			object.put("pointInterval", oneday);
			object.put("pointStart",startdate.getTime());
			
			JSONArray objectqty = new JSONArray();
			JSONArray objecttotal = new JSONArray();
			Random rand = new Random();
			Date currentDate = startdate;
			
			while (currentDate.compareTo(enddate)<=0){

				if (!dates.contains(currentDate)){
					objectqty.add(0);
					objecttotal.add(0.0);
				}else{
					Record rec = records.get(currentDate);
					objectqty.add(rec.getQuantity(item));
					objecttotal.add(rec.getTotalValue(item));
				}
				
				currentDate = addDays(currentDate, 1);
			}
			
			if (toggle){
				object.put("data", objecttotal);
			}else{
				object.put("data", objectqty);
			}
			series.add(object);
		}
		
		JSONObject chart = new JSONObject();
		chart.put("chart", this.charttype);
		chart.put("title", this.title);
		chart.put("subtitle", this.subtitle);
		chart.put("xAxis", this.xAxis);
		chart.put("yAxis", this.yAxis);
		chart.put("series", this.series);
		
		return chart.toJSONString();
	}
}
