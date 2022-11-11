package com.MagicPrices.controller;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.model.Price;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;

public class CardDatabaseController {
  private FetcherSystem system = MainController.getFetcherSystem();
  private MainMenu menu = MainController.getMainMenu();
  private CardDatabase database = MainController.getCardDatabase();
  
  private CardRepository cardRepository;
  private PriceRepository priceRepository;

  public CardDatabaseController() {

  }

  /**
   * Find a particular card by its id in the database using linear search
   * @param cardId - card to be found using it's id
   * @return the card's object, null if not found
   */
  @Deprecated
  public Card findCardById(String cardId) {
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
  @Deprecated
  public List<Card> findCardsById(String cardId) {
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
  @Deprecated
  public List<Card> findCardsByName(String cardName) {
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
  @Deprecated
  public Card findCardByIdBinarySearch(String cardId) {
    int index = binarySearch(database.getCards(), cardId, 0, database.getCards().size()-1);
    if (index == -1) return null;
    else return database.getCard(index);
  }

  /**
   * Print the content of the repository if not empty
   * @param database - database to be printed
   */

  public void printRepository() {
    //print all cards and their prices
    List<Card> list = (List<Card>) cardRepository.findAll();
    printCards(list);
  }

  public Card searchRepositoryById(String cardId) {
    Card card = cardRepository.findCardByCardId(cardId);
    return card;
  }
  
  public List<Card> searchRepositoryByName(String cardName) {
    List<Card> list = (List<Card>) cardRepository.findAll();
    List<Card> resultList = new ArrayList<Card>();
    for (Card c: list) {
      if (c.getName().toLowerCase().replaceAll(" ", "").contains(cardName)) resultList.add(c);
    }
    return resultList;
  }
  
  

  /**
   * Print all the prices of a card
   * @param card - Card to be printed
   */
  public void printCard(Card card) {
    List<Card> list = new ArrayList<Card>();
    list.add(card);
    printCards(list);
  }

  /**
   * Print all the prices of a list of cards
   * @param card - Cards prices to be printed
   */
  public void printCards(List<Card> cards) {
    if (cards.size() == 0) {
      System.out.println("No card found.");
      return;
    }
    for (Card c: cards) {
      System.out.println("--------------------------------------");
      System.out.println("Prices of "+c.getName()+" | "+c.getCategory());
      System.out.println("(Card Id: "+c.getCardId()+")");
      System.out.println("--------------------------------------");
      System.out.println("Price\tIn Stock\tIs NM\tFoil\tDate");
      System.out.println("--------------------------------------");
      Hibernate.initialize(c.getPrices());
      for (Price price: c.getPrices()) {
        System.out.println(price.getAmount() +"\t"+ price.getAmountInStock() +"\t\t"+ price.getCondition() +"\t"+ price.getFoiling() +"\t"+ price.getFetchDate());
      }
      System.out.println("--------------------------------------");
    }
  }
  /**
   * Delete the content of all repositories
   */
  public void clearAllRepositories() {
    priceRepository.deleteAll();
    cardRepository.deleteAll();
  }
  public void clearPrices() {
    List<Card> list = (List<Card>) cardRepository.findAll();
    clearAllRepositories();
    for (Card c: list) {
      String cardId = c.getCardId();
      String name = c.getName();
      String set = c.getCategory();
      Card card = new Card();
      card.setCardId(cardId);
      card.setCategory(set);
      card.setName(name);
      cardRepository.save(card);
    }
  }

  /**
   * Recursive method to execute binary search on an array
   * @param array - Full array of Card
   * @param x - The id of the card to be found
   * @param low - Start the search with this value at 0
   * @param high - Start the search with this value at array.size()-1
   * @return - Position of the card, -1 if not present in the array
   */
  public static int binarySearch(List<Card> array, String x, int low, int high) {

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
  
  public void setRepositories(CardRepository cardRepository, PriceRepository priceRepository) {
    this.cardRepository = cardRepository;
    this.priceRepository = priceRepository;
  }
}

