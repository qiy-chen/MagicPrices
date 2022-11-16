package com.MagicPrices.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.MagicPrices.model.Card;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;
import com.MagicPrices.model.Price;
import com.MagicPrices.repository.CardRepository;
import com.MagicPrices.repository.PriceRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        System.out.println("Press 'yes' to confirm.");
        String input = inputReader.nextLine();
        if (!input.equals("yes")) {
          System.out.println("Command aborted.");
          continue;
        }
        setStartTime();
        cardDatabaseController.clearAllRepositories();
        System.out.println("All repositories have been cleared.");
        setEndTime(command);
      }
      else if (command.equals("clearprices")||command.equals("cp")) {
        System.out.println("Press 'yes' to confirm.");
        String input = inputReader.nextLine();
        if (!input.equals("yes")) {
          System.out.println("Command aborted.");
          continue;
        }
        setStartTime();
        cardDatabaseController.clearPrices();
        System.out.println("All prices have been cleared.");
        setEndTime(command);
      }
      else if (command.equals("clearpricescard")||command.equals("cpc")) {
        System.out.println("Please input your card id: ");
        String input = inputReader.nextLine().toLowerCase().replaceAll(" ", "");
        if (input.equals("\\return")|input.equals("\\r")) continue;
        setStartTime();
        cardDatabaseController.clearPricesOfCard(input);
        System.out.println("All prices of "+input+" have been cleared.");
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
            System.out.println("Please input your path name. You can also drag and drop the file/directory into the console.");
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

          else if (command2.equals("printrecentprices")||command2.equals("prp")) {
            boolean success = false;
            String condition = "";
            String foiling = "";
            while (!success) {
              System.out.println("Please input your default pricing options as this format: [condition],[foiling]");
              String input = inputReader.nextLine().trim();
              if (input.equals("\\return")|input.equals("\\r")) break;
              if (StringUtils.countMatches(input, ",")!=1) System.out.println("Wrong input.");
              else {
                try {
                  String[] separated = input.split(",");
                  condition = separated[0];
                  foiling = separated[1];
                  success = true;
                }
                catch(Exception e) {
                  System.out.println("Some inputs are empty.");
                }
              }
            }
            setStartTime();
            for (File file:loadedFiles) {
              if (!fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(IDLISTFILEXTENSION.replaceFirst(".", ""))){
                System.out.println("Wrong file extension for "+file.getAbsolutePath());
                continue;
              }
              //Convert list of cardIds into Cards found in the repository
              List<String> listId = fileManager.readFile(file);
              List<Card> listCard = new ArrayList<Card>();
              List<String> conditionList= new ArrayList<String>();
              List<String> foilingList = new ArrayList<String>();
              for (String cardIdRaw:listId) {

                List<String> separatedId = splitCardId(cardIdRaw,condition,foiling);
                //System.out.println(separatedId.get(0));
                Card card = cardDatabaseController.searchRepositoryById(separatedId.get(0));
                if (card!=null) {
                  listCard.add(card);
                  conditionList.add(separatedId.get(1));
                  foilingList.add(separatedId.get(2));
                }
              }

              List<Card> listCards = cardDatabaseController.getCardsPricesMostRecent(listCard, conditionList, foilingList);
              cardDatabaseController.printCards(listCards);
            }
            setEndTime(command2);
          }
          else if (command2.equals("printspecificprices")||command2.equals("psp")) {
            boolean successPricing = false;
            boolean successDates = false;
            String condition = "";
            String foiling = "";
            LocalDateTime oldestDate = null;
            LocalDateTime newestDate = null;
            while (!successDates) {
              System.out.println("Please input your date boundaries (from,to) as this format: [YYYY/MM/DD],[YYYY/MM/DD]");
              String input = inputReader.nextLine().trim();
              if (StringUtils.countMatches(input, ",")!=1) System.out.println("Wrong number of commas.");
              else {
                try{
                  String[] separated = input.split(",");
                  String oldestDateRaw = separated[0];
                  String newestDateRaw = separated[1];
                  int year = 0;
                  int month = 0;
                  int day = 0;
                  String[] separatedDateParameters = oldestDateRaw.split("/");
                  year = Integer.parseInt(separatedDateParameters[0]);
                  month = Integer.parseInt(separatedDateParameters[1]);
                  day = Integer.parseInt(separatedDateParameters[2]);
                  oldestDate = LocalDateTime.of(year, month, day, 0, 0, 0);

                  separatedDateParameters = newestDateRaw.split("/");
                  year = Integer.parseInt(separatedDateParameters[0]);
                  month = Integer.parseInt(separatedDateParameters[1]);
                  day = Integer.parseInt(separatedDateParameters[2]);
                  newestDate = LocalDateTime.of(year, month, day, 0, 0, 0);
                  successDates = true;
                }
                catch (Exception e) {
                  System.out.println("Error, wrong date format. Please try again.");
                }
              }
            }
            while (!successPricing) {
              System.out.println("Please input your default pricing options as this format: [condition],[foiling]");
              String input = inputReader.nextLine().trim();
              if (StringUtils.countMatches(input, ",")!=1) System.out.println("Wrong number of commas.");
              else {
                try {
                  String[] separated = input.split(",");
                  condition = separated[0];
                  foiling = separated[1];
                  successPricing = true;
                }
                catch(Exception e) {
                  System.out.println("Some inputs are empty.");
                }
              }
            }
            setStartTime();
            for (File file:loadedFiles) {
              //Do not proceed if not correct extension
              if (!fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(IDLISTFILEXTENSION.replaceFirst(".", ""))){
                System.out.println("Wrong file extension for "+file.getAbsolutePath());
                continue;
              }
              //Convert list of cardIds into Cards found in the repository
              List<String> listId = fileManager.readFile(file);
              List<Card> listCard = new ArrayList<Card>();
              List<String> conditionList= new ArrayList<String>();
              List<String> foilingList = new ArrayList<String>();
              for (String cardIdRaw:listId) {
                List<String> separatedId = splitCardId(cardIdRaw,condition,foiling);

                Card card = cardDatabaseController.searchRepositoryById(separatedId.get(0));
                if (card!=null) {
                  listCard.add(card);
                  conditionList.add(separatedId.get(1));
                  foilingList.add(separatedId.get(2));
                }
              }

              List<Card> listCards = cardDatabaseController.getCardsPricesSpecific(listCard, oldestDate, newestDate,conditionList, foilingList);
              cardDatabaseController.printCards(listCards);
            }
            setEndTime(command2);
          }
          else if (command2.equals("outputrecentprices")||command2.equals("orp")) {
            boolean success = false;
            String condition = "";
            String foiling = "";
            while (!success) {
              System.out.println("Please input your default pricing options as this format: [condition],[foiling]");
              String input = inputReader.nextLine().trim();
              if (input.equals("\\return")|input.equals("\\r")) break;
              if (StringUtils.countMatches(input, ",")!=1) System.out.println("Wrong input.");
              else {
                try {
                  String[] separated = input.split(",");
                  condition = separated[0];
                  foiling = separated[1];
                  success = true;
                }
                catch(Exception e) {
                  System.out.println("Some inputs are empty.");
                }
              }
            }
            setStartTime();
            for (File file:loadedFiles) {
              if (!fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(IDLISTFILEXTENSION.replaceFirst(".", ""))){
                System.out.println("Wrong file extension for "+file.getAbsolutePath());
                continue;
              }
              //Convert list of cardIds into Cards found in the repository
              List<String> listId = fileManager.readFile(file);
              List<Card> listCard = new ArrayList<Card>();
              List<String> conditionList= new ArrayList<String>();
              List<String> foilingList = new ArrayList<String>();
              for (String cardIdRaw:listId) {

                List<String> separatedId = splitCardId(cardIdRaw,condition,foiling);
                //System.out.println(separatedId.get(0));
                Card card = cardDatabaseController.searchRepositoryById(separatedId.get(0));
                if (card!=null) {
                  listCard.add(card);
                  conditionList.add(separatedId.get(1));
                  foilingList.add(separatedId.get(2));
                }
              }

              List<Card> listCards = cardDatabaseController.getCardsPricesMostRecent(listCard, conditionList, foilingList);

              List<String> listCardsAppendedPrice = new ArrayList<String>();
              listCardsAppendedPrice.add("Generated at "+LocalDateTime.now());
              double totalPrice = 0;
              int nbCards = listCards.size();
              for (Card c: listCards) {
                Price cardPrice = c.getPrice(0);
                String nameAndSet = c.getName()+"|"+c.getCategory();
                int nbTabs = 7-(nameAndSet.length())/8;
                //Prevent negative number of tabs
                if (nbTabs<0) nbTabs = 0;
                String tabs = new String(new char[nbTabs]).replace("\0", "\t");
                listCardsAppendedPrice.add(nameAndSet+tabs+"Price: "+cardPrice.getAmount()+"\t\t"+"Stock: "+cardPrice.getAmountInStock()+"\t"+cardPrice.getCondition()+"|"+cardPrice.getFoiling());
                totalPrice+=c.getPrice(0).getAmount();
              }
              totalPrice = Math.round(totalPrice * 100);
              totalPrice = totalPrice/100;
              listCardsAppendedPrice.add("Total :"+totalPrice+"CAD"+"\tFor "+nbCards+" cards");
              fileManager.saveFile(listCardsAppendedPrice, filePath, ".txt");
            }
            setEndTime(command2);
          }
          else if (command2.equals("addstatusfiles")||command2.equals("asf")) {
            String condition = "";
            String foiling = null;
            boolean successPricing = false;
            while (!successPricing) {
              System.out.println("Please input your default pricing options as this format: [condition],[foiling].");
              String input = inputReader.nextLine().trim();
              if (StringUtils.countMatches(input, ",")!=1) System.out.println("Wrong number of commas.");
              else {
                String[] separated = input.split(",");
                condition = separated[0];
                foiling = separated[1];
                successPricing = true;
              }
            }
            for (File file: loadedFiles) {
              //Do not proceed if not correct extension
              if (!fileManager.getExtensionByApacheCommonLib(file.getAbsolutePath()).equals(IDLISTFILEXTENSION.replaceFirst(".", ""))){
                System.out.println("Wrong gile extension for "+file.getAbsolutePath());
                continue;
              }
              String path = file.getAbsolutePath();
              List<String> oldLines = fileManager.readFile(file);
              List<String> newLines = new ArrayList<String>();
              for (String l : oldLines) {
                if (StringUtils.countMatches(l, "/")==0) {
                  newLines.add(l+"/"+condition+"/"+foiling);
                }
                else {
                  newLines.add(l);
                }
              }
              fileManager.saveFile(newLines, path);
            }
            System.out.println("Successfully added the default condition and foiling status at the end of each ids.");
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
        driver = WebDriverManager.chromedriver().create();
      }
      catch (Exception e){
        try {
          driver = WebDriverManager.safaridriver().create();
          
        }
        catch (Exception e1){
          try {
            driver = WebDriverManager.firefoxdriver().create();
          }
          catch (Exception e2){
            System.out.println("Error when starting up web driver. Please check the error below.\nError message:");
            System.out.println(e2);
          }
        }
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

  /**
   * Convert the raw id stored in .idlist files into a list of size 3, cardId at index 0, condition at index 1 and foiling at index 2
   * @param cardIdRaw - id found in a .idlist file
   * @param defaultCondition - apply this condition when no condition is found inside the raw id
   * @param defaultFoiling - apply this foiling when no foiling is found inside the raw id
   * @return - List of String with cardId at index 0, condition at index 1 and foiling at index 2
   */
  public static List<String> splitCardId(String cardIdRaw, String defaultCondition, String defaultFoiling) {
    List<String> idConditionFoiling = new ArrayList<String>();
    if (StringUtils.countMatches(cardIdRaw, "/")==0) {
      idConditionFoiling.add(cardIdRaw);
      idConditionFoiling.add(defaultCondition);
      idConditionFoiling.add(defaultFoiling);
      return idConditionFoiling;
    }
    else if (StringUtils.countMatches(cardIdRaw, "/")==2) {
      String[] components = cardIdRaw.split("/");
      idConditionFoiling.add(components[0]);
      idConditionFoiling.add(components[1]);
      idConditionFoiling.add(components[2]);
      return idConditionFoiling;
    }
    else {
      System.out.println("Error, could not read raw card id "+cardIdRaw+": wrong format.");
      idConditionFoiling.add(cardIdRaw);
      idConditionFoiling.add(defaultCondition);
      idConditionFoiling.add(defaultFoiling);
      return idConditionFoiling;
    }
  }

}
