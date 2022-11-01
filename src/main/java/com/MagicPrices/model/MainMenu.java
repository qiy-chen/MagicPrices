/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;

// line 20 "../../../Fetcher.ump"
public class MainMenu
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextMainId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MainMenu Attributes
  private String dataPath;

  //Autounique Attributes
  private int mainId;

  //MainMenu Associations
  private Reader reader;
  private FetcherSystem fetcherSystem;
  private Fetcher fetcher;
  private CardDatabase cardDatabase;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MainMenu(FetcherSystem aFetcherSystem, CardDatabase aCardDatabase)
  {
    dataPath = "./";
    mainId = nextMainId++;
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create mainMenu due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCardDatabase = setCardDatabase(aCardDatabase);
    if (!didAddCardDatabase)
    {
      throw new RuntimeException("Unable to create mainMenu due to cardDatabase. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDataPath(String aDataPath)
  {
    boolean wasSet = false;
    dataPath = aDataPath;
    wasSet = true;
    return wasSet;
  }

  public String getDataPath()
  {
    return dataPath;
  }

  public int getMainId()
  {
    return mainId;
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
  public CardDatabase getCardDatabase()
  {
    return cardDatabase;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setReader(Reader aNewReader)
  {
    boolean wasSet = false;
    if (reader != null && !reader.equals(aNewReader) && equals(reader.getMainMenu()))
    {
      //Unable to setReader, as existing reader would become an orphan
      return wasSet;
    }

    reader = aNewReader;
    MainMenu anOldMainMenu = aNewReader != null ? aNewReader.getMainMenu() : null;

    if (!this.equals(anOldMainMenu))
    {
      if (anOldMainMenu != null)
      {
        anOldMainMenu.reader = null;
      }
      if (reader != null)
      {
        reader.setMainMenu(this);
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
      //Unable to setFetcherSystem to null, as mainMenu must always be associated to a fetcherSystem
      return wasSet;
    }
    
    MainMenu existingMainMenu = aNewFetcherSystem.getMainMenu();
    if (existingMainMenu != null && !equals(existingMainMenu))
    {
      //Unable to setFetcherSystem, the current fetcherSystem already has a mainMenu, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FetcherSystem anOldFetcherSystem = fetcherSystem;
    fetcherSystem = aNewFetcherSystem;
    fetcherSystem.setMainMenu(this);

    if (anOldFetcherSystem != null)
    {
      anOldFetcherSystem.setMainMenu(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setFetcher(Fetcher aNewFetcher)
  {
    boolean wasSet = false;
    if (fetcher != null && !fetcher.equals(aNewFetcher) && equals(fetcher.getMainMenu()))
    {
      //Unable to setFetcher, as existing fetcher would become an orphan
      return wasSet;
    }

    fetcher = aNewFetcher;
    MainMenu anOldMainMenu = aNewFetcher != null ? aNewFetcher.getMainMenu() : null;

    if (!this.equals(anOldMainMenu))
    {
      if (anOldMainMenu != null)
      {
        anOldMainMenu.fetcher = null;
      }
      if (fetcher != null)
      {
        fetcher.setMainMenu(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCardDatabase(CardDatabase aNewCardDatabase)
  {
    boolean wasSet = false;
    if (aNewCardDatabase == null)
    {
      //Unable to setCardDatabase to null, as mainMenu must always be associated to a cardDatabase
      return wasSet;
    }
    
    MainMenu existingMainMenu = aNewCardDatabase.getMainMenu();
    if (existingMainMenu != null && !equals(existingMainMenu))
    {
      //Unable to setCardDatabase, the current cardDatabase already has a mainMenu, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    CardDatabase anOldCardDatabase = cardDatabase;
    cardDatabase = aNewCardDatabase;
    cardDatabase.setMainMenu(this);

    if (anOldCardDatabase != null)
    {
      anOldCardDatabase.setMainMenu(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Reader existingReader = reader;
    reader = null;
    if (existingReader != null)
    {
      existingReader.delete();
    }
    FetcherSystem existingFetcherSystem = fetcherSystem;
    fetcherSystem = null;
    if (existingFetcherSystem != null)
    {
      existingFetcherSystem.setMainMenu(null);
    }
    Fetcher existingFetcher = fetcher;
    fetcher = null;
    if (existingFetcher != null)
    {
      existingFetcher.delete();
    }
    CardDatabase existingCardDatabase = cardDatabase;
    cardDatabase = null;
    if (existingCardDatabase != null)
    {
      existingCardDatabase.setMainMenu(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "mainId" + ":" + getMainId()+ "," +
            "dataPath" + ":" + getDataPath()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "reader = "+(getReader()!=null?Integer.toHexString(System.identityHashCode(getReader())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcher = "+(getFetcher()!=null?Integer.toHexString(System.identityHashCode(getFetcher())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardDatabase = "+(getCardDatabase()!=null?Integer.toHexString(System.identityHashCode(getCardDatabase())):"null");
  }
}