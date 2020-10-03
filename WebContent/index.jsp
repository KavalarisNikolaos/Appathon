<%@page import="appathonservlet.Servlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html lang="en"><head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">   

<title>Appathon</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  margin: 0;
}

/* Style the header */
.header {
  padding: 20px;
  text-align: center;
  background: #008B8B;
  color: white;
}

/* Increase the font size of the h1 element */
.header h1 {
  font-size: 50px;
}

.topbar {
  border-top: 3px solid white;
  border-bottom: 3px solid white;
  width: 100%;
  text-align: center;
}

.button {
  background-color: #777;
  border: none;
  color: white;
  text-align: center;
  padding: 15px 25px;
  display: inline-block;
  margin:0 auto;
  width: 200px
}

.active {
  background-color: #ddd;
  color: red;
}

.inbutton {
  background-color: #333;
  color: white;
  text-align: center;
  padding: 10px 15px;
  text-decoration: none;
}

.inbutton:hover {
  background-color: #ddd;
  color: black;
}

/* Change color on hover */
.button:hover {
  background-color: #ddd;
  color: black;
}
.main {   
  flex: 100%;
  background-color: grey;
  padding: 20px;
}

/* Footer */
.footer {
  padding: 15px;
  background: #333;
  border-style: solid;
  border-width: 3px 0px 0px 0px;
  border-top-color: #F8F8FF;
  color : white;
  text-align: center;
}

.parent {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

.box {
	background-color: #eee;
	flex: 0 1 450px ;
	margin: 15px;
	padding: 10px;
	border-radius: 5px; 
}

</style>

</head>

<body style="background-color: #333;" >

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    // execute
	(function() {
			
			
			Display();
	})();
});
function Display(){

			var arr1='<%=session.getAttribute("dis1")%>';
			var length1='<%=session.getAttribute("length1")%>';
			
			var arr2='<%=session.getAttribute("dis2")%>';
			var length2='<%=session.getAttribute("length2")%>';
			var arr3= '<%=session.getAttribute("dis3")%>';
			var length3='<%=session.getAttribute("length3")%>';

ListClear("ol_all");
ListClear("ol_sum");
ListClear("ol_cri");

arr1 = arr1.replace(/^\[|\]$/g, "").split(", ");
arr2 = arr2.replace(/^\[|\]$/g, "").split(", ");
arr3 = arr3.replace(/^\[|\]$/g, "").split(", ");


for(var i=0; i < length1;i++){ 
ListAdd("ol_all","URL",arr1[i]);
}
for(var i=0; i < length2;i++){ 
ListAdd("ol_sum","URL", arr2[i] );
}
 for(var i=0; i < length3;i++) {
ListAdd("ol_cri","URL", arr3[i]);
 }



}
	
	
	
	function ListClear(ol_id) {
	
		document.getElementById(ol_id).innerHTML = "";
	}
	
	function ListAdd(ol_id, txt, url) {
	
		var ol = document.getElementById(ol_id);
		
		var li = document.createElement("li");
		var a = document.createElement('a');
		var n = document.createTextNode(txt);
		a.appendChild(n);
		a.title = url;
		a.href = url;
		a.target = "_blank";
		li.appendChild(a);
		ol.appendChild(li);
	}
	
	
</script>



<div class="header">
  <h1>Appathon </h1>
  <p>PROJECT CTGOV-03</p>
</div>


<div class="topbar" style="padding: 20px">
	<p style="color:white; width: 60%; display: inline-block;">
	Σκοπός είναι να φτιάξουμε μια δικτυακή εφαρμογή η οποία θα παίρνει ως input το όνομα μιας ασθένειας και θα εντοπίζει τις κλινικές μελέτες με την ασθένεια αυτή με βάση την πληροφορία που υπάρχει στο αντίστοιχο πεδίο με τα conditions.Ακολούθως θα εξετάζει κατά πόσο η ασθένεια αυτή αναφέρεται στην σύνοψη της κλινικής μελέτης (brief summary – free text) και τα κριτήρια καταλληλότητας (eligibility criteria – free text) . Τέλος το σύστημα θα παρουσιάζει τα άρθρα (καθώς και το πλήθος τους) στα οποία η ασθένεια υπάρχει και στα τρία παραπάνω πεδία (conditions,brief summary, eligibility criteria) καθώς επίσης και αυτά στα οποία δεν υπάρχει είτε στο ένα (brief summary) είτε στο άλλο (eligibility criteria).
	</p>
	<br>
	<br>
	<form class="example" action="servlet" id="formid" method="post" >
		<a href="http://localhost:8080/appathonservlet/"></a>
		
		
		  <input type="text" placeholder="Search for a diseace..." id="txt_search" name="txt_search">
		  <br>
		  <button form="formid" type="submit" style="width:100px;color:black;height:28px;" ></button>
		  <br>
		
		
	</form>
</div>


<div class="parent" style="height: 100%;">
	<div class="box">
		<p style="border-bottom: 1px solid black">Conditions and Summary and Criteria</p>
		<ol id="ol_all">
		</ol>
	</div>
	<div class="box">
		<p style="border-bottom: 1px solid black">Conditions and Summary</p>
		<ol id="ol_sum">
		</ol>
	</div>
	<div class="box">
		<p style="border-bottom: 1px solid black">Conditions and Criteria</p>
		<ol id="ol_cri">
		</ol>
	</div>
</div>

<div class="footer" style=" float: bottom;">
  <p>2 0 2 0</p>
</div>



</body></html>