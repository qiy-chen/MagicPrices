/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.MagicPrices.model;
import java.time.LocalDateTime;
import com.MagicPrices.controller.CardDatabaseController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

// line 53 "../../../Fetcher.ump"
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

  // line 73 "../../../Fetcher.ump"
   public boolean fetchAll(){
    boolean success = false;
    WebDriver driver = null;

    try {
      driver = new SafariDriver();
      //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.get(url);
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

      //Extract information
      //System.out.println(driver.getPageSource());
      List<WebElement> ListOfCards = driver.findElements(By.xpath("//div[@class='hawk-results__item']"));

      for (WebElement card: ListOfCards) {
        System.out.println("Found a card! Now analyzing it");
        //System.out.println(card.getText());
        String cardName;
        String cardSet;
        double concurrentPrice=0;
        String condition;
        String foiling;
        int amountInStock = 0;
        WebElement cardNameHTML = card.findElement(By.className("hawk-results__hawk-contentTitle"));
        cardName = cardNameHTML.getText().trim();
        WebElement cardSetHTML = card.findElement(By.className("hawk-results__hawk-contentSubtitle"));
        cardSet = cardSetHTML.getText().trim();

        Card existingCard = CardDatabaseController.findCardById(Card.convertToCardId(cardName, cardSet));
        if (existingCard ==null) {
          existingCard = new Card(Card.convertToCardId(cardName, cardSet), cardName, cardSet, cardDatabase, fetcherSystem);
        }
        //Special case if the card is unique (scan)
        if (cardName.contains(" - Scan")){
          condition = "Scan";
          foiling = "Scan";
          amountInStock = Integer.parseInt(card.findElement(By.className("hawkStock")).getDomAttribute("data-stock-num"));
          double price = Double.parseDouble(card.findElement(By.className("retailPrice")).getText().replaceAll(java.util.regex.Matcher.quoteReplacement("CAD $"), ""));
          existingCard.addPrice(price, concurrentPrice, condition, amountInStock, foiling, fetchDate, fetcherSystem);
        }
        //Special case if it's an Art Card
        else if(cardName.contains(" Art Card")) {
          condition = "Art Card";
          foiling = "Art Card";
          amountInStock = Integer.parseInt(card.findElement(By.className("hawkStock")).getDomAttribute("data-stock-num"));
          double price = Double.parseDouble(card.findElement(By.className("retailPrice")).getText().replaceAll(java.util.regex.Matcher.quoteReplacement("CAD $"), ""));
          System.out.println("Added to the database: " + price +"\t"+ amountInStock +"\t"+ condition +"\t"+ foiling +"\t"+ fetchDate);
          existingCard.addPrice(price, concurrentPrice, condition, amountInStock, foiling, fetchDate, fetcherSystem);
        }
        else {
          List<List<String>> radioTable = new ArrayList<List<String>>();
          //Search all the options that are available for that card, compile the result in a table
          //populate the 2d table
          List<WebElement> ListOfConditions = card.findElements(By.cssSelector("[data-variant-name=condition]"));
          List<WebElement> ListOfFoiling = card.findElements(By.cssSelector("[data-variant-name=finish]"));

          //Create two rows of array
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

          //for (List<String> a : radioTable) {
           // String str = "";
           // for (int i=0;i<a.size();i++) {
           //   str+=a.get(i)+"|";
           // }
          //  System.out.println(str);
          //}
          //find the card price currently displayed on the website
          List<WebElement> ListOfPrices = card.findElements(By.className("retailPrice"));
          List<WebElement> ListOfStockStatus = card.findElements(By.className("hawkStock"));
          //for (WebElement a: ListOfStockStatus) {
          //  System.out.println(a.getText());
          //}

          int i = 0;

          while (i < ListOfPrices.size()){
            for (int j=0; j<ListOfFoiling.size(); j++) {
              foiling = radioTable.get(1).get(j);
              for (int k=0;k<ListOfConditions.size();k++) {
                //Add the next price with the next combination of NM and Foil available for the card
                condition = radioTable.get(0).get(k);
                String strPrice = ListOfPrices.get(i).getText().trim();
                //System.out.println("Price: " + strPrice);
                double price = Double.parseDouble(strPrice.replaceAll(java.util.regex.Matcher.quoteReplacement("CAD $"), ""));
                //if (!ListOfStockStatus.get(i).getText().equals("Out of Stock")) {
                //There is some cards in stock
                //  amountInStock = Integer.parseInt(ListOfStockStatus.get(i).getText().replaceAll(" In Stock", ""));
                //}
                amountInStock = Integer.parseInt(ListOfStockStatus.get(i).getDomAttribute("data-stock-num"));


                System.out.println("Added to the database: " + price +"\t"+ amountInStock +"\t"+ condition +"\t"+ foiling +"\t"+ fetchDate);
                existingCard.addPrice(price, concurrentPrice, condition, amountInStock, foiling, fetchDate, fetcherSystem);
                i++;
              }
            }

          }
        }
      }
      success = true;
      driver.quit();

    }
    catch (Exception e) {
      System.out.println("Error: "+e);
      success = false;
      if (driver!=null) driver.quit();
    }
    return success;
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