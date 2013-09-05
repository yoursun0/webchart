<%-- 
-- Recruitment task for .NET Developer position
-- Candidate: Helic Leung
-- Technology Used: J2EE MVC/Model2, JSP, AJAX, JQuery, JDBC MySQL Driver, HighCharts JS
--
-- JSP controller for AJAX request on chart drawing
--%>


<%@ page language="java" contentType="application/x-www-form-urlencoded; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page import="com.helic.webchart.Customer" %>

<%
	String customer = request.getParameter("name");
	String toggle = request.getParameter("toggle");
	Customer client = new Customer(customer,toggle);
	
%>

<%-- Output Json for Highcharts to plot the graph --%>
<%= client.writeJSON() %>
