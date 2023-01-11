# MagicPrices

Magic Prices Fetcher for Face To Face Games.

Direct follow up to my previous project, MTG-TCG-Prices, written in Java instead of Python.

## Requirements

 * PostgreSQL
 
 * Java
 
 Note: To use Safari, remote automation must be enabled.
 
 ## Instructions on the installation of PostgreSQL
 
 PostgreSQL can be downloaded from https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
 
 During the installation, everything can be kept to default.
 
 The password database superuser must be set to 'password' for the default value. If you wish to use a different password, you will need to change the value of 'spring.datasource.password=password' in src/main/resources/application.properties to the password you want.
 
 After the installation, make sure to open the terminal or cmd and type the command:
 
 psql -U postgres
 
 The password will be the one you set up during the installation.
 
 After that, create the database that will hold the repositories of this application by typing this command:

CREATE DATABASE magicprices;

You can exit psql by typing this command:

\q

## Starting the application

If downloaded directly from the GitHub repository, the application can be run by typing this command from the project's folder:

./gradlew bootRun

## Commands

### Main Menu

| Command | Shortcut | Usage |
| ------------- | --- | --- |
| FETCH COMMANDS |
| fetchallfast | faf | Fetch all the available price options from a card name search in the first page. |
| fetchall  | fa  | Fetch all the available price options from a card name search in all search pages. |
| REPOSITORY COMMANDS |
| printrepository  | pr  | Print all the cards in the repository. |
| searchbyid  | sbid  | Search the repository by id. |
| searchbyname  | sbn  | Search the repository by name. |
| clearallrepositories  | car  | Clear all repositories. |
| clearprices  | cp  | Clear all registered prices. |
| clearpricescard  | cpc  | Clear all prices of a card. |
| FILES COMMAND|
| filemanager  | fm  | Enter the file manager menu. |
| OTHER COMMANDS |
| restartdriver  | rd  | Restart the web driver. Use it if the web driver hasn't started or if there are some issues with it. |
| \return  | \r  | Go to the previous menu or abort operation. |
| quit  | q  | Close the program. |

### File Manager Menu

| Command | Shortcut | Usage |
| ------------- | --- | --- |
| createnewlist | cnl | Create a list of cards from scratch. |
| loadfrompath  | lfp  | Load all the files present in the input path (including files in subdirectories). |
| printfile  | pf  | Print the content of every loaded file. |
| trackfile  | tf  | Add to the repository the most recent prices found online from cards in loaded .idlist files. |
| printrecentprices  | prp  | Print the most recent prices listed by loaded .idlist files. |
| printspecificprices  | psp  | Print all the prices listed by loaded .idlist files bounded between two periods of time (included). |
| outputrecentprices  | orp  | Generate an easy to read csv file containing information on the most recent prices listed by loaded .idlist files. |
| addstatusfiles  | asf  | Append at the end of each loaded .idlist files the selected condition and foiling status of the card if not present previously. |
| convertfile  | cf  | Attempt to convert each loaded file into a .idlist file. |
| help  | h  | Print all the commands available in the file manager menu. |
| \return  | \r  | Go to the main menu. |
