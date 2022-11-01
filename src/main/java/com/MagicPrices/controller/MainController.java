package com.MagicPrices.controller;

import java.util.Scanner;
import javax.annotation.PostConstruct;
import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainController implements CommandLineRunner{
  private static FetcherSystem system;
  private static MainMenu menu;
  
  @Override
  public void run(String... args) throws Exception {
       
    system = new FetcherSystem();
    system.setMainMenu(MainController.getMainMenu());
    
    Scanner inputReader = new Scanner(System.in);
    
    //Intro
    System.out.println("Welcome.\n Please input your card name:");
    String cardName = inputReader.nextLine();
    //Test execution
    
    FetcherController.findCardByCardName(cardName);
    
  }
  public static FetcherSystem getFetcherSystem() {
    if (system == null) {
      system = new FetcherSystem();
     
    }
    return system;
  }
  
     public static MainMenu getMainMenu() {
        if (menu == null) {
          menu = new MainMenu(MainController.getFetcherSystem());
         
        }
        return menu;
      }

  
}
