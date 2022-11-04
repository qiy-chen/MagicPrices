package com.MagicPrices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainController implements CommandLineRunner{
  private static FetcherSystem system;
  private static MainMenu menu;
  private static CardDatabase database;
  private static WebDriver driver;
  private long startTime = 0;
  private long endTime = 0;
  private String filePath = "./";

  @Override
  public void run(String... args) throws Exception {

    system = new FetcherSystem();
    system.setMainMenu(MainController.getMainMenu());
    MainController.getWebDriver();

    Scanner inputReader = new Scanner(System.in);
    String command = "";
    //Intro
    System.out.println("Welcome.\nPlease input your command");
    //while(true) {
    while (inputReader.hasNext()) {
      command = inputReader.nextLine().toLowerCase();
      if (command.equals("fetchallacard")||command.equals("faac")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        FetcherController.fetchCardByCardName(input,driver);
        setEndTime(command);
      }
      if (command.equals("fetchall")||command.equals("fa")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        FetcherController.fetchCardByCardName(input,false,true,false,false,true,driver);
        setEndTime(command);
      }
      else if (command.equals("quit")||command.equals("q")) break;
      //else if (command.equals("sortdatabase")||command.equals("sd")) CardDatabaseController.rebuilDatabase(database);
      else if (command.equals("printdatabase")||command.equals("pd")) CardDatabaseController.printDatabase(database);
      else if (command.equals("binarysearchdbbyid")||command.equals("bsdid")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;

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
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        Card card = CardDatabaseController.findCardById(input);
        if (card == null) {
          System.out.println("Card not found. Please ensure that the id is correct.");

        }
        else CardDatabaseController.printCard(card);
        setEndTime(command);
      }
      else if (command.equals("linearsearchdbbyname")||command.equals("lsdn")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase().replaceAll(" ", "");
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        List<Card> cards = CardDatabaseController.findCardsByName(input);
        if (cards.size() == 0) {
          System.out.println("No card found. Please ensure that the name is correct.");

        }
        else CardDatabaseController.printCards(cards);
        setEndTime(command);
      }
      else if (command.equals("restartdriver")||command.equals("rd")) {
        if (driver!=null) driver.close();
        driver = null;
        MainController.getWebDriver();
      }
      else if (command.equals("discoverpageurl")||command.equals("dpurl")) {
        System.out.println("Please input your url: ");
        String input = inputReader.nextLine();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        FetcherController.printPageFromURL(input, driver);
        setEndTime(command);
      }
      else if (command.equals("createlist")||command.equals("cl")) {
        System.out.println("You will now create a list of cards from scratch. Press \\return or \\r when the list is complete");
        List<String> userList = new ArrayList<String>();
        while (inputReader.hasNext()) {
          String input = inputReader.nextLine();
          if (input.equals("\\return")|input.equals("\\r")) break;
          System.out.println("Please select the correct card by entering the number before the name of the card. Press 'p' to go to the previous page and 'n' to go to the next page.");
          boolean activeSearch = true;
          int pagenb = 1;
          while (activeSearch) {
            String url = FetcherController.generateURL(input, false, false, false, true, pagenb);
            List<WebElement> listOfCards = FetcherController.printPageFromURL(url, driver);
            String cardNumber = inputReader.nextLine();
            if (cardNumber.equals("p")) {
              if (pagenb>1) pagenb--;
              else System.out.println("This is already the first page.");
            }
            else if (cardNumber.equals("n")) {
              pagenb++;
            }
            else if (cardNumber.equals("\\return")|cardNumber.equals("\\r")) activeSearch = false;
            else {
              int nb;
              try {
                nb = Integer.parseInt(cardNumber);
                userList.add(Card.convertToCardId(listOfCards.get(nb)));
                System.out.println("Successfully added "+Card.convertToCardId(listOfCards.get(nb))+"\nPlease enter your next entry or press \\return or \\r to terminate the search.");
                activeSearch = false;
              }
              catch (Exception e) {
                System.out.println("Wrong input.");
              }
            }
          }
        }
        setStartTime();
        if (userList.size()>0) FileManager.createIdListFromScratch(userList,filePath);
        setEndTime(command);

      }
      else printHelp();

      System.out.println("Welcome.\nPlease input your command");
    }
    if (driver!=null) driver.close();
  }

  //}
  private void printHelp() {
    System.out.println("All commands: \n"
        + "Command\t\t\tAbrievated\tUsage\n"
        + "fetchallpage\t\tfapage\tFetch all the available price options from a card name search from the first page.\n"
        + "fetchall\t\tfa\tFetch all the available price options from a card name search.\n"
        + "discoverpageurl\tdpurl\tPrint the name, set and id of all cards on the page of target URL.\n"
        + "printdatabase\t\tpd\tPrint all the cards in the database.\n"
        + "binarysearchdbbyid\tbsdid\tSearch the database by id using binary search.\n"
        + "linearsearchdbbyid\tlsdid\tSearch the database by id using linear search.\n"
        + "linearsearchdbbyname\tlsdn\tSearch the database by name using linear search.\n"
        + "createlist\t\tcl\tCreate a list of cards from scratch.\n"
        + "restartdriver\t\trd\tRestart the web driver. Use it if the web driver hasn't started or if there are some issues with it.\n"
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

  public static WebDriver getWebDriver() {
    if (driver == null) {
      try {
        driver = new SafariDriver();
      }
      catch (Exception e){
        System.out.println("Error when starting up web driver. Please make sure that the browser support remote automation and try again.\nError code:");
        System.out.println(e);
      }
    }
    return driver;
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
