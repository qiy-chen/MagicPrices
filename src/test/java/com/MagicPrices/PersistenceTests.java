package com.MagicPrices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.Price;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;

@SpringBootTest
class PersistenceTests {
  @Autowired
  private static CardRepository cardRepository;
  @Autowired
  private static PriceRepository priceRepository;
    
@AfterEach
public void cleanup() {
  priceRepository.deleteAll();
  cardRepository.deleteAll();
}

    @Test
    //Test persistence of card
    public void createAndSearchCard() {
      String cardId = "1234";
      String cardName = "NameTest";
      String cardSet = "SetTest";
      Card card = new Card();
      card.setCardId(cardId);
      card.setCategory(cardSet);
      card.setName(cardName);
      
      cardRepository.save(card);
      card = null;
      
      card = cardRepository.findCardByCardId(cardId);
      assertNotNull(card);
      
      assertEquals(cardId, card.getCardId());
      assertEquals(cardName, card.getName());
      assertEquals(cardSet, card.getCategory());
      
    }
    
    @Test
    //Test persistence of price
    public void createAndSearchPrice() {
      String cardId = "1234";
      String cardName = "NameTest";
      String cardSet = "SetTest";
      Card card = new Card();
      card.setCardId(cardId);
      card.setCategory(cardSet);
      card.setName(cardName);
      
      cardRepository.save(card);
      
      double amount = 10.00;
      double concurrentPrice = 12.00;
      String condition = "Near-Mint";
      int amountInStock = 2;
      String foiling = "Non-Foil";
      LocalDateTime fetchDate = LocalDateTime.now();
      String fetchDateString = fetchDate.toString();
      Price price = new Price();
      price = priceRepository.save(price);
      Integer priceId = price.getPriceId();
      
      card = null;
      price = null;
      
      price = priceRepository.findPriceByPriceId(priceId);
      assertNotNull(price);
      
      assertEquals(priceId, price.getPriceId());
      assertEquals(amount, price.getAmount());
      assertEquals(concurrentPrice, price.getConcurrentPrice());
      assertEquals(condition, price.getCondition());
      assertEquals(amountInStock, price.getAmountInStock());
      assertEquals(foiling, price.getFoiling());
      assertEquals(fetchDate, price.getFetchDate());
      assertEquals(fetchDateString, price.getFetchDateString());      
    }

}
