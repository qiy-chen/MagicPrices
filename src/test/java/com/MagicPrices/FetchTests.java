package com.MagicPrices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import com.MagicPrices.controller.FetcherController;
import com.MagicPrices.controller.MainController;

@SpringBootTest
class FetchTests {
private static WebDriver driver;
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
  if (driver!=null) driver.close();
  priceRepository.deleteAll();
  cardRepository.deleteAll();
}

    @Test
    //Test basic fetch all visible cards option from card name input
    public void fetchCardByCardNameTest() {
      driver = new SafariDriver();
      FetcherController fetcherController = new FetcherController();
      fetcherController.setRepositories(cardRepository, priceRepository);
      fetcherController.fetchCardByCardName("fellwar stone",false,true,false,false,true,driver);
      int resultNb = Integer.parseInt(driver.findElement(By.className("hawk__results-title")).getText().trim().replaceAll(" search results for 'fellwar stone'", ""));
      List<Card> list = (List<Card>) cardRepository.findAll();
      assertEquals(resultNb,list.size());
    }

}
