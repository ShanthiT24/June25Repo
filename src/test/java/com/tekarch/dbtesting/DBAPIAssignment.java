package com.tekarch.dbtesting;

import org.testng.annotations.Test;

import com.mysql.cj.jdbc.Driver;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import static org.hamcrest.Matchers.*;


public class DBAPIAssignment {
	WebDriver driver;
	String projName="";
	String projId="";

	
	//@BeforeMethod
	@BeforeMethod
	    public void setUp() {
	        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
	        WebDriverManager.chromedriver().setup();

	     // Set Chrome experimental preferences
	        Map<String, Object> prefs = new HashMap<>();
	        prefs.put("credentials_enable_service", false);  // Disable credential service
	        prefs.put("profile.password_manager_enabled", false);  // Disable password manager

	        ChromeOptions options = new ChromeOptions();
	        options.setExperimentalOption("prefs", prefs);

	        options.addArguments("disable-infobars");
	        options.addArguments("--disable-notifications");
	        options.addArguments("start-maximized");


	        driver = new ChromeDriver(options);
	    }

	
	@Test
	public void uiApiDbTest() throws Throwable
	{
		try {
										
		// driver = new ChromeDriver(options);
		 driver.get("http://49.249.28.218:8091/");
		 Alert alert;
		 WebDriverWait wait;
		 projName="java project"+Math.random();
		 wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
		 
		//driver.get("http://49.249.28.218:8091/");
		String mainWindow = driver.getWindowHandle(); // Store main window
		
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		Thread.sleep(1500);
//		 Set<String> handles = driver.getWindowHandles();
//		 
//		 for (String handle : handles) {
//	            if (!handle.equals(mainWindow)) {
//	               // driver.switchTo().window(handle); // Switch to new tab
//	                System.out.println("New tab title: " + driver.getTitle());
//	                
//	                
//	                alert = wait.until(ExpectedConditions.alertIsPresent());
//	                alert = driver.switchTo().alert();
//	                // Accept the alert
//	                System.out.println("Alert text: " + alert.getText());
//	                alert.accept(); // Click OK
//
//	                // Do something in new tab...
//	                driver.close(); // Close the new tab
//	            }
//	        }
		
		driver.switchTo().window(mainWindow); // Switch back to main window
		driver.findElement(By.xpath("//a[text()='Projects']")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[text()='Create Project']")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@name='projectName']")).sendKeys(projName);
		driver.findElement(By.xpath("//input[@name='createdBy']")).sendKeys("renu");
		WebElement proStatus = driver.findElement(By.xpath("//div[label[contains(text(),'Project Status')]]//select[@name='status']"));
		
		Select status = new Select(proStatus);
		status.selectByVisibleText("Completed");
		
		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(500);

		WebElement toastMsgBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
			    By.xpath("//div[@role='alert']")));
		
		String toastMsg = toastMsgBox.getText();
	
	    System.out.println("Toast text is :" +toastMsg);
		 
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
		
	 
		@Test(dependsOnMethods="uiApiDbTest")
		public void checkDBForAddedProject()
		{
		
		Connection conn = null;
		String resProjId="";

        try {
            // Register JDBC driver (optional for modern JDBC versions)
            //Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class name
            
            Driver driverRef = new Driver();
            DriverManager.registerDriver(driverRef);

            // Open a connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://49.249.28.218:3307/ninza_hrm",
                "root@%", // or whatever your username is
                "root"  // your password
            );

            System.out.println("Connected successfully!");

            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM project where project_name='"+projName+"'");
            
    		while(result.next())
    		{
    			System.out.println(result.getString(1)+ "\t" + result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4)+"\t"+result.getString(5));
    			
    		}
    		
    		ResultSet rs1 = stmt.executeQuery("Select project_id from project where project_name='"+projName+"'");
    		while (rs1.next()) {
    		    System.out.println(rs1.getString(1));
    		    projId = rs1.getString(1);
    		    
    		}

        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	@Test(dependsOnMethods = "checkDBForAddedProject")
	public void checkAPI()
	{
		System.out.println("project id:"+projId);
		Response res = RestAssured
							.given()
							.baseUri("http://49.249.28.218:8091")
							 .when()
							 .get("/project/" + projId);
							 					 
							 
						 res.then()
							 	.statusCode(200)
							 	//.body("project_name", notNullValue());
							 	.body("projectName", notNullValue());
		
		System.out.println("Status Code :"+res.getStatusCode());
		System.out.println("Response Body:");
		System.out.println(res.getBody().asString());
		
		
	}
		
	 @AfterMethod
	    public void tearDown() {
	        if (driver != null) driver.quit();
	    }
		
		
	}




