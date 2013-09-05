<%-- 
 Recruitment task for .NET Developer position
 Candidate: Helic Leung
 Technology Used: J2EE MVC/Model2, JSP, AJAX, JQuery, JDBC MySQL Driver, HighCharts JS

 Front Page of Web Application WebChart
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.helic.webchart.db.MySQLAccess" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<%-- Call Database Access Object to crawl customer information for autocomplete search --%>
<%

MySQLAccess dao = new MySQLAccess();
List<String> columns = dao.loadCustomers();

StringBuffer values = new StringBuffer();
for (int i = 0; i < columns.size(); ++i) {
    if (values.length() > 0) {
        values.append(',');
    }
    values.append('"').append(columns.get(i)).append('"');
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>WebChart Application</title>
    
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
    <link rel="stylesheet" href="./css/main.css" />
    <script src="./js/chart.js" ></script>
    <script src="./js/query.js" ></script>
    <script src="./js/ajax-jquery.js" ></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <script type="text/javascript" id="script">

    var userlist = [<%=values %>];
    var toggleStatus = "0";

$(document).ready(function () {
	
	<%-- JQuery AJAX for AutoComplete searchbox --%>
    $.get("customer.jsp",function(data,status){
        userlist = data;       
    });

    $( "#word" ).autocomplete({
      source: userlist
    });
	
    <%-- JQuery AJAX for Toggle button between Quantity and Total Value --%>
    $("#qty").click(function(){
    	 $("#total").show();
	  	 $("#qty").hide(); 
	  	 toggleStatus = "0";
	  	 $.post("draw.jsp",
	  	        {
	  	        name:$("#word").val(),
	  	        toggle:toggleStatus
	  	        },
	  	        function(data,status){
	  	            $('#container').highcharts(data);
	  	        },'json');
	});

    $("#total").click(function(){
      	 	 $("#total").hide();
    	  	 $("#qty").show(); 
    	  	 toggleStatus = "1";
    	  	 $.post("draw.jsp",
    	  	        {
    	  	        name:$("#word").val(),
    	  	        toggle:toggleStatus
    	  	        },
    	  	        function(data,status){
    	  	            $('#container').highcharts(data);
    	  	        },'json');
    });
    
    <%-- JQuery AJAX for Chart Display --%>
    $("#submit").click(function(){
        $.post("draw.jsp",
        {
        name:$("#word").val(),
        toggle:toggleStatus
        },
        function(data,status){
            $('#container').highcharts(data);
        },'json');
    });

});
    </script>
</head>
<body>
    <script src="./js/highcharts.js"></script>
    <script src="./js/exporting.js"></script>
    <div class="headers">
          <h1>WebChart Dashboard for Transaction Records</h1>
          <h2>Recruitment task for .NET Developer position</h2>
          <h3>Candidate: Helic Leung</h3>
          <h3>Technology Used: J2EE MVC/Model2, JSP, AJAX, JQuery, JDBC MySQL Driver, HighCharts JS</h3>
    </div>
    <hr />
    <form name="f1">
    <p>Customer Name: 
    <span class="search-panel">
        <input id="word" class="search-box" autocomplete="OFF" aria-autocomplete="list" />
    </span>
    <input id="submit" class="button" value="Search" type="button" />
    <input id="qty" class="button toggle" value="Display Quantity" type="button" style="display:none;" />
    <input id="total" class="button toggle" value="Display Total Value" type="button" />
    </p>
    <p class="small">customer names include helic, remi, claire, james, jane.</p>
    <div id="result"></div>
    </form>
    
    <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
</body>
</html>