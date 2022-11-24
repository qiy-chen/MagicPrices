package com.MagicPrices.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.MagicPrices.model.Card;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest
class FetchControllerTests {
@Autowired
private CardRepository cardRepository;
@Autowired
private PriceRepository priceRepository;
    

@BeforeEach
public void startup() {
  priceRepository.deleteAll();
  cardRepository.deleteAll();
}

@AfterEach
public void cleanup() {
  priceRepository.deleteAll();
  cardRepository.deleteAll();
}

    @Test
    //Test basic fetch all visible cards option from card name input
    public void fetchCardByCardNameTest() {
      WebDriver driver = MainController.getWebDriver();
      FetcherController fetcherController = new FetcherController();
      fetcherController.setRepositories(cardRepository, priceRepository);
      fetcherController.fetchCardByCardName("fellwar stone",false,true,false,false,true,driver);
      int resultNb = Integer.parseInt(driver.findElement(By.className("hawk__results-title")).getText().trim().replaceAll(" search results for 'fellwar stone'", ""));
      List<Card> list = (List<Card>) cardRepository.findAll();
      assertEquals(resultNb,list.size());
      driver.close();
    }
    @Test
    public void fetchCardByCardNameFailedInitializationTest() {
      FetcherController fetcherController = new FetcherController();
      assertFalse(fetcherController.fetchCardByCardName("fellwar stone",false,true,false,false,true,null));
      WebDriver driver = MainController.getWebDriver();
      assertFalse(fetcherController.fetchCardByCardName("fellwar stone",false,true,false,false,true,driver));
      fetcherController.setRepositories(cardRepository, priceRepository);
    }
    @Test
    public void printPageFromURLTest() {
      WebDriver driver = MainController.getWebDriver();
      FetcherController fetcherController = new FetcherController();
      fetcherController.setRepositories(cardRepository, priceRepository);
      String url = FetcherController.generateURL("sneak attack", false, false, false, true, 1);
      List<WebElement> listOfCards = fetcherController.printPageFromURL(url,driver);
      int resultNb = Integer.parseInt(driver.findElement(By.className("hawk__results-title")).getText().trim().replaceAll(" search results for 'sneak attack'", ""));
      assertEquals(resultNb,listOfCards.size());
      driver.close();
    }
    

}
