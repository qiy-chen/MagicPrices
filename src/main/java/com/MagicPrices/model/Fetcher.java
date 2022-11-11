/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.Duration;
import java.time.LocalDateTime;
import com.MagicPrices.controller.CardDatabaseController;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// line 57 "../../../Fetcher.ump"
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
  
  //Repositories
  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private PriceRepository priceRepository;

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

  // line 78 "../../../Fetcher.ump"
   public boolean fetchAllPage(WebDriver driver){
    boolean success = false;
    try {
      
      //Extract information
      List<WebElement> ListOfCards = discoverPage(url,driver);
      
      if (ListOfCards.size()<1) return success;
      for (WebElement card: ListOfCards) {
        System.out.println("Found a card! Now analyzing it");
        String cardName;
        String cardSet;
        String cardId;
        double concurrentPrice=0;
        String condition;
        String foiling;
        int amountInStock = 0;
        WebElement cardNameHTML = card.findElement(By.className("hawk-results__hawk-contentTitle"));
        cardName = cardNameHTML.getText().trim();
        WebElement cardSetHTML = card.findElement(By.className("hawk-results__hawk-contentSubtitle"));
        cardSet = cardSetHTML.getText().trim();

        cardId = Card.convertToCardId(card);
        //Card existingCard = CardDatabaseController.findCardById(Card.convertToCardId(card));
        Card existingCard = cardRepository.findCardByCardId(cardId);
        if (existingCard ==null) {
          //Card existingCard = CardDatabaseController.findCardById(Card.convertToCardId(card));
          existingCard = new Card();
          existingCard.setCardId(cardId);
          existingCard.setCategory(cardSet);
          existingCard.setName(cardName);
        }
        //Special case if the card is unique (scan)
        if (cardName.contains(" - Scan")){
          fetchSinglePrice(card, concurrentPrice, existingCard, "Scan");
        }
        //Special case if it's an Art Card
        else if(cardName.contains(" Art Card")) {
          fetchSinglePrice(card, concurrentPrice, existingCard, "Art Card");
        }
        else {
          List<List<String>> radioTable = new ArrayList<List<String>>();
          //Search all the pricing options that are available for that card, compile the result in two different lists
          List<WebElement> ListOfConditions = card.findElements(By.cssSelector("[data-variant-name=condition]"));
          List<WebElement> ListOfFoiling = card.findElements(By.cssSelector("[data-variant-name=finish]"));


          //Special case where there is only a single price for a card, no pricing option available
          if (ListOfConditions.size()<1||ListOfFoiling.size()<1) {
            fetchSinglePrice(card, concurrentPrice, existingCard, "N/A");
            success=true;
            return success;
          }
          
          //Create two rows of lists
          radioTable.add(new ArrayList<String>());
          radioTable.add(new ArrayList<String>());
          //Populate the first row with card's available conditions
          for (int i =0; i<ListOfConditions.size(); i++) {
            radioTable.get(0).add(ListOfConditions.get(i).getDomAttribute("value"));
          }

          //Populate the second row with card's available foilings
          for (int i =0; i<ListOfFoiling.size(); i++) {
            radioTable.get(1).add(ListOfFoiling.get(i).getDomAttribute("value"));
          }

          //find the list of card price and stock status currently on the page
          List<WebElement> ListOfPrices = card.findElements(By.className("retailPrice"));
          List<WebElement> ListOfStockStatus = card.findElements(By.className("hawkStock"));

          int i = 0;

          //Get the next available combination of foiling and condition and assign it to the next available price
          while (i < ListOfPrices.size()){
            for (int j=0; j<ListOfFoiling.size(); j++) {
              foiling = radioTable.get(1).get(j);
              for (int k=0;k<ListOfConditions.size();k++) {
                //Add the next price with the next combination of NM and Foil available for the card
                condition = radioTable.get(0).get(k);
                String strPrice = ListOfPrices.get(i).getText().trim();
                double amount = Double.parseDouble(strPrice.replaceAll(java.util.regex.Matcher.quoteReplacement("CAD $"), "").replaceAll(",", ""));
                amountInStock = Integer.parseInt(ListOfStockStatus.get(i).getDomAttribute("data-stock-num"));
                //existingCard.addPrice(price, concurrentPrice, condition, amountInStock, foiling, fetchDate, fetcherSystem);
                Price price = new Price();
                price.setAmount(amount);
                price.setConcurrentPrice(concurrentPrice);
                price.setCondition(condition);
                price.setAmountInStock(amountInStock);
                price.setFoiling(foiling);
                price.setFetchDate(fetchDate);
                price.setFetchDateString(fetchDate.toString());
                price.setCard(existingCard);
                existingCard = cardRepository.save(existingCard);
                price = priceRepository.save(price);
                i++;
              }
            }

          }
        }
      }
      success = true;
    }
    catch (Exception e) {
      System.out.println("Error: "+e);
      success = false;
    }
    return success;
  }


  /**
   * 
   * Method to get the list of cards as WebElement from a search result
   * @param url - url must be derived from a search result page
   * @param driver - Instance of the current WebDriver
   * @return - List of WebElement of all cards found in the url content
   */
  // line 176 "../../../Fetcher.ump"
   public List<WebElement> discoverPage(String url, WebDriver driver){
    driver.get(url);
      List<WebElement> listOfCards = new ArrayList();
      WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement results = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("hawk-results")));
      //If no results, return immediately
      if (results.getText().trim().equals("No Results")) return listOfCards;
      listOfCards = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("retailPrice"))).findElements(By.xpath("//div[@class='hawk-results__item']"));
     return listOfCards;
  }


  /**
   * 
   * Helper method to deal with card with only one pricing option
   * @return - true if successful, false if there is a failure
   */
  // line 188 "../../../Fetcher.ump"
   private boolean fetchSinglePrice(WebElement card, double concurrentPrice, Card existingCard, String fillerText){
    boolean success = false;
     String condition = fillerText;
     String foiling = fillerText;
     int amountInStock = Integer.parseInt(card.findElement(By.className("hawkStock")).getDomAttribute("data-stock-num"));
     double amount = Double.parseDouble(card.findElement(By.className("retailPrice")).getText().replaceAll(java.util.regex.Matcher.quoteReplacement("CAD $"), "").replaceAll(",", ""));
     //existingCard.addPrice(price, concurrentPrice, condition, amountInStock, foiling, fetchDate, fetcherSystem);
     Price price = new Price();
     price.setAmount(amount);
     price.setConcurrentPrice(concurrentPrice);
     price.setCondition(condition);
     price.setAmountInStock(amountInStock);
     price.setFoiling(foiling);
     price.setFetchDate(fetchDate);
     price.setFetchDateString(fetchDate.toString());
     price.setCard(existingCard);
     existingCard = cardRepository.save(existingCard);
     price = priceRepository.save(price);
     success = true;
     return success;
  }
   
   public void setRepositories(CardRepository cardRepository, PriceRepository priceRepository) {
     this.cardRepository = cardRepository;
     this.priceRepository = priceRepository;
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