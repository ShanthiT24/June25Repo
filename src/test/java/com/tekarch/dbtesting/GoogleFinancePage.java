package com.tekarch.dbtesting;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class GoogleFinancePage {
	
	@Test
	
	public void getStocks()
	{
		WebDriver driver=null;
		
		try {
		 driver = new ChromeDriver();		 
		 driver.get("https://www.google.com/finance/");
		 driver.findElement(By.xpath("//ul[@class='sbnBtf']"));
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='ZvmM7']"));
//		for (WebElement el : elements) {
//		    System.out.println(el.getText()); // This prints the visible text of each element
//		}
		
		for (int i = 0; i < 6; i++) {
		    System.out.println(elements.get(i).getText());
		}
		 
		 
		}catch(Exception e)
		{}

		driver.close();
	}
}
