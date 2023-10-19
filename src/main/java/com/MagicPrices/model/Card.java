/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

// line 29 "../../../Fetcher.ump"
@Entity
public class Card implements java.io.Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  @Id
  private String cardId;
  private String name;
  private String category;
  private transient Comparator<Price> pricesPriority;

  //Card Associations
  @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
  private List<Price> prices;
  @Transient
  private CardDatabase cardDatabase;
  @Transient
  private Fetcher fetcher;
  @Transient
  private FetcherSystem fetcherSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aCardId, String aName, String aCategory, CardDatabase aCardDatabase, FetcherSystem aFetcherSystem)
  {
    cardId = aCardId;
    name = aName;
    category = aCategory;
    pricesPriority = 
      Comparator.comparing(Price::getFetchDateString);
    prices = new ArrayList<Price>();
    boolean didAddCardDatabase = setCardDatabase(aCardDatabase);
    if (!didAddCardDatabase)
    {
      throw new RuntimeException("Unable to create card due to cardDatabase. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create card due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  
  public Card() {
    pricesPriority = 
        Comparator.comparing(Price::getFetchDateString);
      prices = new ArrayList<Price>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCardId(String aCardId)
  {
    boolean wasSet = false;
    cardId = aCardId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCategory(String aCategory)
  {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setPricesPriority(Comparator<Price> aPricesPriority)
  {
    boolean wasSet = false;
    pricesPriority = aPricesPriority;
    wasSet = true;
    return wasSet;
  }

  public String getCardId()
  {
    return cardId;
  }

  public String getName()
  {
    return name;
  }

  public String getCategory()
  {
    return category;
  }

  public Comparator<Price> getPricesPriority()
  {
    return pricesPriority;
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
  /* Code from template association_GetOne */
  public FetcherSystem getFetcherSystem()
  {
    return fetcherSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPrices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Price addPrice(double aAmount, double aConcurrentPrice, String aCondition, int aAmountInStock, String aFoiling, LocalDateTime aFetchDate, FetcherSystem aFetcherSystem)
  {
    return new Price(aAmount, aConcurrentPrice, aCondition, aAmountInStock, aFoiling, aFetchDate, aFetcherSystem, this);
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
    if(wasAdded)
        Collections.sort(prices, pricesPriority);
    
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

  /* Code from template association_SetOneToMany */
  public boolean setCardDatabase(CardDatabase aCardDatabase)
  {
    boolean wasSet = false;
    if (aCardDatabase == null)
    {
      return wasSet;
    }

    CardDatabase existingCardDatabase = cardDatabase;
    cardDatabase = aCardDatabase;
    if (existingCardDatabase != null && !existingCardDatabase.equals(aCardDatabase))
    {
      existingCardDatabase.removeCard(this);
    }
    cardDatabase.addCard(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setFetcher(Fetcher aNewFetcher)
  {
    boolean wasSet = false;
    if (aNewFetcher == null)
    {
      Fetcher existingFetcher = fetcher;
      fetcher = null;
      
      if (existingFetcher != null && existingFetcher.getCard() != null)
      {
        existingFetcher.setCard(null);
      }
      wasSet = true;
      return wasSet;
    }

    Fetcher currentFetcher = getFetcher();
    if (currentFetcher != null && !currentFetcher.equals(aNewFetcher))
    {
      currentFetcher.setCard(null);
    }

    fetcher = aNewFetcher;
    Card existingCard = aNewFetcher.getCard();

    if (!equals(existingCard))
    {
      aNewFetcher.setCard(this);
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

  /* Code from template association_sorted_serializable_readObject */ 
  private void readObject(java.io.ObjectInputStream in)
  throws Exception
  {
    in.defaultReadObject();

    pricesPriority = 
      Comparator.comparing(Price::getFetchDateString);
  }
  
  public void delete()
  {
    for(int i=prices.size(); i > 0; i--)
    {
      Price aPrice = prices.get(i - 1);
      aPrice.delete();
    }
    CardDatabase placeholderCardDatabase = cardDatabase;
    this.cardDatabase = null;
    if(placeholderCardDatabase != null)
    {
      placeholderCardDatabase.removeCard(this);
    }
    if (fetcher != null)
    {
      fetcher.setCard(null);
    }
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removeCard(this);
    }
  }

  // line 42 "../../../Fetcher.ump"
   public boolean setCardId(WebElement card){
    boolean wasSet = false;
    cardId = Card.convertToCardId(card);
    wasSet = true;
    return wasSet;
  }

  // line 49 "../../../Fetcher.ump"
   public static  String convertToCardId(WebElement card){
    return card.findElement(By.tagName("a")).getDomAttribute("href").replaceAll("https://www.facetofacegames.com/", "").replaceAll("/","");
  }
   
   public boolean setCardPrices(List<Price> prices) {
     boolean wasSet = false;
     this.prices = prices;
     wasSet = true;
     return wasSet;
   }


  public String toString()
  {
    return super.toString() + "["+
            "cardId" + ":" + getCardId()+ "," +
            "name" + ":" + getName()+ "," +
            "category" + ":" + getCategory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pricesPriority" + "=" + (getPricesPriority() != null ? !getPricesPriority().equals(this)  ? getPricesPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardDatabase = "+(getCardDatabase()!=null?Integer.toHexString(System.identityHashCode(getCardDatabase())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcher = "+(getFetcher()!=null?Integer.toHexString(System.identityHashCode(getFetcher())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null");
  }
}