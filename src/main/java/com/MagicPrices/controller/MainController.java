package com.MagicPrices.controller;

import java.util.Scanner;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainController implements CommandLineRunner{
  private static FetcherSystem system;
  private static MainMenu menu;
  private static CardDatabase database;
  
  @Override
  public void run(String... args) throws Exception {
       
    system = new FetcherSystem();
    system.setMainMenu(MainController.getMainMenu());
    
    Scanner inputReader = new Scanner(System.in);
    
    //Intro
    System.out.println("Welcome.\n Please input your card name:");
    //String cardName = inputReader.nextLine();
    //Test execution
    
    FetcherController.fetchCardByCardName("sneak attack");
    
  }
  public static FetcherSystem getFetcherSystem() {
    if (system == null) {
      system = new FetcherSystem();
     
    }
    return system;
  }
  
     public static MainMenu getMainMenu() {
        if (menu == null) {
          menu = new MainMenu(MainController.getFetcherSystem(), MainController.getCardDatabase());
         
        }
        return menu;
      }
     
     public static CardDatabase getCardDatabase() {
       if (database == null) {
         database = new CardDatabase(MainController.getFetcherSystem());
        
       }
       return database;
     }
  
}
