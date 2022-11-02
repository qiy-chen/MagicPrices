package com.MagicPrices.controller;

import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;

public class CardDatabaseController {
  private static FetcherSystem system = MainController.getFetcherSystem();
  private static MainMenu menu = MainController.getMainMenu();
  private static CardDatabase database = MainController.getCardDatabase();
  
  public CardDatabaseController() {
    
  }
  
  public static Card findCardById(String cardId) {
    for (int i = 0; i<database.getCards().size();i++) {
      if (database.getCard(i).getCardId() == cardId) return database.getCard(i);
    }
    return null;
  }

}
