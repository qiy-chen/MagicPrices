/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.*;

// line 39 "../../../Fetcher.ump"
public class Fetcher
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextFetcherId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Fetcher Attributes
  private LocalDateTime fetchDate;
  private String url;
  private double conversionRateUSDToCAD;

  //Autounique Attributes
  private int fetcherId;

  //Fetcher Associations
  private MainMenu mainMenu;
  private FetcherSystem fetcherSystem;
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Fetcher(LocalDateTime aFetchDate, String aUrl, double aConversionRateUSDToCAD, MainMenu aMainMenu, FetcherSystem aFetcherSystem)
  {
    fetchDate = aFetchDate;
    url = aUrl;
    conversionRateUSDToCAD = aConversionRateUSDToCAD;
    fetcherId = nextFetcherId++;
    boolean didAddMainMenu = setMainMenu(aMainMenu);
    if (!didAddMainMenu)
    {
      throw new RuntimeException("Unable to create fetcher due to mainMenu. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddFetcherSystem = setFetcherSystem(aFetcherSystem);
    if (!didAddFetcherSystem)
    {
      throw new RuntimeException("Unable to create fetcher due to fetcherSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFetchDate(LocalDateTime aFetchDate)
  {
    boolean wasSet = false;
    fetchDate = aFetchDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setConversionRateUSDToCAD(double aConversionRateUSDToCAD)
  {
    boolean wasSet = false;
    conversionRateUSDToCAD = aConversionRateUSDToCAD;
    wasSet = true;
    return wasSet;
  }

  public LocalDateTime getFetchDate()
  {
    return fetchDate;
  }

  public String getUrl()
  {
    return url;
  }

  public double getConversionRateUSDToCAD()
  {
    return conversionRateUSDToCAD;
  }

  public int getFetcherId()
  {
    return fetcherId;
  }
  /* Code from template association_GetOne */
  public MainMenu getMainMenu()
  {
    return mainMenu;
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setMainMenu(MainMenu aNewMainMenu)
  {
    boolean wasSet = false;
    if (aNewMainMenu == null)
    {
      //Unable to setMainMenu to null, as fetcher must always be associated to a mainMenu
      return wasSet;
    }
    
    Fetcher existingFetcher = aNewMainMenu.getFetcher();
    if (existingFetcher != null && !equals(existingFetcher))
    {
      //Unable to setMainMenu, the current mainMenu already has a fetcher, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    MainMenu anOldMainMenu = mainMenu;
    mainMenu = aNewMainMenu;
    mainMenu.setFetcher(this);

    if (anOldMainMenu != null)
    {
      anOldMainMenu.setFetcher(null);
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
      existingFetcherSystem.removeFetcher(this);
    }
    fetcherSystem.addFetcher(this);
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
    Fetcher existingFetcher = aCard.getFetcher();
    if (existingFetcher == null)
    {
      aCard.setFetcher(this);
    }
    else if (!this.equals(existingFetcher))
    {
      existingFetcher.removeCard(aCard);
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
      aCard.setFetcher(null);
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
    MainMenu existingMainMenu = mainMenu;
    mainMenu = null;
    if (existingMainMenu != null)
    {
      existingMainMenu.setFetcher(null);
    }
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removeFetcher(this);
    }
    while( !cards.isEmpty() )
    {
      cards.get(0).setFetcher(null);
    }
  }

  // line 54 "../../../Fetcher.ump"
   public void init(boolean isInCAD){
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //Shutdown some error messages
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setPrintContentOnFailingStatusCode(false);

    try {
       HtmlPage page = webClient.getPage(url);

       webClient.getCurrentWindow().getJobManager().removeAllJobs();
       webClient.close();
       //Extract information
       List<DomElement> ListOfCards = page.getByXPath("//div[@class='hawk-results__item-name']");
       for (DomElement card: ListOfCards) {
         String cardName;
         String cardSet;
         boolean isNM;
         boolean isInStock;
         boolean isFoil;
         double concurrentPrice=0;
         double cardPrice=0;
         DomElement cardNameHTML = card.getFirstByXPath("//h4[@class='hawk-results__hawk-contentTitle']");
         cardName = cardNameHTML.asNormalizedText();
         DomElement cardSetHTML = card.getFirstByXPath("//p[@class='hawk-results__hawk-contentSubtitle']");
         cardSet = cardSetHTML.asNormalizedText();
         
         //find the card currently displayed on the website
         DomElement cardDisplayed;
         List<DomElement> ListOfCandidateCard = card.getByXPath("//span[@class='retailPrice hawkPrice']");
         for (DomElement candidateCard: ListOfCandidateCard) {
           if (candidateCard.isDisplayed()) cardPrice = Double.parseDouble(candidateCard.asNormalizedText());
         }
         
         DomElement cardConditionHTML = card.getFirstByXPath("//h4[@class='hawk-results__hawk-contentSubtitle']");
         //isNM = cardConditionHTML.asNormalizedText();
         DomElement cardStockHTML = card.getFirstByXPath("//h4[@class='hawk-results__hawk-contentSubtitle']");
         //isInStock = cardStockHTML.asNormalizedText();
       }
       

    } catch (IOException e) {
       System.out.println("An error occurred: " + e);
       webClient.close();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "fetcherId" + ":" + getFetcherId()+ "," +
            "url" + ":" + getUrl()+ "," +
            "conversionRateUSDToCAD" + ":" + getConversionRateUSDToCAD()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fetchDate" + "=" + (getFetchDate() != null ? !getFetchDate().equals(this)  ? getFetchDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "mainMenu = "+(getMainMenu()!=null?Integer.toHexString(System.identityHashCode(getMainMenu())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null");
  }
}