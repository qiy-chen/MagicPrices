package com.MagicPrices.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  /**
   * Search the repository for the cards containing the given id
   * @param cardName - Id of the card for the search
   * @return Card with the given id, null if not found
   */
  public Card searchRepositoryById(String cardId) {
    Card card = cardRepository.findCardByCardId(cardId);
    return card;
  }

  /**
   * Search the repository for all cards containing the given name
   * @param cardName - Name of the card for the search
   * @return List of Card containing the given name
   */
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
   * @param cards - Cards to be printed
   */
  public void printCards(List<Card> cards) {
    if (cards.size() == 0) {
      System.out.println("No card found.");
      return;
    }
    for (Card c: cards) {
      printSeparator();
      System.out.println("Prices of "+c.getName()+" | "+c.getCategory());
      System.out.println("(Card Id: "+c.getCardId()+")");
      printPrices(c.getPrices());
    }
  }

  /**
   * Give a list of cards of prices within specified time boundaries (included) and pricing options of a list of cards 
   * @param cards - List of Card to be filtered
   * @return - a list of cards with filtered prices
   */
  public List<Card> filterPrices(List<Card> cards,LocalDateTime oldestTime,LocalDateTime newestTime,List<String> condition,List<String> foiling) {
    List<Card> filteredCards = new ArrayList<Card>();
    if (cards.size() == 0) {
      System.out.println("No card found.");
      return filteredCards;
    }
    if (cards.size() != condition.size() || cards.size()!=foiling.size()) {
      System.out.println("Wrong lists length.");
      return filteredCards;
    }
    int indexCard = 0;
    for (Card c: cards) {
      List<Price> filteredPrice = new ArrayList<Price>();
      for (Price price: c.getPrices()) {
        LocalDateTime fetchDate = price.getFetchDate();
        String conditionPrice = price.getCondition();
        String foilingPrice = price.getFoiling();
        //Check time bounds
        if ((fetchDate.isAfter(oldestTime)|| fetchDate.isEqual(oldestTime)) && (fetchDate.isBefore(newestTime) || fetchDate.isEqual(newestTime))) {
          //Check condition and foiling status
          if (conditionPrice.equals(condition.get(indexCard)) && foilingPrice.equals(foiling.get(indexCard))) filteredPrice.add(price);
        }
        
      }
      c.setCardPrices(filteredPrice);
      filteredCards.add(c);
      indexCard++;
    }
    return filteredCards;
  }
  
  /**
   * Give a list of Card containing only the prices within the given boundaries (included) with the specified condition and foiling
   * @param cards - List of Card to be filtered
   * @param oldestTime - Oldest LocalDateTime that prices can possess
   * @param newestTime - Newest LocalDateTime that prices can possess
   * @param condition - List of conditions, each value will be applied for each corresponding card (based on index)
   * @param foiling - List of foiling, each value will be applied for each corresponding card (based on index)
   * @return A list of Card containing only the prices within the given boundaries (included) with the specified condition and foiling
   */
  public List<Card> getCardsPricesSpecific(List<Card> cards,LocalDateTime oldestTime,LocalDateTime newestTime,List<String> condition,List<String> foiling) {
    List<Card> filteredCards = new ArrayList<Card>();
    filteredCards = filterPrices(cards, oldestTime, newestTime, condition, foiling);
    return filteredCards;
  }
  
  /**
   * Give a list of Card containing only the most recent price with the specified condition and foiling
   * @param listCards - List of Card to be filtered
   * @param condition - List of conditions, each value will be applied for each corresponding card (based on index)
   * @param foiling - List of foiling, each value will be applied for each corresponding card (based on index)
   * @return A list of Card containing only the most recent price with the specified condition and foiling
   */
  public List<Card> getCardsPricesMostRecent(List<Card> listCards,List<String> condition,List<String> foiling) {
    List<Card> filteredCards = new ArrayList<Card>();
    if (listCards.size() != condition.size() || listCards.size()!=foiling.size()) {
      System.out.println("Wrong lists length.");
      return filteredCards;
    }
    int cardIndex = 0;
    for (Card card: listCards) {
      Card repositoryCard = searchRepositoryById(card.getCardId());
      if (repositoryCard==null) continue;
      List<Price> listPrices = repositoryCard.getPrices();
      LocalDateTime mostRecentDate = LocalDateTime.of(1970, Month.JANUARY, 1, 1, 1, 1);
      for (Price price: listPrices) {
        if (price.getFetchDate().isAfter(mostRecentDate)) mostRecentDate = price.getFetchDate();
      }
      repositoryCard.setCardPrices(filterPrices(Arrays.asList(card),mostRecentDate,mostRecentDate, Arrays.asList(condition.get(cardIndex)), Arrays.asList(foiling.get(cardIndex))).get(0).getPrices());
      filteredCards.add(repositoryCard);
      cardIndex++;
    }
    return filteredCards;
  }

  /**
   * Delete the content of all repositories
   */
  public void clearAllRepositories() {
    priceRepository.deleteAll();
    cardRepository.deleteAll();
  }
  
  /**
   * Clear all the prices in the repository
   */
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
   * Clear all the prices of the card with given Id
   * @param cardId - The id of the card
   */
  public void clearPricesOfCard(String cardId) {
    Card card = cardRepository.findCardByCardId(cardId);
    if (card!=null) {
      card.setCardPrices(new ArrayList<Price>());
      cardRepository.save(card);
    }
  }

  /**
   * Print all the prices in a formatted way from a list of prices
   * @param listPrices - list of Price
   */
  public void printPrices(List<Price> listPrices) {
    printSeparator();
    System.out.println("Price\tIn Stock\tIs NM\tFoil\t\tDate");
    printSeparator();
    for (Price price: listPrices) {
      int nbTabs = 3-(price.getFoiling().length())/4;
      //Prevent negative number of tabs
      if (nbTabs<0) nbTabs = 0;
      String tabs = new String(new char[nbTabs]).replace("\0", "\t");
      System.out.println(price.getAmount() +"\t"+ price.getAmountInStock() +"\t\t"+ price.getCondition() +"\t"+ price.getFoiling() +tabs+ price.getFetchDate());
    }
    printSeparator();
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
  
  public static void printSeparator(){
    System.out.println("----------------------------------------------------------------------------");
  }

  public void setRepositories(CardRepository cardRepository, PriceRepository priceRepository) {
    this.cardRepository = cardRepository;
    this.priceRepository = priceRepository;
  }
}

