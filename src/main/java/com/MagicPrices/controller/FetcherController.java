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
   * Fetch and upgrade prices related to a card by name
   * @param cardName - Name of the card to be updated
   * @param aIsInStock - Search with the option "the card is in stock"
   * @param aPreferedNMCondition - Search with the option "the card is NM"
   * @param aPreferedFoilCondition - Search with the option "the card is Foil"
   * @param aFastMode - Search only the first page if true
   * @return Card object that is being updated
   */
  public static void fetchCardByCardName(String cardName, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition,boolean aFastMode, boolean alooseSearch) {
    preferedInStock = aIsInStock;
    preferedNMCondition = aPreferedNMCondition;
    preferedFoilCondition = aPreferedFoilCondition;
    fastMode = aFastMode;
    looseSearch = alooseSearch;
    System.out.println("------------------------------------------");

    if(!updateConversionRate()) return;
 
    if (updateCurrentTime() == null) return;
    
    System.out.println("Seeking now "+cardName);
    int pagenb = 1;
    boolean success = true;
    while (success) {
      String url = "https://www.facetofacegames.com/search/?keyword="+cardName+"&pg="+pagenb+"&tab=Magic&product%20type=Singles";
      
      if (!looseSearch) {
        if (preferedInStock) url+="&child_inventory_level=1";
        if (preferedNMCondition) url+="&option_condition=NM";
        else url+="&option_condition=PL";
        if (preferedFoilCondition) url+="&option_finish=Foil";
        else url+="&option_finish=Non-Foil";
      }
      url = url.replaceAll(" ", "%20");
      System.out.println("Seeking card at "+url+"\nConversion: "+conversionRateUSDToCAD+"\tTime: "+currentTime.toString());
      
      
      Fetcher fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system, database);
      success = fetcher.fetchAll();
      fetcher.delete();
      if (fastMode) success = false;
      pagenb++;
    }

    //print all cards and their prices
    System.out.println("Database Content:");
    System.out.println("--------------------------------------");
    if (!database.hasCards()) System.out.println("The database is empty.");
    for (Card card: database.getCards()) {
      System.out.println("Prices of "+card.getName()+" | "+card.getCategory());
      System.out.println("(Card Id: "+card.getCardId()+")");
      System.out.println("--------------------------------------");
      System.out.println("Price\tIn Stock\tIs NM\tFoil\tDate");
      System.out.println("--------------------------------------");
      for (Price price: card.getPrices()) {
        System.out.println(price.getAmount() +"\t"+ price.getIsInStock() +"\t\t"+ price.getIsNM() +"\t"+ price.getIsFoil() +"\t"+ price.getFetchDate());
      }
      System.out.println("--------------------------------------");
    }
    System.out.println("--------------------------------------");
  }
  /**
   * Overload method for fetchCardByCardName with default boolean parameters and a loose search
   * @param cardName - Name of the card to be updated
   * @return Card object that is being updated
   */
  public static void fetchCardByCardName(String cardName) {
    fetchCardByCardName(cardName,false,true,false,true,true);
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
   * Update the current time
   * @return LocalDateTime of the current time
   */
  public static LocalDateTime updateCurrentTime() {
    return currentTime = system.updateCurrentDate();
  }
  
}
