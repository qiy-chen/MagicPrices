/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

// line 197 "../../../Fetcher.ump"
@Entity
public class Price implements java.io.Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Price Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int priceId;
  private double amount;
  private double concurrentPrice;
  private String condition;
  private int amountInStock;
  private String foiling;
  private LocalDateTime fetchDate;
  private String fetchDateString;

  //Price Associations
  @Transient
  private FetcherSystem fetcherSystem;
  @ManyToOne
  private Card card;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Price(double aAmount, double aConcurrentPrice, String aCondition, int aAmountInStock, String aFoiling, LocalDateTime aFetchDate, FetcherSystem aFetcherSystem, Card aCard)
  {
    amount = aAmount;
    concurrentPrice = aConcurrentPrice;
    condition = aCondition;
    amountInStock = aAmountInStock;
    foiling = aFoiling;
    fetchDate = aFetchDate;
    fetchDateString = fetchDate.toString();
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
  
  public Price() {
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPriceId(int aPriceId)
  {
    boolean wasSet = false;
    priceId = aPriceId;
    wasSet = true;
    return wasSet;
  }

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

  public boolean setCondition(String aCondition)
  {
    boolean wasSet = false;
    condition = aCondition;
    wasSet = true;
    return wasSet;
  }

  public boolean setAmountInStock(int aAmountInStock)
  {
    boolean wasSet = false;
    amountInStock = aAmountInStock;
    wasSet = true;
    return wasSet;
  }

  public boolean setFoiling(String aFoiling)
  {
    boolean wasSet = false;
    foiling = aFoiling;
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

  public boolean setFetchDateString(String aFetchDateString)
  {
    boolean wasSet = false;
    fetchDateString = aFetchDateString;
    wasSet = true;
    return wasSet;
  }

  public int getPriceId()
  {
    return priceId;
  }

  public double getAmount()
  {
    return amount;
  }

  public double getConcurrentPrice()
  {
    return concurrentPrice;
  }

  public String getCondition()
  {
    return condition;
  }

  public int getAmountInStock()
  {
    return amountInStock;
  }

  public String getFoiling()
  {
    return foiling;
  }

  public LocalDateTime getFetchDate()
  {
    return fetchDate;
  }

  public String getFetchDateString()
  {
    return fetchDateString;
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
            "condition" + ":" + getCondition()+ "," +
            "amountInStock" + ":" + getAmountInStock()+ "," +
            "foiling" + ":" + getFoiling()+ "," +
            "fetchDateString" + ":" + getFetchDateString()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fetchDate" + "=" + (getFetchDate() != null ? !getFetchDate().equals(this)  ? getFetchDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "card = "+(getCard()!=null?Integer.toHexString(System.identityHashCode(getCard())):"null");
  }
}