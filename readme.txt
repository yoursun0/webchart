#####################################
    Release Notes of WebChart 3.0
#####################################

 Recruitment task for .NET Developer position
 Candidate: Helic Leung
 Technology Used: J2EE MVC/Model2, JSP, AJAX, JQuery, JDBC MySQL Driver, HighCharts JS

===============================
 Features:
===============================

an AJAX enabled, ASP.Net MVC project with:

  -   A database with two tables for customers and related transactions (transactions have date, total value [$] and volume). Include some sample records.
  -   Web application that allows customers to be searched by a name (with auto-complete search box). After the customer is selected display the following items: 
     ¡E	a chart showing transactions over time (split by product)
     ¡E	a toggle button for switching between total and quantity
     ¡E	a filter to select one or multiple products to be displayed on the chart

===============================
 Installation Steps:
===============================

(1) Put the webchart/webchart.war in webapps/ folder of Apache Tomcat Application Server (localhost).
(2) Execute the SQL command in following DB script file to the target MySQL database:
    -  dbscript.sql
    -  insert.sql
(3) Launch the Web Application webchart
(4) Edit the config.properties file in <apache-tomcat>/webapps/webchart/WEB-INF/classes/
    Correct the parameters "db_host_address","db_user" and "db_password" to your DB host address, DB admin user and password.

(5) Open a HTML5-supported browser (IE9, FireFox v17, Chrome v26, Sarifi 5, etc.) and access URL http://localhost:8080/webchart/