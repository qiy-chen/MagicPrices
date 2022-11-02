package com.MagicPrices.controller;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import com.MagicPrices.MagicPricesApplication;
import com.MagicPrices.model.Card;
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
  private long startTime = 0;
  private long endTime = 0;

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
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\return")|input.equals("\r")) continue;
        setStartTime();
        FetcherController.fetchCardByCardName(input);
        setEndTime(command);
      }
      else if (command.equals("quit")||command.equals("q")) break;
      else if (command.equals("sortdatabase")||command.equals("sd")) CardDatabaseController.rebuilDatabase(database);
      else if (command.equals("printdatabase")||command.equals("pd")) CardDatabaseController.printDatabase(database);
      else if (command.equals("binarysearchdbbyid")||command.equals("bsdid")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\return")|input.equals("\r")) continue;
        
        setStartTime();
        Card card = CardDatabaseController.findCardByIdBinarySearch(input);
        if (card == null) {
          System.out.println("Card not found. Please ensure that the id is correct and the database is sorted when using binary search.");

        }
        else CardDatabaseController.printCard(card);
        setEndTime(command);
      }
      else if (command.equals("linearsearchdbbyid")||command.equals("lsdid")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\return")|input.equals("\r")) continue;
        setStartTime();
        Card card = CardDatabaseController.findCardById(input);
        if (card == null) {
          System.out.println("Card not found. Please ensure that the id is correct.");

        }
        else CardDatabaseController.printCard(card);
        setEndTime(command);
      }
      else printHelp();

    }


  }
  private void printHelp() {
    System.out.println("All commands: \n"
        + "Command\t\t\tAbrievated\tUsage\n"
        + "fetchallacard\t\tfaac\tFetch all the available price options from a card name.\n"
        + "sortdatabase\t\tsd\tSort cards in database by id by ascending order.\n"
        + "printdatabase\t\tpd\tPrint all the cards in the database.\n"
        + "binarysearchdbbyid\tbsdid\tSearch the database using binary search. WARNING: The database must be sorted (command 'sd') before using binary search.\n"
        + "linearsearchdbbyid\tlsdid\\tSearch the database using linear search.\n"
        + "\\return\t\t\t\\r\tGo to previous menu.\n"
        + "quit\t\t\tq\tClose the program.");
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
  
  public void setStartTime() {
    startTime = System.nanoTime();
  }
  
  public void setEndTime(String message) {
    this.endTime   = System.nanoTime();
    long totalTime = endTime - startTime;
    System.out.println("Operation "+ message +" took "+totalTime*0.000000001+" s");
  }

}
