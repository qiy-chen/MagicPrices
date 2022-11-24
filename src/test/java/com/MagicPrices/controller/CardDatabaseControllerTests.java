package com.MagicPrices.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.Price;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;

@SpringBootTest
class CardDatabaseControllerTests {
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
    public void searchRepositoryByIdTest() {
      CardDatabaseController cardDBController = new CardDatabaseController();
      cardDBController.setRepositories(cardRepository, priceRepository);
      Card card = new Card();
      card.setCardId("exampleId");
      card.setCategory("setName");
      
      Card card2 = new Card();
      card2.setCardId("exampleId2");
      card2.setCategory("setName2");
      
      card = cardRepository.save(card);
      card2 = cardRepository.save(card2);
      Card cardFound = cardDBController.searchRepositoryById("exampleId");
      assertNotNull(cardFound);
      assertEquals("exampleId",cardFound.getCardId());
      assertEquals("setName",cardFound.getCategory());
    }
    
    @Test
    public void searchRepositoryByNameTest() {
      CardDatabaseController cardDBController = new CardDatabaseController();
      cardDBController.setRepositories(cardRepository, priceRepository);
      Card card = new Card();
      card.setCardId("id1");
      card.setName("exampleName");
      card.setCategory("setName");
      
      Card card2 = new Card();
      card2.setCardId("id2");
      card2.setName("exampleName2");
      card2.setCategory("setName2");
      
      Card card3 = new Card();
      card3.setCardId("id3");
      card3.setName("hello");
      card3.setCategory("setName3");
      
      card = cardRepository.save(card);
      card2 = cardRepository.save(card2);
      card3 = cardRepository.save(card3);
      
      List<Card> cardFounds = cardDBController.searchRepositoryByName("example");
      assertNotNull(cardFounds);
      assertEquals(2,cardFounds.size());
      assertEquals("id1",cardFounds.get(0).getCardId());
      
      cardFounds = cardDBController.searchRepositoryByName("hello");
      
      assertNotNull(cardFounds);
      assertEquals(1,cardFounds.size());
      assertEquals("id3",cardFounds.get(0).getCardId());
    }
    
    @Test
    public void filterPricesTest() {
      CardDatabaseController cardDBController = new CardDatabaseController();
      cardDBController.setRepositories(cardRepository, priceRepository);
      Card card = new Card();
      card.setCardId("exampleId");
      card.setCategory("setName");
      Price price = new Price();
      LocalDateTime date = LocalDateTime.now();
      price.setFetchDate(date);
      price.setCondition("NM");
      price.setFoiling("Non-Foil");
      price.setFetchDateString(date.toString());
      price.setCard(card);
      Price price2 = new Price();
      price2.setFetchDate(date.minus(Duration.ofDays(3)));
      price2.setCondition("PL");
      price2.setFoiling("Foil");
      price2.setFetchDateString(date.minus(Duration.ofDays(3)).toString());
      price2.setCard(card);
      Price price3 = new Price();
      price3.setFetchDate(date.minus(Duration.ofDays(3)));
      price3.setCondition("NM");
      price3.setFoiling("Non-Foil");
      price3.setFetchDateString(date.minus(Duration.ofDays(3)).toString());
      price3.setCard(card);
      Price price4 = new Price();
      price4.setFetchDate(date.minus(Duration.ofDays(6)));
      price4.setCondition("NM");
      price4.setFoiling("Non-Foil");
      price4.setFetchDateString(date.minus(Duration.ofDays(6)).toString());
      price4.setCard(card);

      card = cardRepository.save(card);
      price = priceRepository.save(price);
      price2 = priceRepository.save(price2);
      price3 = priceRepository.save(price3);
      price4 = priceRepository.save(price4);
      
      List<Card> filteredCards = cardDBController.filterPrices(Collections.singletonList(card),date.minus(Duration.ofDays(3)),date,Collections.singletonList("NM"),Collections.singletonList("Non-Foil"));
      assertEquals(2,filteredCards.get(0).getPrices().size());
      assertEquals("NM",filteredCards.get(0).getPrice(0).getCondition());
      assertEquals("Non-Foil",filteredCards.get(0).getPrice(0).getFoiling());
    }

    @Test
    public void getCardsPricesSpecificTest() {
      CardDatabaseController cardDBController = new CardDatabaseController();
      cardDBController.setRepositories(cardRepository, priceRepository);
      Card card = new Card();
      card.setCardId("exampleId");
      card.setCategory("setName");
      Price price = new Price();
      LocalDateTime date = LocalDateTime.now();
      price.setFetchDate(date);
      price.setCondition("NM");
      price.setFoiling("Non-Foil");
      price.setFetchDateString(date.toString());
      price.setCard(card);
      Price price2 = new Price();
      price2.setFetchDate(date.minus(Duration.ofDays(3)));
      price2.setCondition("PL");
      price2.setFoiling("Foil");
      price2.setFetchDateString(date.minus(Duration.ofDays(3)).toString());
      price2.setCard(card);
      Price price3 = new Price();
      price3.setFetchDate(date.minus(Duration.ofDays(3)));
      price3.setCondition("NM");
      price3.setFoiling("Non-Foil");
      price3.setFetchDateString(date.minus(Duration.ofDays(3)).toString());
      price3.setCard(card);
      Price price4 = new Price();
      price4.setFetchDate(date.minus(Duration.ofDays(6)));
      price4.setCondition("NM");
      price4.setFoiling("Non-Foil");
      price4.setFetchDateString(date.minus(Duration.ofDays(6)).toString());
      price4.setCard(card);

      card = cardRepository.save(card);
      price = priceRepository.save(price);
      price2 = priceRepository.save(price2);
      price3 = priceRepository.save(price3);
      price4 = priceRepository.save(price4);
      
      List<Card> filteredCards = cardDBController.getCardsPricesSpecific(Collections.singletonList(card),date.minus(Duration.ofDays(3)),date,Collections.singletonList("NM"),Collections.singletonList("Non-Foil"));
      assertEquals(2,filteredCards.get(0).getPrices().size());
      assertEquals("NM",filteredCards.get(0).getPrice(0).getCondition());
      assertEquals("Non-Foil",filteredCards.get(0).getPrice(0).getFoiling());
    }

}
