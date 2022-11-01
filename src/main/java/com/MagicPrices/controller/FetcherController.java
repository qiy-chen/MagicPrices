package com.MagicPrices.controller;

import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.Fetcher;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FetcherController {

  private static FetcherSystem system = MainController.getFetcherSystem();
  private static MainMenu menu = MainController.getMainMenu();
  private static double conversionRateUSDToCAD;
  private static LocalDateTime currentTime;
  private static boolean preferedInStock = true;
  private static boolean preferedNMCondition = true;
  private static boolean preferedFoilCondition = false;
  private static boolean fastMode = false;
  
  public FetcherController() {}
  
  public static Card findCardByCardName(String cardName, boolean aIsInStock, boolean aPreferedNMCondition, boolean aPreferedFoilCondition,boolean aFastMode) {
    preferedInStock = aIsInStock;
    preferedNMCondition = aPreferedNMCondition;
    preferedFoilCondition = aPreferedFoilCondition;
    fastMode = aFastMode;
    System.out.println("------------------------------------------");

    if (conversionRateUSDToCAD == 0) {
      if (!updateConversionRate()) return null;
    }
    if (currentTime == null) {
      if (updateCurrentTime() == null) return null;
    }
    System.out.println("Seeking now "+cardName);
    int pagenb = 99;
    String url = "https://www.facetofacegames.com/search/?keyword="+cardName+"&pg="+pagenb+"&tab=Magic&product%20type=Singles";
    if (preferedInStock) url+="&child_inventory_level=1";
    if (preferedNMCondition) url+="&option_condition=NM";
    else url+="&option_condition=PL";
    if (preferedFoilCondition) url+="&option_finish=Foil";
    else url+="&option_finish=Non-Foil";
    System.out.println("Seeking card at "+url+"\tConversion: "+conversionRateUSDToCAD+"\tTime: "+currentTime.toString());
    
    
    Fetcher fetcher = new Fetcher(currentTime,url,conversionRateUSDToCAD,menu,system);
    //Card card = fetcher.getCard(0);
    return null;
  }
  
  public static Card findCardByCardName(String cardName) {
    return findCardByCardName(cardName,true,true,false,false);
  }
  
  public static boolean updateConversionRate() {
    String from = "usd";
    String to = "cad";
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //Shutdown some error messages
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setPrintContentOnFailingStatusCode(false);

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
  public static LocalDateTime updateCurrentTime() {
    return currentTime = system.updateCurrentDate();
  }
  
}
