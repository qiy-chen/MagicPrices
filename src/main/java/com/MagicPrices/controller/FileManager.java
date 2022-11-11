package com.MagicPrices.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.MagicPrices.model.CardDatabase;
import com.MagicPrices.model.FetcherSystem;
import com.MagicPrices.model.MainMenu;




public class FileManager {
  private FetcherSystem system = MainController.getFetcherSystem();
  private MainMenu menu = MainController.getMainMenu();
  private CardDatabase database = MainController.getCardDatabase();
  public FileManager() {}

  /**
   * Method to save a list of String as a file
   * @param userList - List of String to be saved as a file
   * @param path - directory where the file will be saved
   * @param fileExt - extension of the file that will be created
   * @return - true if success, false if there is an error
   */
  public boolean saveFile(List<String> userList, String path,String fileExt){
    boolean success = false;
    List<String> lines = new ArrayList<String>(userList);
    new File(path).mkdirs();
    String date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString().replaceAll(":", "-");
    Path file = Paths.get(path+date+fileExt);
    try {
      Files.write(file, lines, StandardCharsets.UTF_8);
      System.out.println("File saved to "+path+date+fileExt);
      success = true;
    } catch (IOException e) {
      System.out.println("There was an error when saving the file");
      e.printStackTrace();
      success = false;
      return success;
    }
    return success;
  }

  /**
   * Method to print the content of every file that exists from a directory/file path
   * @param path - path name of the file/directory
   * @return - true if successful, false if there is an error
   */
  public boolean printFile(String path) {
    boolean success = false;
    List<File> listFiles = new ArrayList<File>();
    listFiles = getFilesFromDirectory(path, listFiles);

    //Print content of every file in the list
    for (File file: listFiles) {
      Scanner myReader;
      try {
        myReader = new Scanner(file);
        System.out.println("Content of "+file.getAbsolutePath());
        while (myReader.hasNextLine()) {
          String data = myReader.nextLine();
          System.out.println(data);
          success = true;
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("Error, file at "+file.getAbsolutePath()+" does not exist or can't be read.");
      }
    }
    return success;
  }

  /**
   * Recursive method to return a list of File from a directory path
   * @param path - path name of the directory or file to explore
   * @return - List of File in the directory path
   */
  public List<File> getFilesFromDirectory(String path, List<File> listFiles) {
    try {
      File file = new File(path);
      //Base case
      if (file.isFile()) {
        listFiles.add(file);
        return listFiles;
      }
      //Explore recursively if it is a directory and add the files to a list
      else if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (File f: files) {
          getFilesFromDirectory(f.getAbsolutePath(), listFiles);
        }
      }
    } catch (Exception e) {
      System.out.println("Error, file not found.");
      return listFiles;

    }
    return listFiles;

  }

}
