package bankingTestNG;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.assertEquals;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BankingTestNG {
	private WebDriver driver;
	private  String baseUrl;
	/**
	 * create test data for testing The test data include set of username,
	 * password
	 * 
	 */

	@DataProvider(name="GajaTest")
	public Object[][] testData() throws Exception {
		return Util.getDataFromExcel(Util.FILE_PATH,
				Util.SHEET_NAME, Util.TABLE_NAME);
	}


	// Before Testing Setup test environment before executing test

	@BeforeMethod
	public void setUp()  {

		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		baseUrl = Util.BASE_URL;
		driver.get(baseUrl + "/V4/");
	}
	/**
	 * Above test script executed several times for each set of data used in @DataProvider
	 * annotation. Any failed test does not impact other set of execution.
	 * 
	 * SS1: Enter valid userid & password 
	 * Expected: Login successful home page shown 
	 * 
	 * SS2: Enter invalid userid & valid password 
	 * SS3: Enter valid userid & invalid password 
	 * SS4: Enter invalid userid & invalid password 
	 * Expected:A pop-up “User or Password is not valid” is shown
	 */
	@Test(dataProvider="GajaTest")
	public void testCase(String username, String password) {
		String actualTitle;
		String actualBoxMsg;
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();

		/** Determine Pass Fail Status of the Script
		 * If login credentials are correct,  Alert(Pop up) is NOT present. An Exception is thrown and code in catch block is executed	
		 * If login credentials are invalid, Alert is present. Code in try block is executed 	    
		 **/
		try{ 
			Alert alt = driver.switchTo().alert();
			actualBoxMsg = alt.getText(); // get content of the Alert Message
			alt.accept();
			// Compare Error Text with Expected Error Value	
			assertEquals(actualBoxMsg,Util.EXPECT_ERROR);
		}    
		catch (NoAlertPresentException Ex){ 
			actualTitle = driver.getTitle();
			// On Successful login compare Actual Page Title with Expected Title
			assertEquals(actualTitle,Util.EXPECT_TITLE);
		} 
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	} 
}



