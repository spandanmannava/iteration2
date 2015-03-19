import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HttpGet {
	
	public static void main(String[] args){
		//Document doc;
		//try {
			
			WebDriver driver=new FirefoxDriver();
			driver.get("http://www.kcscout.net/mobile");
			System.out.println(driver.getTitle());
			//System.out.println(driver.getCurrentUrl());

/*			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//System.out.println(driver.getCurrentUrl());
			WebDriverWait wait=new WebDriverWait(driver, 30, 1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lbtnIncidents")));
			
			driver.findElement(By.linkText("Incidents")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Show All Incident")));
			driver.findElement(By.partialLinkText("Show All Incident")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Click here to go to Full Website")));
			//System.out.println(driver.findElement(By.xpath("//a[contains(text(),'Click here to go to Full Website')]/..")).getText());
			
			String allIncidents = driver.findElement(By.xpath("//a[contains(text(),'Click here to go to Full Website')]/..")).getText();
			System.out.println(allIncidents);
			
			/*String allIncidents=
					"Welcome To Zscaler Directory Authentication\n"+
					"Click here to go to Full Website\n"+
					"Home >> Show All Incident(s)\n"+
					"Type : Scheduled\n"+
					"Location : 291 HWY SB PAST I-470\n"+
					"Description : ROADWORK 291 HWY SB PAST I-470 LEFT LANE CLOSED EST. CLEARANCE TIME: 3/23/2015 3:00 PM\n"+
					"Start Time : Mon Mar 16 09:02:46 CDT 2015\n"+
					
					
					"Type : Scheduled\n"+
					"Location : 71 HWY NB AT 87TH ST\n"+
					"Description : ROADWORK 71 HWY NB AT 87TH ST LEFT LANE CLOSED EST. CLEARANCE TIME: 5/6/2015 3:00 PM\n"+
					"Start Time : Fri Mar 13 09:20:03 CDT 2015\n"+
					
					"Type : Scheduled\n"+
					"Location : I-35 SB PAST CHOUTEAU TRFWY\n"+
					"Description : ROADWORK I-35 SB PAST CHOUTEAU TRFWY LEFT LANE CLOSED EST. CLEARANCE TIME: 3/18/2015 5:00 AM\n"+
					"Start Time : Tue Mar 17 23:13:34 CDT 2015\n"+


					"Type : Scheduled\n"+
					"Location : I-35 SB TO I-435 SB\n"+
					"Description : ROADWORK I-35 SB TO I-435 SB BE PREPARED TO STOP EST. CLEARANCE TIME: 3/18/2015 5:00 AM\n"+
					"Start Time : Tue Mar 17 23:17:53 CDT 2015\n";


					/*Type : Scheduled
					Location : I-49 SB TO 291 HWY
					Description : EXIT CLOSED I-49 SB TO 291 HWY USE ALT ROUTE EST. CLEARANCE TIME: 5/7/2015 3:00 PM
					Start Time : Fri Mar 13 06:46:16 CDT 2015


					Type : Scheduled
					Location : I-70 WB PAST LITTLE BLUE PKWY
					Description : ROADWORK I-70 WB PAST LITTLE BLUE PKWY BE PREPARED TO STOP EST. CLEARANCE TIME: 5:00 AM
					Start Time : Wed Mar 18 01:34:47 CDT 2015


					Type : Scheduled
					Location : K-10 WB AT I-435
					Description : K-10 WB CLOSED AT I-435 USE ALT ROUTE
					Start Time : Tue Mar 17 22:04:42 CDT 2015


					Have you been helped by Motorist Assist?   Click here to take the survey"
*/
			//driver.close();
			
			List<String> Type=new ArrayList<String>();
			List<String> Location=new ArrayList<String>();
			List<String> Description=new ArrayList<String>();
			List<String> StartTime=new ArrayList<String>();
			
			//Parsing the Type
			Pattern p=Pattern.compile("Type :(.*)");
			Matcher m = p.matcher(allIncidents);
		
			while(m.find()){
				  //System.out.println(m.group(1));
				  Type.add(m.group(1));
				}
				System.out.println(Type);
		
		   //Parsing the Location
		   p=Pattern.compile("Location :(.*)");
		    m = p.matcher(allIncidents);
			
			while(m.find()){
				  //System.out.println(m.group(1));
				  Location.add(m.group(1));
				}
				System.out.println(Location);
		
		   //Parsing the Description		
		   p=Pattern.compile("Description :(.*)");
		   
		    m = p.matcher(allIncidents);
			
			while(m.find()){
				 // System.out.println(m.group(1));
				  Description.add(m.group(1));
				}
				System.out.println(Description);
		   
		   //Parsing the Start Time
		   p=Pattern.compile("Start Time :(.*)");
		   
		    m = p.matcher(allIncidents);
			
			while(m.find()){
				 // System.out.println(m.group(1));
				  StartTime.add(m.group(1));
				}
				System.out.println(StartTime);
	
	// JDBC driver name and database URL
	   //final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  //final String DB_URL = "jdbc:mysql://10.131.91.23:1433/Incidents?user=sa&password=common99";
	   
	   //final String USER = "sa";
	   //final String PASS = "common99";
	   
	   Connection conn = null;
	   PreparedStatement stmt = null;
	   
	   try{
	      //STEP 2: Register JDBC driver
	      //Class.forName("com.mysql.jdbc.Driver");
		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection("jdbc:sqlserver://LODIMAUTOMC21:1433;selectMethod=cursor;DatabaseName=master","sa","firewall");
	      //jdbc:sqlserver://LODIMAUTOMC21:1433SQL.adminLogin=saSQL.adminPwd=firewall

	      //conn = DriverManager.getConnection(DB_URL);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      String  sql = "INSERT into Incidents values(?,?,?,?)";
	      stmt = conn.prepareStatement(sql);
	      for(int i=0;i<Type.size();i++){
	    	  stmt.setString(1,Type.get(i));
	          stmt.setString(2,Location.get(i));
	          stmt.setString(3,Description.get(i));
	          stmt.setString(4,StartTime.get(i));
	          stmt.executeUpdate();
	      }
      
	      System.out.println("DB insertion is done");
	      
	      //STEP 5: Clean-up environment
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   
	   
			
		/*	// need http protocol
			doc = Jsoup.connect("http://www.kcscout.net/mobile/").get();
	 
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);
	 
			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 
				// get the value from href attribute
				System.out.println("\nlink : " + link.attr("href"));
				System.out.println("text : " + link.text());
	 
			}*/
	 
	/*	} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
