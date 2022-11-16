package com.MagicPrices.controller;

import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.Fetcher;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.model.Price;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

public class FetcherController {

  private FetcherSystem system = MainController.getFetcherSystem();
  private MainMenu menu = MainController.getMainMenu();
  private CardDatabase database = MainController.getCardDatabase();
  private double conversionRateUSDToCAD;
  private LocalDateTime currentTime;
  private boolean preferedInStock = true;
  private boolean preferedNMCondition = true;
  private boolean preferedFoilCondition = false;
  private boolean fastMode = false;
  private boolean looseSearch;

  private Fetcher fetcher;
  private CardRepository cardRepository;
  private PriceRepository priceRepository;

  public FetcherController() {}
  /**
   * Fetch and upgrade prices related to a list of cards by name using the website's search function
   * @param cardNameList - List of card's names to be fetched and saved
   * @param aIsInStock - Search with the option "the card is in stock"
   * @param aPreferedNMCondition - Search with the option "the card is NM"
   * @param aPreferedFoilCondition - Search with the option "the card is Foil"
   * @param aFastMode - Search only the first page if true
   * @param alooseSearch - the search will be as general as possible
   * @param driver - Current WebDriver instance
   */
  public void fetchCardsByCardName(List<String> cardNameList, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition,boolean aFastMode, boolean alooseSearch,WebDriver driver) {

    preferedInStock = aIsInStock;
    preferedNMCondition = aPreferedNMCondition;
    preferedFoilCondition = aPreferedFoilCondition;
    fastMode = aFastMode;
    looseSearch = alooseSearch;
    System.out.println("------------------------------------------");

    if (driver == null) {
      System.out.println("Error, no web driver was started, please enter 'rd' to attempt to open a new web driver.");
      return;
    }

    if(!updateConversionRate()) {
      System.out.println("There were an error when updating the conversion rate.");
      return;
    }

    if (updateCurrentTime() == null) {
      System.out.println("There were an error when updating the current time.");
      return;
    }
    for (String cardName: cardNameList) {
      System.out.println("Fetching now "+cardName);
      int pagenb = 1;
      boolean success = true;
      while (success) {
        String url = generateURL(cardName, aIsInStock, aPreferedNMCondition, aPreferedFoilCondition, looseSearch, pagenb);
        System.out.println("Fetching card at "+url+"\nConversion: "+conversionRateUSDToCAD+"\tTime: "+currentTime.toString());


        fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system, database);
        fetcher.setRepositories(cardRepository, priceRepository);
        success = fetcher.fetchAllPage(driver);
        fetcher.delete();
        if (fastMode) success = false;
        pagenb++;
      }
    }
    System.out.println("Search done. Enter 'pr' to print repository.");
  }
  /**
   * Fetch and upgrade prices related to a card by name using the website's search function
   * @param cardName - Name of the card to be fetched and saved
   * @param aIsInStock - Search with the option "the card is in stock"
   * @param aPreferedNMCondition - Search with the option "the card is NM"
   * @param aPreferedFoilCondition - Search with the option "the card is Foil"
   * @param aFastMode - Search only the first page if true
   * @param alooseSearch - the search will be as general as possible
   * @param driver - Current WebDriver instance
   */
  public void fetchCardByCardName(String cardName, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition,boolean aFastMode, boolean alooseSearch,WebDriver driver) {
    List<String> list = new ArrayList<String>();
    list.add(cardName);
    fetchCardsByCardName(list,  aIsInStock,  aPreferedNMCondition,  aPreferedFoilCondition, aFastMode,  alooseSearch, driver);
  }

  /**
   * Overload method for fetchCardByCardName with default boolean parameters and a loose search activated
   * @param cardName - Name of the card to be fetched and saved
   * @param driver - Current WebDriver instance
   */
  public void fetchCardByCardName(String cardName,WebDriver driver) {
    fetchCardByCardName(cardName,false,true,false,true,true,driver);
  }

  /**
   * Overload method for fetchCardsByCardName with default boolean parameters and a loose search activated
   * @param cardNames - List of names of cards to be fetched and saved
   * @param driver - Current WebDriver instance
   */
  public void fetchCardsByCardName(List<String> cardNames,WebDriver driver) {
    fetchCardsByCardName(cardNames,false,true,false,true,true,driver);
  }
  
  /**
   * Fetch and upgrade prices related to a card by its id using the website's search function
   * @param listOfCardId - List of ids of the cards to be fetched and saved
   * @param driver - Current WebDriver instance
   */
  public void fetchCardsByCardId(List<String> listOfCardId, WebDriver driver) {
    if (driver == null) {
      System.out.println("Error, no web driver was started, please enter 'rd' to attempt to open a new web driver.");
      return;
    }

    if(!updateConversionRate()) {
      System.out.println("There were an error when updating the conversion rate.");
      return;
    }

    if (updateCurrentTime() == null) {
      System.out.println("There were an error when updating the current time.");
      return;
    }
    for (String cardId: listOfCardId) {
      List<String> splitId = MainController.splitCardId(cardId, "NM", "Non-Foil");
      //Get card Id without card's condition and foiling
      cardId = splitId.get(0);
      String cardName = cardId.replaceAll("-", " ");
      int pagenb = 1;
      boolean success = false;
      while(!success) {
        String url = generateURL(cardName, false, false, false, true, pagenb);
        fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system, database);
        fetcher.setRepositories(cardRepository, priceRepository);
        boolean noResult = fetcher.noResult(driver);
        //Exit the loop if the card hasn't been found
        if (noResult) {
          fetcher.delete();
          break;
        }
        success = fetcher.fetchCardId(driver, cardId);
        fetcher.delete();
        //Continue to search next pages if card not found and there are still some results
        pagenb++;
      }
      if(!success) System.out.println("Card with id "+cardId+" was not found");
    }
  }
  
  /**
   * Fetch and upgrade prices related to a card by its id using the website's search function
   * @param cardId - The id of the cards to be fetched and saved
   * @param driver - Current WebDriver instance
   */
  public void fetchCardByCardId(String cardId, WebDriver driver) {
    List<String> list = new ArrayList<String>();
    list.add(cardId);
    fetchCardsByCardId(list, driver);
  }


  /**
   * Update the current conversion rate (between USD and CAD)
   * @return true if success, false if there is an error
   */
  public boolean updateConversionRate() {
    String from = "usd";
    String to = "cad";
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //Shutdown some error messages
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setPrintContentOnFailingStatusCode(false);
    webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener()); webClient.setCssErrorHandler(new SilentCssErrorHandler());
    try {
      HtmlPage page = webClient.getPage("https://ca.investing.com/currencies/"+from+"-"+to);

      webClient.getCurrentWindow().getJobManager().removeAllJobs();
      webClient.close();
      //Extract information
      DomElement element = page.getFirstByXPath("//span[@data-test='instrument-price-last']");
      System.out.println("Current conversion rate from "+from+" to "+to+" is "+element.asNormalizedText());
      conversionRateUSDToCAD = Double.parseDouble(element.asNormalizedText());

      return true;

    } catch (IOException e) {
      System.out.println("An error occurred: " + e);
      webClient.close();
      return false;
    }
  }

  /**
   * Print a numbered list of all the cards found in a search url page
   * @param url -  url website (search page)
   * @param driver - Current WebsiteDriver
   * @return - List of cards as WebElements found in the search url page
   */
  public List<WebElement> printPageFromURL(String url, WebDriver driver) {
    if (driver == null) {
      System.out.println("Error, no web driver is active, please enter 'rd' to attempt to open a new web driver.");
      return null;
    }
    Fetcher fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system, database);
    List<WebElement> listOfCards = fetcher.discoverPage(url, driver);
    System.out.println("Card(s) at "+url);
    System.out.println("--------------------------------------");
    //Print name, set and id (url)
    int cardNumber = 0;
    for (WebElement card: listOfCards) {
      WebElement cardNameHTML = card.findElement(By.className("hawk-results__hawk-contentTitle"));
      String cardName = cardNameHTML.getText().trim();
      WebElement cardSetHTML = card.findElement(By.className("hawk-results__hawk-contentSubtitle"));
      String cardSet = cardSetHTML.getText().trim();
      String cardId=Card.convertToCardId(card);
      System.out.println("["+ cardNumber++ +"] Name: "+cardName+" | Set: "+cardSet+"\nCard Id: "+cardId);
      System.out.println("--------------------------------------");
    }
    System.out.println("--------------------------------------");
    fetcher.delete();
    return listOfCards;
  }

  /**
   * Generate a url link based on given parameters
   * @param cardName - Name of the card
   * @param aIsInStock - If the card is in stock
   * @param aPreferedNMCondition - If the card will be shown as in Near-Mint condition
   * @param aPreferedFoilCondition - If the card will be shown as in Foil condition
   * @param alooseSearch - Only take the card's name in consideration for the search
   * @param pagenb - the current page number of the search page
   * @return - A String containing the full url
   */
  public static String generateURL(String cardName, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition, boolean alooseSearch, int pagenb) {
    String url = "https://www.facetofacegames.com/search/?keyword="+cardName+"&pg="+pagenb+"&tab=Magic&product%20type=Singles";

    if (!alooseSearch) {
      if (aIsInStock) url+="&child_inventory_level=1";
      if (aPreferedNMCondition) url+="&option_condition=NM";
      else url+="&option_condition=PL";
      if (aPreferedFoilCondition) url+="&option_finish=Foil";
      else url+="&option_finish=Non-Foil";
    }
    url = url.replaceAll(" ", "%20");
    return url;
  }

  /**
   * Update the current time
   * @return LocalDateTime of the current time
   */
  public LocalDateTime updateCurrentTime() {
    return currentTime = system.updateCurrentDate();
  }

  public void setRepositories(CardRepository cardRepository, PriceRepository priceRepository) {
    this.cardRepository = cardRepository;
    this.priceRepository = priceRepository;
  }

}
