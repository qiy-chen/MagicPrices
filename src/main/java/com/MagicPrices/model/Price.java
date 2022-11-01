/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;

// line 107 "../../../Fetcher.ump"
public class Price
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextPriceId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Price Attributes
  private double amount;
  private double concurrentPrice;
  private boolean isNM;
  private boolean isInStock;
  private boolean isFoil;
  private LocalDateTime fetchDate;

  //Autounique Attributes
  private int priceId;

  //Price Associations
  private FetcherSystem fetcherSystem;
  private Card card;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Price(double aAmount, double aConcurrentPrice, boolean aIsNM, boolean aIsInStock, boolean aIsFoil, LocalDateTime aFetchDate, FetcherSystem aFetcherSystem, Card aCard)
  {
    amount = aAmount;
    concurrentPrice = aConcurrentPrice;
    isNM = aIsNM;
    isInStock = aIsInStock;
    isFoil = aIsFoil;
    fetchDate = aFetchDate;
    priceId = nextPriceId++;
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create price due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCard = setCard(aCard);
    if (!didAddCard)
    {
      throw new RuntimeException("Unable to create price due to card. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAmount(double aAmount)
  {
    boolean wasSet = false;
    amount = aAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setConcurrentPrice(double aConcurrentPrice)
  {
    boolean wasSet = false;
    concurrentPrice = aConcurrentPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsNM(boolean aIsNM)
  {
    boolean wasSet = false;
    isNM = aIsNM;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsInStock(boolean aIsInStock)
  {
    boolean wasSet = false;
    isInStock = aIsInStock;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsFoil(boolean aIsFoil)
  {
    boolean wasSet = false;
    isFoil = aIsFoil;
    wasSet = true;
    return wasSet;
  }

  public boolean setFetchDate(LocalDateTime aFetchDate)
  {
    boolean wasSet = false;
    fetchDate = aFetchDate;
    wasSet = true;
    return wasSet;
  }

  public double getAmount()
  {
    return amount;
  }

  public double getConcurrentPrice()
  {
    return concurrentPrice;
  }

  public boolean getIsNM()
  {
    return isNM;
  }

  public boolean getIsInStock()
  {
    return isInStock;
  }

  public boolean getIsFoil()
  {
    return isFoil;
  }

  public LocalDateTime getFetchDate()
  {
    return fetchDate;
  }

  public int getPriceId()
  {
    return priceId;
  }
  /* Code from template association_GetOne */
  public FetcherSystem getFetcherSystem()
  {
    return fetcherSystem;
  }
  /* Code from template association_GetOne */
  public Card getCard()
  {
    return card;
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
      existingFetcherSystem.removePrice(this);
    }
    fetcherSystem.addPrice(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCard(Card aCard)
  {
    boolean wasSet = false;
    if (aCard == null)
    {
      return wasSet;
    }

    Card existingCard = card;
    card = aCard;
    if (existingCard != null && !existingCard.equals(aCard))
    {
      existingCard.removePrice(this);
    }
    card.addPrice(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removePrice(this);
    }
    Card placeholderCard = card;
    this.card = null;
    if(placeholderCard != null)
    {
      placeholderCard.removePrice(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "priceId" + ":" + getPriceId()+ "," +
            "amount" + ":" + getAmount()+ "," +
            "concurrentPrice" + ":" + getConcurrentPrice()+ "," +
            "isNM" + ":" + getIsNM()+ "," +
            "isInStock" + ":" + getIsInStock()+ "," +
            "isFoil" + ":" + getIsFoil()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fetchDate" + "=" + (getFetchDate() != null ? !getFetchDate().equals(this)  ? getFetchDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "card = "+(getCard()!=null?Integer.toHexString(System.identityHashCode(getCard())):"null");
  }
}