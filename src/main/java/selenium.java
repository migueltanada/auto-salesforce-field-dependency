package auto-salesforce-field-dependency;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; 
import static org.junit.Assert.*;
import org.openqa.selenium.remote.Augmenter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.lang.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.*; 

public class basicTitleTest {
	

	// Type -> Sub-type
	// public String url = "https://mydomain.lightning.force.com/lightning/setup/ObjectManager/Case/FieldsAndRelationships/editDependency?controller=Type&dependent=<sub-type field ID>";


	public static void main(String[] args) throws IOException, java.lang.InterruptedException
  {

    // if chromedriver is not in your system $PATH
		// System.setProperty("webdriver.chrome.driver", "~/chromedriver");


    // List in csv
    /* in type,sub-type
       type1,sub-type2
       type2,sub-type1
       type3,sub-type1
       ...
     */
     
    List<String> list=new LinkedList<String>(); 
		TimeUnit time = TimeUnit.SECONDS; 
	
		WebDriver driver = new ChromeDriver();
		driver.get(url);


		String xpath;
    
    // INCLUDE BUTTON XPATH
		String include = "//*[@id=\"ep\"]/div[2]/div[6]/table/tbody/tr[1]/td/div/input[1]";
	

		//login 
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("USERNAME@SALESFORCE.COM");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("PASSWORD");
		driver.findElement(By.xpath("//*[@id=\"Login\"]")).click();


		// Wait for element to load
		WebElement element = (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@title='Edit Field Dependency ~ Salesforce - Unlimited Edition']")));

    // switch to iFrame
    driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Edit Field Dependency ~ Salesforce - Unlimited Edition']")));

		//Wait for next button to be available
		WebElement waitNext = (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Next')]")));
		
    // variable for reading csv
    String []csv;


    // loop through csv list
		for(String subType:list){

			csv = subType.split(",");

			// Press Next until header is available
			while (driver.findElements(By.xpath("//th[contains(text(),'\u00A0" + csv[0] + "\u00A0')]")).size() == 0){
				driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
			}
			

			xpath = "//*[@title=\"Excluded: Type: " + csv[0] + "\"][contains(text(),'\u00A0" + csv[1] + "\u00A0')]";
			if (driver.findElements(By.xpath("//*[@title=\"Included: Type: " + csv[0] + "\"][contains(text(),'\u00A0" + csv[1] + "\u00A0')]")).size() > 0){

				System.out.printf("[SKIP] %s -> %s is already included\n",csv[0],csv[1]);
        
			} else {
      
				driver.findElement(By.xpath(xpath)).click();
				time.sleep(1);
				driver.findElement(By.xpath(include)).click();
				System.out.printf("[INFO] %s -> %s is ok\n",csv[0],csv[1]);

			}



		}

		System.out.println("Waiting for 5 mins for save and verification.");
		time.sleep(300);
		
		driver.quit();

	}
	
}
