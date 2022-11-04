package com.MagicPrices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.test.context.SpringBootTest;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.controller.FetcherController;
import com.MagicPrices.controller.MainController;

@SpringBootTest
class FetchTests {
private static WebDriver driver;
    
@AfterEach
public void cleanup() {
  if (driver!=null) driver.close();
}

    @Test
    //Test basic fetch all visible cards option from card name input
    public void fetchCardByCardNameTest() {
      driver = new SafariDriver();
      FetcherController.fetchCardByCardName("fellwar stone",false,true,false,false,true,driver);
      int resultNb = Integer.parseInt(driver.findElement(By.className("hawk__results-title")).getText().trim().replaceAll(" search results for 'fellwar stone'", ""));
      CardDatabase database = MainController.getCardDatabase();
      assertEquals(resultNb,database.getCards().size());
    }

}
