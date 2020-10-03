import javax.xml.parsers.DocumentBuilderFactory;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import org.xml.sax.InputSource;
import java.io.ByteArrayInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File; 
import java.sql.*;

public class Datainsertion{

  public static void main(String args[]) {
    
    String username = "root", password = "";
    
    NodeList nodeListCond, nodeListBS, nodeListEC, nodeList, nodeListurl;
    Node nodecond, nodeBS, nodeEC, nodeurl,node;
    
    Element eElementInter, eElementBS, eElementEC, eElement;
    String cond = "", bs, ec, url, sql;
    int result, i = 0, j, k;

    try{    
       
       String connectionURL = "jdbc:mysql://localhost:3306/clinicaltrials";
       Connection connection = null;
       Statement statement = null;
       Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
       connection = DriverManager.getConnection(connectionURL, username, password); 
       statement = connection.createStatement();
       
       File TopFolder = new File("./AllPublicXML");
       String[] folderNames = TopFolder.list();
       File[] folder = new File[folderNames.length];

       for(k = 0; k < folderNames.length; k++) {
         folder[k] = new File("./AllPublicXML/" + folderNames[k]);
         String[] fileNames = folder[k].list(); 
         BufferedInputStream[] input = new BufferedInputStream[fileNames.length];
         InputSource[] source = new InputSource[fileNames.length];

         for(j = 0; j < fileNames.length; j++) {
           cond=url=bs = ec = "";
           input[j] = new BufferedInputStream(new FileInputStream("./AllPublicXML/" + folderNames[k] + "/" + fileNames[j]));
           source[j] = new InputSource(input[j]);  
           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder db = dbf.newDocumentBuilder();
           Document doc = db.parse(source[j]);
           doc.getDocumentElement().normalize();
      
           nodeListCond= doc.getElementsByTagName("condition");
     
         
           nodecond = nodeListCond.item(0);
           cond= nodecond.getTextContent();
           //eElementCond = (Element) nodeCond;
            
            //   cond += eElementCond.getElementsByTagName("condition").item(0).getTextContent();     
           
            //(inter.equals("")) { input[j].close(); continue; }
           //  if(inter.endsWith(",")) inter = inter.replaceAll(",$","");
           nodeListBS = doc.getElementsByTagName("brief_summary");
             if(nodeListBS.getLength() > 0) {
               nodeBS = nodeListBS.item(0);
               eElementBS = (Element) nodeBS;
               bs = eElementBS.getElementsByTagName("textblock").item(0).getTextContent();
             }
             nodeList = doc.getElementsByTagName("eligibility");
             if(nodeList.getLength() > 0) { 
               node = nodeList.item(0);
               eElement = (Element) node;
               nodeListEC = eElement.getElementsByTagName("criteria");
               if(nodeListEC.getLength() > 0) {
                 nodeEC = nodeListEC.item(0);
                 eElementEC = (Element) nodeEC;
                 ec = eElementEC.getElementsByTagName("textblock").item(0).getTextContent();
               }
               else if(bs.equals("")) { input[j].close(); continue; } 
             }
             else if(bs.equals("")) { input[j].close(); continue; }
             nodeListurl = doc.getElementsByTagName("url");
             nodeurl = nodeListurl.item(0);
             url = nodeurl.getTextContent();
           
             if(cond.contains("\"")) cond = cond.replace("\"", "\\\"");
             if(bs.contains("\"")) bs = bs.replace("\"", "\\\"");
             if(ec.contains("\"")) ec = ec.replace("\"", "\\\"");
             if(url.contains("\"")) url = url.replace("\"", "\\\"");
             
             sql = "INSERT INTO data (fileName,cond, briefSummary, eligibilityCriteria,URL) VALUES (\""+fileNames[j].replace(".xml","")+"\",\""+cond+"\",\""+bs+"\",\""+ec+"\",\""+url+"\")";
             result = statement.executeUpdate(sql);
           } 
           input[j].close();
        
      }
    }  
    catch(Exception e) { e.printStackTrace(); }
  }
}