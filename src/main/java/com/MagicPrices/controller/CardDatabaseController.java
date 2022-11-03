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
   * Find all cards that match the id in the database using linear search
   * @param cardId - list of cards to be found matching the id given
   * @return list of cards matching the id
   */
  public static List<Card> findCardsById(String cardId) {
    List<Card> listOfCards = new ArrayList<Card>();
    for (int i = 0; i<database.getCards().size();i++) {
      if (database.getCard(i).getCardId().contains(cardId))
        listOfCards.add(database.getCard(i));
    }
    return listOfCards;
  }

  /**
   * Find all cards that match the name given in the database using linear search
   * @param cardId - list of cards to be found matching the name given
   * @return list of cards matching the name
   */
  public static List<Card> findCardsByName(String cardName) {
    List<Card> listOfCards = new ArrayList<Card>();
    for (int i = 0; i<database.getCards().size();i++) {
      if (database.getCard(i).getName().toLowerCase().replaceAll(" ", "").contains(cardName))
        listOfCards.add(database.getCard(i));
    }
    return listOfCards;
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

public static void printCards(List<Card> cards) {
  if (cards.size() == 0) {
    System.out.println("No card found.");
    return;
  }
  for (Card c: cards) {
    System.out.println("Card Content:");
    System.out.println("--------------------------------------");
    System.out.println("Prices of "+c.getName()+" | "+c.getCategory());
    System.out.println("(Card Id: "+c.getCardId()+")");
    System.out.println("--------------------------------------");
    System.out.println("Price\tIn Stock\tIs NM\tFoil\tDate");
    System.out.println("--------------------------------------");
    for (Price price: c.getPrices()) {
      System.out.println(price.getAmount() +"\t"+ price.getAmountInStock() +"\t\t"+ price.getCondition() +"\t"+ price.getFoiling() +"\t"+ price.getFetchDate());
    }
    System.out.println("--------------------------------------");
  }
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
