package com.openbravo.auctions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Parameterized.class)
public class EnglishAuctionCelebrationTest {
  private WebDriver webDriver;

  private String auctionId;
  private ArrayList<String> buyersIds;

  private Integer auctionDurationInMinutes;

  public EnglishAuctionCelebrationTest(String auctionId, ArrayList<String> buyersIds,
      Integer auctionDurationInMinutes) {
    this.auctionId = auctionId;
    this.buyersIds = buyersIds;
    this.auctionDurationInMinutes = auctionDurationInMinutes;
  }

  @Parameters
  public static Collection<Object[]> data() {
    ArrayList<String> auction1BuyersIds = new ArrayList<String>();
    auction1BuyersIds.add("462837");
    auction1BuyersIds.add("911330");
    auction1BuyersIds.add("468400");
    auction1BuyersIds.add("809408");
    auction1BuyersIds.add("985710");

    Object[][] data = new Object[][] { { "671259", auction1BuyersIds, 11 } };

    return Arrays.asList(data);
  }

  @Before
  public void setup() {
    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
    webDriver = new ChromeDriver();
  }

  @Test
  public void englishAuctionCelebrationTest() {
    WebElement newOfferInputElement = null;
    WebElement confirmButtonElement = null;
    WebElement updateButtonElement = null;

    int fractionOfTimeInMinutes = auctionDurationInMinutes / 4;

    webDriver.manage().window().maximize();

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(0));

    newOfferInputElement = webDriver.findElement(By.id("input_offer"));
    newOfferInputElement.sendKeys("176");
    confirmButtonElement = webDriver.findElement(By.id("confirm_offer_button"));
    confirmButtonElement.click();
    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();

    assertEquals("176 €", webDriver.findElement(By.id("highest_offer")).getText());

    // ------------------------------------------------------------------------------------------------

    System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver");
    webDriver = new FirefoxDriver();

    webDriver.manage().window().maximize();

    try {
      TimeUnit.SECONDS.sleep(calculateTimeDelay(fractionOfTimeInMinutes));
    } catch (InterruptedException e1) {
    }

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(1));

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();
    assertEquals("176 €", webDriver.findElement(By.id("highest_offer")).getText());

    newOfferInputElement = webDriver.findElement(By.id("input_offer"));
    newOfferInputElement.sendKeys("177");
    confirmButtonElement = webDriver.findElement(By.id("confirm_offer_button"));
    confirmButtonElement.click();

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();
    assertEquals("177 €", webDriver.findElement(By.id("highest_offer")).getText());

    // ------------------------------------------------------------------------------------------------

    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
    webDriver = new ChromeDriver();

    webDriver.manage().window().maximize();

    try {
      TimeUnit.SECONDS.sleep(calculateTimeDelay(fractionOfTimeInMinutes));
    } catch (InterruptedException e1) {
    }

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(2));

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();
    assertEquals("177 €", webDriver.findElement(By.id("highest_offer")).getText());

    newOfferInputElement = webDriver.findElement(By.id("input_offer"));
    newOfferInputElement.sendKeys("180");
    confirmButtonElement = webDriver.findElement(By.id("confirm_offer_button"));
    confirmButtonElement.click();

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();
    assertEquals("180 €", webDriver.findElement(By.id("highest_offer")).getText());

    // ------------------------------------------------------------------------------------------------

    System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver");
    webDriver = new FirefoxDriver();

    webDriver.manage().window().maximize();

    try {
      TimeUnit.SECONDS.sleep(calculateTimeDelay(fractionOfTimeInMinutes));
    } catch (InterruptedException e1) {
    }

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(3));

    newOfferInputElement = webDriver.findElement(By.id("input_offer"));
    newOfferInputElement.sendKeys("100");
    confirmButtonElement = webDriver.findElement(By.id("confirm_offer_button"));
    confirmButtonElement.click();

    assertEquals("¡Error!", webDriver.findElement(By.id("error")).getText());

    // ------------------------------------------------------------------------------------------------

    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
    webDriver = new ChromeDriver();

    webDriver.manage().window().maximize();

    try {
      TimeUnit.SECONDS.sleep(calculateTimeDelay(fractionOfTimeInMinutes));
    } catch (InterruptedException e1) {
    }

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(4));

    newOfferInputElement = webDriver.findElement(By.id("input_offer"));
    newOfferInputElement.sendKeys("120");
    confirmButtonElement = webDriver.findElement(By.id("confirm_offer_button"));
    confirmButtonElement.click();

    assertEquals("¡Error!", webDriver.findElement(By.id("error")).getText());
  }

  @After
  public void tearDown() {
    // webDriver.quit();
  }

  private Integer calculateTimeDelay(Integer fractionOfTimeInMinutes) {
    return RandomUtils.nextInt(0, fractionOfTimeInMinutes);
  }
}
