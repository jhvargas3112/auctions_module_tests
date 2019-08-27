package com.openbravo.auctions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
public class DutchAuctionCelebrationTest {
  private WebDriver webDriver;

  private String auctionId;
  private ArrayList<String> buyersIds;

  private Integer auctionDurationInMinutes;

  public DutchAuctionCelebrationTest(String auctionId, ArrayList<String> buyersIds,
      Integer auctionDurationInMinutes) {
    super();
    this.auctionId = auctionId;
    this.buyersIds = buyersIds;
    this.auctionDurationInMinutes = auctionDurationInMinutes;
  }

  @Parameters
  public static Collection<Object[]> data() {
    ArrayList<String> auction1BuyersIds = new ArrayList<String>();
    auction1BuyersIds.add("419613");
    auction1BuyersIds.add("397859");
    auction1BuyersIds.add("368989");
    auction1BuyersIds.add("445822");
    auction1BuyersIds.add("201526");

    Object[][] data = new Object[][] { { "902983", auction1BuyersIds, 240 } };

    return Arrays.asList(data);
  }

  @Before
  public void setup() {
    System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
    webDriver = new ChromeDriver();
  }

  @Test
  public void ducthAuctionCelebrationTest() {
    WebElement acceptCurrentPriceButtonElement = null;
    WebElement updateButtonElement = null;

    int minutesConsumed = 0;

    webDriver.manage().window().maximize();

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(RandomUtils.nextInt(0, 5)));

    updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
    updateButtonElement.click();

    double lastPrice = Double.parseDouble(StringUtils
        .replace(webDriver.findElement(By.id("current_price")).getText().split(" ")[0], ",", "."));

    while (minutesConsumed < auctionDurationInMinutes - 5) {
      System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver");
      webDriver = new ChromeDriver();

      webDriver.manage().window().maximize();

      webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id="
          + auctionId + "&buyer_id=" + buyersIds.get(RandomUtils.nextInt(0, 5)));

      updateButtonElement = webDriver.findElement(By.id("update_auction_info_button"));
      updateButtonElement.click();

      StringUtils.replace(auctionId, ",", ".");

      double currentPrice = Double.parseDouble(StringUtils.replace(
          webDriver.findElement(By.id("current_price")).getText().split(" ")[0], ",", "."));

      if (currentPrice != lastPrice) {
        assertEquals((currentPrice < lastPrice), true);
      }

      try {
        TimeUnit.MINUTES.sleep(1);
      } catch (InterruptedException e1) {
      }

      minutesConsumed++;
    }

    webDriver.manage().window().maximize();

    webDriver.get("http://192.168.0.157:8111/openbravo/auction/celebration?auction_id=" + auctionId
        + "&buyer_id=" + buyersIds.get(RandomUtils.nextInt(0, 5)));

    acceptCurrentPriceButtonElement = webDriver.findElement(By.id("accept_current_price_button"));
    acceptCurrentPriceButtonElement.click();

    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e1) {
    }

    assertEquals("Subasta holandesa", webDriver.getTitle());
  }

  @After
  public void tearDown() {
    // webDriver.quit();
  }
}
