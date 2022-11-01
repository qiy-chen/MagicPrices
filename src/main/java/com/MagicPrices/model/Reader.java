/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.util.*;

// line 102 "../../../Fetcher.ump"
public class Reader
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextReaderId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int readerId;

  //Reader Associations
  private FetcherSystem fetcherSystem;
  private MainMenu mainMenu;
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reader(FetcherSystem aFetcherSystem, MainMenu aMainMenu)
  {
    readerId = nextReaderId++;
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create reader due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMainMenu = setMainMenu(aMainMenu);
    if (!didAddMainMenu)
    {
      throw new RuntimeException("Unable to create reader due to mainMenu. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getReaderId()
  {
    return readerId;
  }
  /* Code from template association_GetOne */
  public FetcherSystem getFetcherSystem()
  {
    return fetcherSystem;
  }
  /* Code from template association_GetOne */
  public MainMenu getMainMenu()
  {
    return mainMenu;
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
      existingFetcherSystem.removeReader(this);
    }
    fetcherSystem.addReader(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setMainMenu(MainMenu aNewMainMenu)
  {
    boolean wasSet = false;
    if (aNewMainMenu == null)
    {
      //Unable to setMainMenu to null, as reader must always be associated to a mainMenu
      return wasSet;
    }
    
    Reader existingReader = aNewMainMenu.getReader();
    if (existingReader != null && !equals(existingReader))
    {
      //Unable to setMainMenu, the current mainMenu already has a reader, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    MainMenu anOldMainMenu = mainMenu;
    mainMenu = aNewMainMenu;
    mainMenu.setReader(this);

    if (anOldMainMenu != null)
    {
      anOldMainMenu.setReader(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    Reader existingReader = aCard.getReader();
    if (existingReader == null)
    {
      aCard.setReader(this);
    }
    else if (!this.equals(existingReader))
    {
      existingReader.removeCard(aCard);
      addCard(aCard);
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
    if (cards.contains(aCard))
    {
      cards.remove(aCard);
      aCard.setReader(null);
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

  public void delete()
  {
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removeReader(this);
    }
    MainMenu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.setReader(null);
    }
    while( !cards.isEmpty() )
    {
      cards.get(0).setReader(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "readerId" + ":" + getReaderId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainMenu = "+(getMainMenu()!=null?Integer.toHexString(System.identityHashCode(getMainMenu())):"null");
  }
}