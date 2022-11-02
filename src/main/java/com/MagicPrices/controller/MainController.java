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
    String command = "";
    while(true) {
      //Intro
      System.out.println("Welcome.\nPlease input your command");
      command = inputReader.nextLine().toLowerCase();
      if (command.equals("fetchallacard")||command.equals("faac")) {
        System.out.println("Please input your card name: ");
        String cardName = inputReader.nextLine().toLowerCase();
        FetcherController.fetchCardByCardName(cardName);
      }
      else if (command.equals("quit")||command.equals("q")) break;
      else printHelp();
      

    }

    
  }
  private void printHelp() {
    System.out.println("All commands: \n"
        + "Command\t\tAbrievated\tUsage\n"
        + "fetchallacard\tfaac\tFetch all the available price options from a card name.\n"
        + "quit\t\tq\tClose the program.");
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
