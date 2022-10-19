package com.accenture.client;

//import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

class KopeeteariaDashboardSeleniumTest {
	
	private ChromeDriver driver;
	private String baseUrl = "http://localhost:3000";

	
	@BeforeClass
	public void setup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\chromedriver.exe");
    	driver = new ChromeDriver() ;
    	// Maximize window
        driver.manage().window().maximize();
        
        // Navigate to site URL
        driver.get(baseUrl);
        
        Thread.sleep(3000);
	}
    
	@Test
	public void addOrderTest() throws InterruptedException {
		System.out.println("Testing AddOrder");
        placeOrder("Latte", "4.5", true);
        Thread.sleep(1000);
        placeOrder("Cappuccino", "5.5", false);
        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("Latte"));
	}
	
	@Test
	public void editOrderTest() throws InterruptedException{
		System.out.println("Testing editOrder");
		// edit feature
        Thread.sleep(3000);
        driver.findElementById("btn_edit").click();
        // update feature
        driver.findElementById("orderName_upd").sendKeys(Keys.CONTROL + "a"); //clear text box
		driver.findElementById("orderName_upd").sendKeys(Keys.DELETE);
        driver.findElementById("orderName_upd").sendKeys("Today's Brew");
		driver.findElementById("price_upd").sendKeys(Keys.CONTROL + "a"); //clear text box
		driver.findElementById("price_upd").sendKeys(Keys.DELETE);
        driver.findElementById("price_upd").sendKeys("3.5");
        driver.findElementById("promo_upd").click();
        driver.findElementById("btn_update").click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("Today's Brew"));
	}
	
	@Test
	public void deleteOrderTest() throws InterruptedException {
		System.out.println("Testing deleteOrder");
		Thread.sleep(1000);
		driver.findElementById("btn_delete").click();
		Assert.assertFalse(driver.getPageSource().contains("Today's Brew"));
	}

    
    public void placeOrder(String orderName, String orderPrice, boolean selectDiscount) {
    	driver.findElementById("orderName").sendKeys(orderName);
        driver.findElementById("price").sendKeys(orderPrice);
        // TODO: Remove [disabled] attribute in order.component.html to allow update for this field
        if (selectDiscount) {
        	driver.findElementById("promo").click();
		}
        
        driver.findElementById("btn_add").click();
    }
	
}
