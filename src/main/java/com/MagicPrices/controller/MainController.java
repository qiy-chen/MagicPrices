package com.MagicPrices.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
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
  private String filePath = "./output/";
  private final static String IDLISTFILEXTENSION = ".idlist";
  private final static String NAMELISTFILEXTENSION = ".namelist";
  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private PriceRepository priceRepository;

  @Override
  public void run(String... args) throws Exception {

    system = new FetcherSystem();
    system.setMainMenu(MainController.getMainMenu());
    MainController.getWebDriver();

    Scanner inputReader = new Scanner(System.in);
    FetcherController fetcherController = new FetcherController();
    fetcherController.setRepositories(cardRepository, priceRepository);
    CardDatabaseController cardDatabaseController = new CardDatabaseController();
    cardDatabaseController.setRepositories(cardRepository, priceRepository);
    FileManager fileManager = new FileManager();
    String command = "";
    //Intro
    System.out.println("Welcome.\nPlease input your command.");
    //while(true) {
    while (inputReader.hasNext()) {
      command = inputReader.nextLine().toLowerCase().trim();
      if (command.equals("fetchallfast")||command.equals("faf")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        fetcherController.fetchCardByCardName(input,driver);
        setEndTime(command);
      }
      else if (command.equals("fetchall")||command.equals("fa")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        fetcherController.fetchCardByCardName(input,false,true,false,false,true,driver);
        setEndTime(command);
      }
      else if (command.equals("fetchid")||command.equals("fid")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        fetcherController.fetchCardByCardId(input, driver);
        setEndTime(command);
      }
      else if (command.equals("quit")||command.equals("q")) break;
      //else if (command.equals("sortdatabase")||command.equals("sd")) CardDatabaseController.rebuilDatabase(database);
      else if (command.equals("printrepository")||command.equals("pr")) cardDatabaseController.printRepository();
      else if (command.equals("searchbyid")||command.equals("sbid")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase();
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        Card card = cardDatabaseController.searchRepositoryById(input);;
        if (card == null) {
          System.out.println("Card not found. Please ensure that the id is correct.");
        }
        else cardDatabaseController.printCard(card);
        setEndTime(command);
      }
      else if (command.equals("searchbyname")||command.equals("sbn")) {
        System.out.println("Please input your card name: ");
        String input = inputReader.nextLine().toLowerCase().replaceAll(" ", "");
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        List<Card> cards = cardDatabaseController.searchRepositoryByName(input);
        if (cards.size() == 0) {
          System.out.println("No card found. Please ensure that the name is correct.");

        }
        else cardDatabaseController.printCards(cards);
        setEndTime(command);
      }
      else if (command.equals("clearallrepositories")||command.equals("car")) {
        setStartTime();
        cardDatabaseController.clearAllRepositories();
        System.out.println("All repositories have been cleared.");
        setEndTime(command);
      }
      else if (command.equals("clearprices")||command.equals("cp")) {
        setStartTime();
        cardDatabaseController.clearPrices();
        System.out.println("All prices have been cleared.");
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
        fetcherController.printPageFromURL(input, driver);
        setEndTime(command);
      }
      //Enter file manager (IO)
      else if (command.equals("filemanager")||command.equals("fm")) {
        System.out.println("Now in file manager menu. Enter \\return or \\r to go to the main menu. Press 'help' or 'h' to print all available commands.");
        
        List<File> loadedFiles = new ArrayList<File>();
        
        while(inputReader.hasNext()) {
          System.out.println("Pleaser enter your command.");
          String command2 = inputReader.nextLine();
          //Exit file manager menu
          if (command2.equals("\\return")|command2.equals("\\r")) break;
          //print file manager commands
          else if (command2.equals("help")|command2.equals("h")) printHelpFileManager();
          //Create a list of card ids from scratch
          else if (command2.equals("createnewlist")||command2.equals("cnl")) {
            System.out.println("You will now create a list of cards from scratch. Press \\return or \\r when the list is complete.\nEnter your card name.");
            List<String> userList = new ArrayList<String>();
            while (inputReader.hasNext()) {
              String input = inputReader.nextLine();
              if (input.equals("\\return")|input.equals("\\r")) break;
              System.out.println("Please select the correct card by entering the number before the name of the card. Press 'p' to go to the previous page, 'n' to go to the next page, \\return or \\r to cancel the search.");
              boolean activeSearch = true;
              int pagenb = 1;
              while (activeSearch) {
                String url = FetcherController.generateURL(input, false, false, false, true, pagenb);
                List<WebElement> listOfCards = fetcherController.printPageFromURL(url, driver);
                String cardNumber = inputReader.nextLine();
                if (cardNumber.equals("p")) {
                  if (pagenb>1) pagenb--;
                  else System.out.println("This is already the first page.");
                }
                else if (cardNumber.equals("n")) {
                  pagenb++;
                }
                else if (cardNumber.equals("\\return")|cardNumber.equals("\\r")) {
                  System.out.println("Search is terminated.\nPlease enter your card name.");
                  activeSearch = false;
                }
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
            if (userList.size()>0) fileManager.saveFile(userList,filePath,IDLISTFILEXTENSION);
            setEndTime(command2);

          }
          else if (command2.equals("loadfrompath")||command2.equals("lfp")) {
            System.out.println("Please input your path name.");
            String input = inputReader.nextLine().trim();
            if (input.equals("\\return")|input.equals("\\r")) continue;
            setStartTime();
            loadedFiles.clear();
            fileManager.getFilesFromDirectory(input, loadedFiles);
            setEndTime(command2);
          }
          //Further commands are not authorized until some files are loaded
          else if (loadedFiles.size()<1) System.out.println("Please load some files before proceeding to further commands.");
          
          else if (command2.equals("printfile")||command2.equals("pf")) {
            setStartTime();
            for (File file:loadedFiles) {
              fileManager.printFile(file.getAbsolutePath());
            }
            setEndTime(command2);
          }
          else if (command2.equals("trackfile")||command2.equals("tf")) {
            setStartTime();
            for (File file:loadedFiles) {
              if (fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(IDLISTFILEXTENSION.replaceFirst(".", ""))) {
                fetcherController.fetchCardsByCardId(fileManager.readFile(file), driver);
              }
              else if (fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(NAMELISTFILEXTENSION.replaceFirst(".", ""))){
                fetcherController.fetchCardsByCardName(fileManager.readFile(file), driver);
              }
              
              else System.out.println("Error, unknown extension for file "+ file.getAbsolutePath());
            }
            setEndTime(command2);
          }
        }
      }
      else printHelp();

      System.out.println("Welcome.\nPlease input your command");
    }
    if (driver!=null) driver.close();
  }

  /**
   * Print all available user commands.
   */
  private void printHelp() {
    System.out.println("All commands: \n"
        + "Command\t\t\tAbrievated\tUsage\n"
        + "\nFETCH COMMANDS\n"
        + "fetchallfast\t\tfaf\tFetch all the available price options from a card name search from the first page.\n"
        + "fetchall\t\tfa\tFetch all the available price options from a card name search.\n"
        + "\nREPOSITORY COMMANDS\n"
        + "printrepository\t\tpr\tPrint all the cards in the repository.\n"
        + "searchbyid\t\tsbid\tSearch the repository by id.\n"
        + "searchbyname\t\tsbn\tSearch the repository by name.\n"
        + "clearallrepositories\t\tcar\tClear all repositories.\n"
        + "clearprices\t\tcp\tClear all registered prices.\n"
        + "\nFILES COMMANDS\n"
        + "filemanager\t\tfm\tEnter the file manager menu.\n"
        + "\nOTHER COMMANDS\n"
        + "restartdriver\t\trd\tRestart the web driver. Use it if the web driver hasn't started or if there are some issues with it.\n"
        + "\\return\t\t\t\\r\tGo to previous menu.\n"
        + "quit\t\t\tq\tClose the program.");
  }
  private void printHelpFileManager() {
    System.out.println("All file manager commands: \n"
        + "Command\t\t\tAbrievated\tUsage\n"
        + "createnewlist\t\tcnl\tCreate a list of cards from scratch.\n"
        + "loadfrompath\tlfp\tRead all the files present in the input path (including files in subdirectories).\n"
        + "printfile\t\tpf\tPrint the content of every file from a path.\n"
        + "trackfile\t\ttf\tAdd to the repository the most recent prices from a list of cards.\n"
        + "help\t\t\th\tPrint all the commands available in the file manager menu.\n"
        + "\\return\t\t\t\\r\tGo to main menu."
        + "");
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
        System.out.println("Error when starting up web driver. Please check the error below.\nError message:");
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
