/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.util.*;
import java.io.Serializable;

// line 279 "../../../Fetcher.ump"
public class CardDatabase implements java.io.Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextDatabaseId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CardDatabase Attributes
  private transient Comparator<Card> cardsPriority;

  //Autounique Attributes
  private int databaseId;

  //CardDatabase Associations
  private Reader reader;
  private Fetcher fetcher;
  private MainMenu mainMenu;
  private FetcherSystem fetcherSystem;
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CardDatabase(FetcherSystem aFetcherSystem)
  {
    cardsPriority = 
      Comparator.comparing(Card::getCardId);
    databaseId = nextDatabaseId++;
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create cardDatabase due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCardsPriority(Comparator<Card> aCardsPriority)
  {
    boolean wasSet = false;
    cardsPriority = aCardsPriority;
    wasSet = true;
    return wasSet;
  }

  public Comparator<Card> getCardsPriority()
  {
    return cardsPriority;
  }

  public int getDatabaseId()
  {
    return databaseId;
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
  public MainMenu getMainMenu()
  {
    return mainMenu;
  }

  public boolean hasMainMenu()
  {
    boolean has = mainMenu != null;
    return has;
  }
  /* Code from template association_GetOne */
  public FetcherSystem getFetcherSystem()
  {
    return fetcherSystem;
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setReader(Reader aNewReader)
  {
    boolean wasSet = false;
    if (reader != null && !reader.equals(aNewReader) && equals(reader.getCardDatabase()))
    {
      //Unable to setReader, as existing reader would become an orphan
      return wasSet;
    }

    reader = aNewReader;
    CardDatabase anOldCardDatabase = aNewReader != null ? aNewReader.getCardDatabase() : null;

    if (!this.equals(anOldCardDatabase))
    {
      if (anOldCardDatabase != null)
      {
        anOldCardDatabase.reader = null;
      }
      if (reader != null)
      {
        reader.setCardDatabase(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setFetcher(Fetcher aNewFetcher)
  {
    boolean wasSet = false;
    if (fetcher != null && !fetcher.equals(aNewFetcher) && equals(fetcher.getCardDatabase()))
    {
      //Unable to setFetcher, as existing fetcher would become an orphan
      return wasSet;
    }

    fetcher = aNewFetcher;
    CardDatabase anOldCardDatabase = aNewFetcher != null ? aNewFetcher.getCardDatabase() : null;

    if (!this.equals(anOldCardDatabase))
    {
      if (anOldCardDatabase != null)
      {
        anOldCardDatabase.fetcher = null;
      }
      if (fetcher != null)
      {
        fetcher.setCardDatabase(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setMainMenu(MainMenu aNewMainMenu)
  {
    boolean wasSet = false;
    if (mainMenu != null && !mainMenu.equals(aNewMainMenu) && equals(mainMenu.getCardDatabase()))
    {
      //Unable to setMainMenu, as existing mainMenu would become an orphan
      return wasSet;
    }

    mainMenu = aNewMainMenu;
    CardDatabase anOldCardDatabase = aNewMainMenu != null ? aNewMainMenu.getCardDatabase() : null;

    if (!this.equals(anOldCardDatabase))
    {
      if (anOldCardDatabase != null)
      {
        anOldCardDatabase.mainMenu = null;
      }
      if (mainMenu != null)
      {
        mainMenu.setCardDatabase(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFetcherSystem(FetcherSystem aNewFetcherSystem)
  {
    boolean wasSet = false;
    if (aNewFetcherSystem == null)
    {
      //Unable to setFetcherSystem to null, as cardDatabase must always be associated to a fetcherSystem
      return wasSet;
    }
    
    CardDatabase existingCardDatabase = aNewFetcherSystem.getCardDatabase();
    if (existingCardDatabase != null && !equals(existingCardDatabase))
    {
      //Unable to setFetcherSystem, the current fetcherSystem already has a cardDatabase, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FetcherSystem anOldFetcherSystem = fetcherSystem;
    fetcherSystem = aNewFetcherSystem;
    fetcherSystem.setCardDatabase(this);

    if (anOldFetcherSystem != null)
    {
      anOldFetcherSystem.setCardDatabase(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Card addCard(String aCardId, String aName, String aCategory, FetcherSystem aFetcherSystem)
  {
    return new Card(aCardId, aName, aCategory, this, aFetcherSystem);
  }

  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    CardDatabase existingCardDatabase = aCard.getCardDatabase();
    boolean isNewCardDatabase = existingCardDatabase != null && !this.equals(existingCardDatabase);
    if (isNewCardDatabase)
    {
      aCard.setCardDatabase(this);
    }
    else
    {
      cards.add(aCard);
    }
    wasAdded = true;
    if(wasAdded)
        Collections.sort(cards, cardsPriority);
    
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    //Unable to remove aCard, as it must always have a cardDatabase
    if (!this.equals(aCard.getCardDatabase()))
    {
      cards.remove(aCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }


  /* Code from template association_sorted_serializable_readObject */ 
  private void readObject(java.io.ObjectInputStream in)
  throws Exception
  {
    in.defaultReadObject();

    cardsPriority = 
      Comparator.comparing(Card::getCardId);
  }
  
  public void delete()
  {
    Reader existingReader = reader;
    reader = null;
    if (existingReader != null)
    {
      existingReader.delete();
    }
    Fetcher existingFetcher = fetcher;
    fetcher = null;
    if (existingFetcher != null)
    {
      existingFetcher.delete();
    }
    MainMenu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.delete();
    }
    FetcherSystem existingFetcherSystem = fetcherSystem;
    fetcherSystem = null;
    if (existingFetcherSystem != null)
    {
      existingFetcherSystem.setCardDatabase(null);
    }
    for(int i=cards.size(); i > 0; i--)
    {
      Card aCard = cards.get(i - 1);
      aCard.delete();
    }
  }

  // line 287 "../../../Fetcher.ump"
   public void setCards(List<Card> list){
    this.cards = list;
  }


  public String toString()
  {
    return super.toString() + "["+
            "databaseId" + ":" + getDatabaseId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cardsPriority" + "=" + (getCardsPriority() != null ? !getCardsPriority().equals(this)  ? getCardsPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "reader = "+(getReader()!=null?Integer.toHexString(System.identityHashCode(getReader())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcher = "+(getFetcher()!=null?Integer.toHexString(System.identityHashCode(getFetcher())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainMenu = "+(getMainMenu()!=null?Integer.toHexString(System.identityHashCode(getMainMenu())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null");
  }
}