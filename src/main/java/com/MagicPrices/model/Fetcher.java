/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import java.io.IOException;
import java.util.List;

// line 51 "../../../Fetcher.ump"
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
  private Card card;
  private CardDatabase cardDatabase;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Fetcher(LocalDateTime aFetchDate, String aUrl, double aConversionRateUSDToCAD, MainMenu aMainMenu, FetcherSystem aFetcherSystem, CardDatabase aCardDatabase)
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
    boolean didAddCardDatabase = setCardDatabase(aCardDatabase);
    if (!didAddCardDatabase)
    {
      throw new RuntimeException("Unable to create fetcher due to cardDatabase. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public Card getCard()
  {
    return card;
  }

  public boolean hasCard()
  {
    boolean has = card != null;
    return has;
  }
  /* Code from template association_GetOne */
  public CardDatabase getCardDatabase()
  {
    return cardDatabase;
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setCard(Card aNewCard)
  {
    boolean wasSet = false;
    if (aNewCard == null)
    {
      Card existingCard = card;
      card = null;
      
      if (existingCard != null && existingCard.getFetcher() != null)
      {
        existingCard.setFetcher(null);
      }
      wasSet = true;
      return wasSet;
    }

    Card currentCard = getCard();
    if (currentCard != null && !currentCard.equals(aNewCard))
    {
      currentCard.setFetcher(null);
    }

    card = aNewCard;
    Fetcher existingFetcher = aNewCard.getFetcher();

    if (!equals(existingFetcher))
    {
      aNewCard.setFetcher(this);
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
      //Unable to setCardDatabase to null, as fetcher must always be associated to a cardDatabase
      return wasSet;
    }
    
    Fetcher existingFetcher = aNewCardDatabase.getFetcher();
    if (existingFetcher != null && !equals(existingFetcher))
    {
      //Unable to setCardDatabase, the current cardDatabase already has a fetcher, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    CardDatabase anOldCardDatabase = cardDatabase;
    cardDatabase = aNewCardDatabase;
    cardDatabase.setFetcher(this);

    if (anOldCardDatabase != null)
    {
      anOldCardDatabase.setFetcher(null);
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
      existingMainMenu.setFetcher(null);
    }
    FetcherSystem placeholderFetcherSystem = fetcherSystem;
    this.fetcherSystem = null;
    if(placeholderFetcherSystem != null)
    {
      placeholderFetcherSystem.removeFetcher(this);
    }
    if (card != null)
    {
      card.setFetcher(null);
    }
    CardDatabase existingCardDatabase = cardDatabase;
    cardDatabase = null;
    if (existingCardDatabase != null)
    {
      existingCardDatabase.setFetcher(null);
    }
  }

  // line 69 "../../../Fetcher.ump"
   public void fetchAll(){
    WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
    //Shutdown some error messages
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setPrintContentOnFailingStatusCode(false);
    webClient.getOptions().setJavaScriptEnabled(true);
    webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener()); webClient.setCssErrorHandler(new SilentCssErrorHandler());

    
    
    try {
       HtmlPage page = webClient.getPage(url);
       webClient.waitForBackgroundJavaScript(3_000);
       webClient.getCurrentWindow().getJobManager().removeAllJobs();
       webClient.close();
       //Extract information
       System.out.println(page.asNormalizedText());
       List<DomElement> ListOfCards = page.getByXPath("//div[@class='hawk-results__item-name']");
       
       for (DomElement card: ListOfCards) {
         System.out.println("Found a card! Now analyzing it");
         String cardName;
         String cardSet;
         double concurrentPrice=0;
         DomElement cardNameHTML = card.getFirstByXPath("//h4[@class='hawk-results__hawk-contentTitle']");
         cardName = cardNameHTML.asNormalizedText();
         DomElement cardSetHTML = card.getFirstByXPath("//p[@class='hawk-results__hawk-contentSubtitle']");
         cardSet = cardSetHTML.asNormalizedText();
         
         Card cardObject = new Card(Card.convertToCardId(cardName, cardSet), cardName, cardSet, cardDatabase, fetcherSystem);
         
         //find the card price currently displayed on the website
         List<DomElement> ListOfPrices = card.getByXPath("//span[@class='retailPrice hawkPrice']");
         List<DomElement> ListOfStockStatus = card.getByXPath("//span[@class='hawkStock']");
         for (int i=0; i<=3; i++) {
           String strPrice = ListOfPrices.get(i).asNormalizedText();
           double price = Double.parseDouble(strPrice.trim().replaceAll("CAD $ ", ""));
           boolean isInStock = !(ListOfStockStatus.get(i).asNormalizedText() == "Out of Stock");
           
           boolean isNM = (i == 0 || i == 3);
           boolean isFoil = (i >= 2);
           cardObject.addPrice(price, concurrentPrice, isNM, isInStock, isFoil, fetchDate, fetcherSystem);
           
         }
         
         int index = cardDatabase.indexOfCard(cardObject);
         if (index > -1) {
           Card existingCard = cardDatabase.getCard(index);
           //Transfer all prices from fetched card to existing card
           while (cardObject.hasPrices()) {
             Price price = cardObject.getPrice(0);
             existingCard.addPrice(price);
             cardObject.removePrice(price);
           }
         }
         else cardDatabase.addCard(cardObject);
         
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
            "  " + "fetcherSystem = "+(getFetcherSystem()!=null?Integer.toHexString(System.identityHashCode(getFetcherSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "card = "+(getCard()!=null?Integer.toHexString(System.identityHashCode(getCard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardDatabase = "+(getCardDatabase()!=null?Integer.toHexString(System.identityHashCode(getCardDatabase())):"null");
  }
}