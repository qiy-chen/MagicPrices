package com.MagicPrices.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;




public class FileManager {
  private static FetcherSystem system = MainController.getFetcherSystem();
  private static MainMenu menu = MainController.getMainMenu();
  private static CardDatabase database = MainController.getCardDatabase();
  public FileManager() {}
  
  public static boolean createIdListFromScratch(List<String> userList, String path){
    boolean success = false;
    List<String> lines = new ArrayList<String>(userList);
    Path file = Paths.get(path+"userCreatedList");
    try {
      Files.write(file, lines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.out.println("There was an error when saving the file");
      e.printStackTrace();
      success = false;
      return success;
    }
    return success;
  }
}
