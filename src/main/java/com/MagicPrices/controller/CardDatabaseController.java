package com.MagicPrices.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.model.Price;

public class CardDatabaseController {
  private static FetcherSystem system = MainController.getFetcherSystem();
  private static MainMenu menu = MainController.getMainMenu();
  private static CardDatabase database = MainController.getCardDatabase();

  public CardDatabaseController() {

  }

  /**
   * Find a particular card by its id in the database using linear search
   * @param cardId - card to be found using it's id
   * @return the card's object, null if not found
   */
  public static Card findCardById(String cardId) {
    for (int i = 0; i<database.getCards().size();i++) {
      if (database.getCard(i).getCardId().equals(cardId)) return database.getCard(i);
    }
    return null;
  }

  /**
   * Find a particular card by its id in the database using binary search (list must be sorted before)
   * @param cardId - card to be found using it's id
   * @return the card's object, null if not found
   */
  public static Card findCardByIdBinarySearch(String cardId) {
    BinarySearch search = new BinarySearch();
    int index = search.binarySearch(database.getCards(), cardId, 0, database.getCards().size()-1);
    if (index == -1) return null;
    else return database.getCard(index);
  }

  /**
   * Sort the database in alphabetical order
   * @param database - database to be sorted
   * @return success status
   */
  public static boolean rebuilDatabase(CardDatabase database) {
    try {
      //Create editable list
      List<Card> ListOfCards = new ArrayList<Card>();
      ListOfCards.addAll(database.getCards());
      Collections.sort(ListOfCards);
      database.setCards(ListOfCards);
      System.out.println("Rebuilt Database Successfully");
    }
    catch (Exception e) {
      System.out.println("Error: "+e);
      return false;
    }
    return true;
  }

  /**
   * Print the content of the database if not empty
   * @param database - database to be printed
   */
  public static void printDatabase(CardDatabase database) {
    //print all cards and their prices
    System.out.println("Database Content:");
    System.out.println("--------------------------------------");
    if (!database.hasCards()) System.out.println("The database is empty.");
    for (Card card: database.getCards()) {
      System.out.println("Prices of "+card.getName()+" | "+card.getCategory());
      System.out.println("(Card Id: "+card.getCardId()+")");
      System.out.println("--------------------------------------");
      System.out.println("Price\tIn Stock\tIs NM\tFoil\tDate");
      System.out.println("--------------------------------------");
      for (Price price: card.getPrices()) {
        System.out.println(price.getAmount() +"\t"+ price.getAmountInStock() +"\t\t"+ price.getCondition() +"\t"+ price.getFoiling() +"\t"+ price.getFetchDate());
      }
      System.out.println("--------------------------------------");
    }
    System.out.println("--------------------------------------");
  }

  public static void printCard(Card card) {
    if (card == null) {
      System.out.println("Card is non-existant.");
      return;
    }
    System.out.println("Card Content:");
    System.out.println("--------------------------------------");
    System.out.println("Prices of "+card.getName()+" | "+card.getCategory());
    System.out.println("(Card Id: "+card.getCardId()+")");
    System.out.println("--------------------------------------");
    System.out.println("Price\tIn Stock\tIs NM\tFoil\tDate");
    System.out.println("--------------------------------------");
    for (Price price: card.getPrices()) {
      System.out.println(price.getAmount() +"\t"+ price.getAmountInStock() +"\t\t"+ price.getCondition() +"\t"+ price.getFoiling() +"\t"+ price.getFetchDate());
    }
    System.out.println("--------------------------------------");
  }
}

class BinarySearch {
  public int binarySearch(List<Card> array, String x, int low, int high) {

    if (high >= low) {
      int mid = low + (high - low) / 2;

      // If found at mid, then return it
      if (array.get(mid).getCardId().equals(x))
        return mid;

      // Search the left half
      if (array.get(mid).getCardId().compareTo(x) > 0)
        return binarySearch(array, x, low, mid - 1);

      // Search the right half
      return binarySearch(array, x, mid + 1, high);
    }

    return -1;
  }

}
