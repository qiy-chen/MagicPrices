package com.MagicPrices.controller;

import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.Fetcher;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.model.Price;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FetcherController {

  private static FetcherSystem system = MainController.getFetcherSystem();
  private static MainMenu menu = MainController.getMainMenu();
  private static CardDatabase database = MainController.getCardDatabase();
  private static double conversionRateUSDToCAD;
  private static LocalDateTime currentTime;
  private static boolean preferedInStock = true;
  private static boolean preferedNMCondition = true;
  private static boolean preferedFoilCondition = false;
  private static boolean fastMode = false;
  private static boolean looseSearch;

  public FetcherController() {}
  /**
   * Fetch and upgrade prices related to a card by name using the website's search function
   * @param cardName - Name of the card to be updated
   * @param aIsInStock - Search with the option "the card is in stock"
   * @param aPreferedNMCondition - Search with the option "the card is NM"
   * @param aPreferedFoilCondition - Search with the option "the card is Foil"
   * @param aFastMode - Search only the first page if true
   * @return Card object that is being updated
   */
  public static void fetchCardByCardName(String cardName, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition,boolean aFastMode, boolean alooseSearch,WebDriver driver) {
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

    System.out.println("Fetching now "+cardName);
    int pagenb = 1;
    boolean success = true;
    while (success) {
      String url = generateURL(cardName, aIsInStock, aPreferedNMCondition, aPreferedFoilCondition, looseSearch, pagenb);
      System.out.println("Fetching card at "+url+"\nConversion: "+conversionRateUSDToCAD+"\tTime: "+currentTime.toString());


      Fetcher fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system, database);
      success = fetcher.fetchAllPage(driver);
      fetcher.delete();
      if (fastMode) success = false;
      pagenb++;
    }
    System.out.println("Search done. Enter 'pd' to print database.");
  }


  /**
   * Overload method for fetchCardByCardName with default boolean parameters and a loose search
   * @param cardName - Name of the card to be updated
   * @return Card object that is being updated
   */
  public static void fetchCardByCardName(String cardName,WebDriver driver) {
    fetchCardByCardName(cardName,false,true,false,true,true,driver);
  }

  /**
   * Update the current conversion rate (between USD and CAD)
   * @return true if success, false if there is an error
   */
  public static boolean updateConversionRate() {
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
  public static List<WebElement> printPageFromURL(String url, WebDriver driver) {
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
  public static LocalDateTime updateCurrentTime() {
    return currentTime = system.updateCurrentDate();
  }

}
