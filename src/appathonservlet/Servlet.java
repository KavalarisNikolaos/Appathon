package appathonservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

 
//import appathonservlet.ConnectToDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			response.setContentType("text/html");
				
		
				String cond = request.getParameter("txt_search");
				Connection connection = null;
				ResultSet rs = null;
				connection = ConnectToDB.getConnection();
			    Statement statement = connection.createStatement();
			    String sqlSelect = "SELECT * FROM data";
			      
			    rs = statement.executeQuery(sqlSelect);	  
			    String con_text;
				int  index;
				boolean boolean1, boolean2;
				
			
				ArrayList<String> url1 = new ArrayList<String>();
				ArrayList<String> url2 = new ArrayList<String>();
				ArrayList<String> url3 = new ArrayList<String>();
				
				if(cond=="") {
				response.getWriter().println("No Input disease");
				}
				
				else {
				while(rs.next()) {
					boolean1 =false;
					boolean2=false;
					con_text = rs.getString("cond").toLowerCase();
					index = con_text.indexOf(cond);
					if(index == -1) continue;
						
					if(rs.getString("briefSummary").toLowerCase().indexOf(cond) >= 0) boolean1 = true;
					if(rs.getString("eligibilityCriteria").toLowerCase().indexOf(cond) >= 0) boolean2 = true;
						
						if(boolean1 && boolean2) {
							url1.add(rs.getString("URL"));
							
						}
			            else if(boolean1 && !boolean2) {
							
							url2.add(rs.getString("URL"));
			            }
			            else if(!boolean1 && boolean2) {
							
							url3.add(rs.getString("URL"));
			            }
						
			            else continue;			
						
				  }
				
				//set new session attributes
				HttpSession session = request.getSession();
				
				session.setAttribute("dis1", url1); 
				session.setAttribute("length1", url1.size());
				session.setAttribute("dis2", url2);
				session.setAttribute("length2", url2.size());
				session.setAttribute("dis3", url3);
				session.setAttribute("length3", url3.size());
			    rs.close(); 
			    statement.close();
					
			    //redirect to index.jsp page
			    response.sendRedirect("index.jsp");
	
		}}
			
		catch (SQLException e)
		{
		throw new RuntimeException("Unable to connect to db, please try again later!", e);
		}	
}
}