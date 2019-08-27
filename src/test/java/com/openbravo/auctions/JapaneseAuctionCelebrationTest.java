package com.openbravo.auctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

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

@RunWith(Parameterized.class)
public class JapaneseAuctionCelebrationTest {
  private WebDriver webDriver;

  private String auctionId;
  private ArrayList<String> buyersIds;

  private Integer auctionDurationInMinutes;

  public JapaneseAuctionCelebrationTest(String auctionId, ArrayList<String> buyersIds,
      Integer auctionDurationInMinutes) {
    super();
    this.auctionId = auctionId;
    this.buyersIds = buyersIds;
    this.auctionDurationInMinutes = auctionDurationInMinutes;
  }

  @Parameters
  public static Collection<Object[]> data() {
    ArrayList<String> auction1BuyersIds = new ArrayList<String>();
    auction1BuyersIds.add("929925");
    auction1BuyersIds.add("216312");
    auction1BuyersIds.add("776199");
    auction1BuyersIds.add("266859");
    auction1BuyersIds.add("852384");

    Object[][] data = new Object[][] { { "907634", auction1BuyersIds, 240 } };

    return Arrays.asList(data);
  }

  @Before
  public void setup() {
    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
    webDriver = new ChromeDriver();
  }

  @Test
  public void japaneseAuctionCelebrationTest() {
    WebElement leaveAuctionButtonElement = null;
    WebElement updateButtonElement = null;

    int fractionOfTimeInMinutes = auctionDurationInMinutes / 4;

    for (int i = 0; i < 3; i++) {
      System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
      webDriver = new ChromeDriver();

      webDriver.manage().window().maximize();

      String buyersId = buyersIds.get(i);

      webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id="
          + auctionId + "&buyer_id=" + buyersId);

      leaveAuctionButtonElement = webDriver.findElement(By.id("leave_auction_button"));
      leaveAuctionButtonElement.click();

      buyersIds.remove(buyersId);

      try {
        TimeUnit.SECONDS.sleep(fractionOfTimeInMinutes - 5);
      } catch (InterruptedException e1) {
      }
    }

    webDriver.manage().window().maximize();

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(4));

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();
  }

  @After
  public void tearDown() {
    // webDriver.quit();
  }
}
