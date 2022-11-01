/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import java.util.*;

// line 28 "../../../Fetcher.ump"
public class Card
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextCardId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private String name;
  private String setName;

  //Autounique Attributes
  private int cardId;

  //Card Associations
  private Fetcher fetcher;
  private List<Price> prices;
  private Reader reader;
  private FetcherSystem fetcherSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName, String aSetName, FetcherSystem aFetcherSystem)
  {
    name = aName;
    setName = aSetName;
    cardId = nextCardId++;
    prices = new ArrayList<Price>();
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create card due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setSetName(String aSetName)
  {
    boolean wasSet = false;
    setName = aSetName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getSetName()
  {
    return setName;
  }

  public int getCardId()
  {
    return cardId;
  }
  /* Code from template association_GetOne */
  public Fetcher getFetcher()
  {
    return fetcher;
  }

  public boolean hasFetcher()
  {
    boolean has = fetcher != null;
    return has;
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
  public Reader getReader()
  {
    return reader;
  }

  public boolean hasReader()
  {
    boolean has = reader != null;
    return has;
  }
  /* Code from template association_GetOne */
  public FetcherSystem getFetcherSystem()
  {
    return fetcherSystem;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setFetcher(Fetcher aFetcher)
  {
    boolean wasSet = false;
    Fetcher existingFetcher = fetcher;
    fetcher = aFetcher;
    if (existingFetcher != null && !existingFetcher.equals(aFetcher))
    {
      existingFetcher.removeCard(this);
    }
    if (aFetcher != null)
    {
      aFetcher.addCard(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Price addPrice(double aAmount, double aConcurrentPrice, boolean aIsNM, boolean aIsInStock, boolean aIsFoil, LocalDateTime aFetchDate, FetcherSystem aFetcherSystem)
  {
    return new Price(aAmount, aConcurrentPrice, aIsNM, aIsInStock, aIsFoil, aFetchDate, aFetcherSystem, this);
  }

  public boolean addPrice(Price aPrice)
  {
    boolean wasAdded = false;
    if (prices.contains(aPrice)) { return false; }
    Card existingCard = aPrice.getCard();
    boolean isNewCard = existingCard != null && !this.equals(existingCard);
    if (isNewCard)
    {
      aPrice.setCard(this);
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
    //Unable to remove aPrice, as it must always have a card
    if (!this.equals(aPrice.getCard()))
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
  /* Code from template association_SetOptionalOneToMany */
  public boolean setReader(Reader aReader)
  {
    boolean wasSet = false;
    Reader existingReader = reader;
    reader = aReader;
    if (existingReader != null && !existingReader.equals(aReader))
    {
      existingReader.removeCard(this);
    }
    if (aReader != null)
    {
      aReader.addCard(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFetcherSystem(FetcherSystem aFetcherSystem)
  {
    boolean wasSet = false;
    if (aFetcherSystem == null)
    {
      return wasSet;
    }

    FetcherSystem existingFetcherSystem = fetcherSystem;
    fetcherSystem = aFetcherSystem;
    if (existingFetcherSystem != null && !existingFetcherSystem.equals(aFetcherSystem))
    {
      existingFetcherSystem.removeCard(this);
    }
    fetcherSystem.addCard(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (fetcher != null)
    {
      Fetcher placeholderFetcher = fetcher;
      this.fetcher = null;
      placeholderFetcher.removeCard(this);
    }
    for(int i=prices.size(); i > 0; i--)
    {
      Price aPrice = prices.get(i - 1);
      aPrice.delete();
    }
    if (reader != null)
    {
      Reader placeholderReader = reader;
      this.reader = null;
      placeholderReader.removeCard(this);
    }
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removeCard(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardId" + ":" + getCardId()+ "," +
            "name" + ":" + getName()+ "," +
            "setName" + ":" + getSetName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fetcher = "+(getFetcher()!=null?Integer.toHexString(System.identityHashCode(getFetcher())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reader = "+(getReader()!=null?Integer.toHexString(System.identityHashCode(getReader())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null");
  }
}