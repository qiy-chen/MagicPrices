/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import java.util.*;

// line 2 "../../../Fetcher.ump"
public class FetcherSystem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FetcherSystem Attributes
  private LocalDateTime currentDate;

  //Autounique Attributes
  private int id;

  //FetcherSystem Associations
  private MainMenu mainMenu;
  private List<Fetcher> fetchers;
  private List<Card> cards;
  private List<Price> prices;
  private CardDatabase cardDatabase;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FetcherSystem()
  {
    currentDate = LocalDateTime.now();
    id = nextId++;
    fetchers = new ArrayList<Fetcher>();
    cards = new ArrayList<Card>();
    prices = new ArrayList<Price>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrentDate(LocalDateTime aCurrentDate)
  {
    boolean wasSet = false;
    currentDate = aCurrentDate;
    wasSet = true;
    return wasSet;
  }

  public LocalDateTime getCurrentDate()
  {
    return currentDate;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public MainMenu getMainMenu()
  {
    return mainMenu;
  }

  public boolean hasMainMenu()
  {
    boolean has = mainMenu != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Fetcher getFetcher(int index)
  {
    Fetcher aFetcher = fetchers.get(index);
    return aFetcher;
  }

  public List<Fetcher> getFetchers()
  {
    List<Fetcher> newFetchers = Collections.unmodifiableList(fetchers);
    return newFetchers;
  }

  public int numberOfFetchers()
  {
    int number = fetchers.size();
    return number;
  }

  public boolean hasFetchers()
  {
    boolean has = fetchers.size() > 0;
    return has;
  }

  public int indexOfFetcher(Fetcher aFetcher)
  {
    int index = fetchers.indexOf(aFetcher);
    return index;
  }
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_GetMany */
  public Price getPrice(int index)
  {
    Price aPrice = prices.get(index);
    return aPrice;
  }

  public List<Price> getPrices()
  {
    List<Price> newPrices = Collections.unmodifiableList(prices);
    return newPrices;
  }

  public int numberOfPrices()
  {
    int number = prices.size();
    return number;
  }

  public boolean hasPrices()
  {
    boolean has = prices.size() > 0;
    return has;
  }

  public int indexOfPrice(Price aPrice)
  {
    int index = prices.indexOf(aPrice);
    return index;
  }
  /* Code from template association_GetOne */
  public CardDatabase getCardDatabase()
  {
    return cardDatabase;
  }

  public boolean hasCardDatabase()
  {
    boolean has = cardDatabase != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setMainMenu(MainMenu aNewMainMenu)
  {
    boolean wasSet = false;
    if (mainMenu != null && !mainMenu.equals(aNewMainMenu) && equals(mainMenu.getFetcherSystem()))
    {
      //Unable to setMainMenu, as existing mainMenu would become an orphan
      return wasSet;
    }

    mainMenu = aNewMainMenu;
    FetcherSystem anOldFetcherSystem = aNewMainMenu != null ? aNewMainMenu.getFetcherSystem() : null;

    if (!this.equals(anOldFetcherSystem))
    {
      if (anOldFetcherSystem != null)
      {
        anOldFetcherSystem.mainMenu = null;
      }
      if (mainMenu != null)
      {
        mainMenu.setFetcherSystem(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFetchers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Fetcher addFetcher(LocalDateTime aFetchDate, String aUrl, double aConversionRateUSDToCAD, MainMenu aMainMenu, CardDatabase aCardDatabase)
  {
    return new Fetcher(aFetchDate, aUrl, aConversionRateUSDToCAD, aMainMenu, this, aCardDatabase);
  }

  public boolean addFetcher(Fetcher aFetcher)
  {
    boolean wasAdded = false;
    if (fetchers.contains(aFetcher)) { return false; }
    FetcherSystem existingFetcherSystem = aFetcher.getFetcherSystem();
    boolean isNewFetcherSystem = existingFetcherSystem != null && !this.equals(existingFetcherSystem);
    if (isNewFetcherSystem)
    {
      aFetcher.setFetcherSystem(this);
    }
    else
    {
      fetchers.add(aFetcher);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeFetcher(Fetcher aFetcher)
  {
    boolean wasRemoved = false;
    //Unable to remove aFetcher, as it must always have a fetcherSystem
    if (!this.equals(aFetcher.getFetcherSystem()))
    {
      fetchers.remove(aFetcher);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFetcherAt(Fetcher aFetcher, int index)
  {  
    boolean wasAdded = false;
    if(addFetcher(aFetcher))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFetchers()) { index = numberOfFetchers() - 1; }
      fetchers.remove(aFetcher);
      fetchers.add(index, aFetcher);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFetcherAt(Fetcher aFetcher, int index)
  {
    boolean wasAdded = false;
    if(fetchers.contains(aFetcher))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFetchers()) { index = numberOfFetchers() - 1; }
      fetchers.remove(aFetcher);
      fetchers.add(index, aFetcher);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFetcherAt(aFetcher, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Card addCard(String aCardId, String aName, String aCategory, CardDatabase aCardDatabase)
  {
    return new Card(aCardId, aName, aCategory, aCardDatabase, this);
  }

  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    FetcherSystem existingFetcherSystem = aCard.getFetcherSystem();
    boolean isNewFetcherSystem = existingFetcherSystem != null && !this.equals(existingFetcherSystem);
    if (isNewFetcherSystem)
    {
      aCard.setFetcherSystem(this);
    }
    else
    {
      cards.add(aCard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    //Unable to remove aCard, as it must always have a fetcherSystem
    if (!this.equals(aCard.getFetcherSystem()))
    {
      cards.remove(aCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Price addPrice(double aAmount, double aConcurrentPrice, String aCondition, int aAmountInStock, String aFoiling, LocalDateTime aFetchDate, Card aCard)
  {
    return new Price(aAmount, aConcurrentPrice, aCondition, aAmountInStock, aFoiling, aFetchDate, this, aCard);
  }

  public boolean addPrice(Price aPrice)
  {
    boolean wasAdded = false;
    if (prices.contains(aPrice)) { return false; }
    FetcherSystem existingFetcherSystem = aPrice.getFetcherSystem();
    boolean isNewFetcherSystem = existingFetcherSystem != null && !this.equals(existingFetcherSystem);
    if (isNewFetcherSystem)
    {
      aPrice.setFetcherSystem(this);
    }
    else
    {
      prices.add(aPrice);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePrice(Price aPrice)
  {
    boolean wasRemoved = false;
    //Unable to remove aPrice, as it must always have a fetcherSystem
    if (!this.equals(aPrice.getFetcherSystem()))
    {
      prices.remove(aPrice);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPriceAt(Price aPrice, int index)
  {  
    boolean wasAdded = false;
    if(addPrice(aPrice))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrices()) { index = numberOfPrices() - 1; }
      prices.remove(aPrice);
      prices.add(index, aPrice);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePriceAt(Price aPrice, int index)
  {
    boolean wasAdded = false;
    if(prices.contains(aPrice))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPrices()) { index = numberOfPrices() - 1; }
      prices.remove(aPrice);
      prices.add(index, aPrice);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPriceAt(aPrice, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setCardDatabase(CardDatabase aNewCardDatabase)
  {
    boolean wasSet = false;
    if (cardDatabase != null && !cardDatabase.equals(aNewCardDatabase) && equals(cardDatabase.getFetcherSystem()))
    {
      //Unable to setCardDatabase, as existing cardDatabase would become an orphan
      return wasSet;
    }

    cardDatabase = aNewCardDatabase;
    FetcherSystem anOldFetcherSystem = aNewCardDatabase != null ? aNewCardDatabase.getFetcherSystem() : null;

    if (!this.equals(anOldFetcherSystem))
    {
      if (anOldFetcherSystem != null)
      {
        anOldFetcherSystem.cardDatabase = null;
      }
      if (cardDatabase != null)
      {
        cardDatabase.setFetcherSystem(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MainMenu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.delete();
      existingMainMenu.setFetcherSystem(null);
    }
    while (fetchers.size() > 0)
    {
      Fetcher aFetcher = fetchers.get(fetchers.size() - 1);
      aFetcher.delete();
      fetchers.remove(aFetcher);
    }
    
    while (cards.size() > 0)
    {
      Card aCard = cards.get(cards.size() - 1);
      aCard.delete();
      cards.remove(aCard);
    }
    
    while (prices.size() > 0)
    {
      Price aPrice = prices.get(prices.size() - 1);
      aPrice.delete();
      prices.remove(aPrice);
    }
    
    CardDatabase existingCardDatabase = cardDatabase;
    cardDatabase = null;
    if (existingCardDatabase != null)
    {
      existingCardDatabase.delete();
      existingCardDatabase.setFetcherSystem(null);
    }
  }

  // line 14 "../../../Fetcher.ump"
   public LocalDateTime updateCurrentDate(){
    currentDate=LocalDateTime.now();
    return currentDate;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentDate" + "=" + (getCurrentDate() != null ? !getCurrentDate().equals(this)  ? getCurrentDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainMenu = "+(getMainMenu()!=null?Integer.toHexString(System.identityHashCode(getMainMenu())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardDatabase = "+(getCardDatabase()!=null?Integer.toHexString(System.identityHashCode(getCardDatabase())):"null");
  }
}