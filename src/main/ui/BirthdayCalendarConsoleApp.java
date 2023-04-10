package ui;

import model.Birthday;
import model.Calendar;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Birthday calendar application
// roughly based on the TellerApp class from https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class BirthdayCalendarConsoleApp {
    private Scanner input;
    private Calendar calendar;
    private static final String SOURCE_FILE_NAME = "./data/calendar.json";
    private boolean unsavedChanges; // keeps track of whether user has unsaved changes

    public BirthdayCalendarConsoleApp() {
        runBirthdayCalendarApp();
    }

    // MODIFIES: this
    // EFFECTS: processes whether app should keep running or quit
    public void runBirthdayCalendarApp() {
        String choice;
        boolean exitApp = false;

        setUp();
        welcomeMessage();
        initializeCalendar();

        while (!exitApp) {
            displayMainMenu();
            choice = input.nextLine();

            if (choice.equals("x")) {
                saveUnsavedChanges();
                exitApp = true;
            } else {
                processMenuChoice(choice);
            }
        }
    }

    // EFFECTS: if user has unsaved changes, and they choose to save them, write calendar to file
    private void saveUnsavedChanges() {
        if (unsavedChanges) {
            System.out.print("You have unsaved changes. Do you want to save them to file before exiting?");
            System.out.print(" Enter (y/n): ");

            if (input.nextLine().equals("y")) {
                saveCalendar();
            } else {
                System.out.println("Your changes have not been saved.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user choice from main menu
    private void processMenuChoice(String choice) {
        switch (choice) {
            case "v":
                viewAllBirthdays();
                break;
            case "a":
                addBirthday();
                break;
            case "l":
                searchBirthdays();
                break;
            case "s":
                saveCalendar();
                break;
            default:
                System.out.println("Your input is invalid.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves calendar to source file
    private void saveCalendar() {
        System.out.println("\n\n\n------------ Save ------------\n");
        JsonWriter writer = new JsonWriter(SOURCE_FILE_NAME);
        try {
            writer.open();
            writer.write(calendar);
            writer.close();
            unsavedChanges = false;
            System.out.print("Your changes have been saved to " + SOURCE_FILE_NAME);
        } catch (IOException e) {
            System.out.print("Calendar could not be saved to " + SOURCE_FILE_NAME);
        }
        System.out.print(". Press enter to continue.");
        input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: searches for birthday with the name the user inputs
    private void searchBirthdays() {
        System.out.println("\n\n\n------------ Search ------------");
        System.out.print("\nEnter a person's name to search for their birthday:");
        String nameToSearch = input.nextLine();
        Birthday foundBirthday = calendar.retrieveBirthday(nameToSearch);
        if (foundBirthday == null) {
            System.out.println("\nYou do not have someone with this name in your birthday calendar.");
        } else {
            viewBirthday(foundBirthday);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a birthday to the calendar using inputted information
    private void addBirthday() {
        System.out.println("\n\n\n---------- Add Birthday ----------");
        System.out.println("\nPlease enter the following information...");
        System.out.print("\tThe person's name:");
        String name = input.nextLine();

        System.out.print("\tTheir birthday in the format mm/dd:");
        int month = input.nextInt();
        int dayNum = input.nextInt();
        input.nextLine(); // clear leftover \n

        int year = inputBirthYear();

        System.out.print("\tTheir interests separated by commas, or enter s to skip this step:");
        ArrayList<String> interests = inputListOfString();

        System.out.print("\tGift ideas for their birthday separated by commas, or enter s to skip this step:");
        ArrayList<String> giftIdeas = inputListOfString();

        calendar.addBirthday(new Birthday(name, month, dayNum, year, interests, giftIdeas));
        unsavedChanges = true;
        System.out.print("\nThe birthday was successfully added! Press Enter to return to main menu.");
        input.nextLine(); // wait for user to press enter
    }

    // EFFECTS: takes input of comma separated strings and processes them into a list of strings
    private ArrayList<String> inputListOfString() {
        String nextLine = input.nextLine();

        // input of "s" means the user wants to skip inputting this information, so return an empty list
        if (nextLine.equals("s")) {
            return new ArrayList<>();
        }

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(nextLine.split("\"\\\\s*,\\\\s*\"")));
        return strings;
    }

    // EFFECTS: collects person's birth year from user and returns it, or returns 0 if user chooses to skip
    private int inputBirthYear() {
        System.out.print("\tTheir birth-year in the format yyyy, or enter s to skip this step:");
        String year = input.nextLine();
        if (year.equals("s")) {
            return 0;
        }
        return Integer.parseInt(year);
    }

    // EFFECTS: prints the name and date for all birthdays to the screen
    private void viewAllBirthdays() {
        System.out.println("\n\n\n---------- All Birthdays ----------\n");
        System.out.println(calendar.birthdaysToString());
        System.out.print("Press Enter to return to main menu.");
        input.nextLine();
    }

    // EFFECTS: prints the main menu to the screen
    public void displayMainMenu() {
        System.out.println("\n\n\n----------- Main Menu -----------\n");
        System.out.println("Please choose one of the options:");
        System.out.println("\t v -> view all birthdays");
        System.out.println("\t a -> add a birthday");
        System.out.println("\t l -> look up a birthday by name");
        System.out.println("\t s -> save calendar to file");
        System.out.println("\t x -> exit app");
        System.out.print("\nEnter your choice here:");
    }

    // MODIFIES: this
    // EFFECTS: displays birthday and provides a choice for the user to delete it
    public void viewBirthday(Birthday b) {
        System.out.println("\n\n\n--------- Viewing Birthday ---------\n");
        displayBirthday(b);
        System.out.print("\nEnter 'd' to delete this birthday, or enter any other key to return to the main menu: ");
        String choice = input.nextLine();
        if (choice.equals("d")) {
            deleteBirthday(b);
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to confirm deletion, and deletes inputted birthday from the calendar if confirmed
    public void deleteBirthday(Birthday b) {
        System.out.print("\nAre you sure you want to delete this birthday? Enter y/n:");
        String choice = input.nextLine();
        if (choice.equals("y")) {
            calendar.deleteBirthday(b.getName());
            System.out.print("This birthday has been deleted. Press enter to return to main menu.");
            input.nextLine();

            unsavedChanges = true;
        } else {
            System.out.print("This birthday will not be deleted. Press enter to return to main menu.");
            input.nextLine();
        }
    }

    // EFFECTS: prints all relevant information about the inputted birthday to the screen
    public void displayBirthday(Birthday b) {
        System.out.println("Name: " + b.getName());
        System.out.println("Birthday: " + b.dateToString());
        System.out.println("Birth year: " + b.yearToString());
        System.out.println("Days until birthday: " + b.daysUntil());
        System.out.println("Age: " + b.ageAsString());
        System.out.println("\nInterests: " + b.interestsToString());
        System.out.println("Gift Ideas: " + b.giftIdeasToString());
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner and sets unsavedChanges to false
    public void setUp() {
        input = new Scanner(System.in);
        input.useDelimiter("/|\\n");

        unsavedChanges = false;
    }

    // MODIFIES: this
    // EFFECTS: loads calendar from source file or creates new blank calendar, depending on user input
    public void initializeCalendar() {
        System.out.println("\n\n\n--------- Setup ---------");
        System.out.print("Would you like to read your calendar from file? Enter y/n: ");

        if (input.nextLine().equals("y")) {
            JsonReader reader = new JsonReader(SOURCE_FILE_NAME);
            try {
                calendar = reader.read();
                System.out.print("\nYour calendar has been loaded from " + SOURCE_FILE_NAME + ".");
                System.out.print(" Press enter to continue to main menu.");
            } catch (IOException e) {
                System.out.print("Your calendar could not be loaded from " + SOURCE_FILE_NAME);
                System.out.print(" Press enter to continue to main menu.");
                calendar = new Calendar();
            }

        } else {
            System.out.print("\nYour calendar will not be loaded from file. Press enter to continue to main menu.");
            calendar = new Calendar();
        }
        input.nextLine();
    }

    // EFFECTS: prints welcome message to the screen
    public void welcomeMessage() {
        System.out.println("Welcome to ... ");

        // cake based on art from https://asciiart.website/index.php?art=events/birthday
        // "bdayz" text was generated using https://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20
        System.out.println("                     (        (");
        System.out.println("                    ( )      ( )          (");
        System.out.println("             (       Y        Y          ( )");
        System.out.println("            ( )     |\"|      |\"|          Y");
        System.out.println("             Y      | |.-----| |---.___  |\"|");
        System.out.println("            |\"|  .--| |,~~~~~| |~~~,,,,'-| |");
        System.out.println("            | |-,,~~'-'      '-'       ~~| |._");
        System.out.println("           .| |~ _        _              '-',,'.");
        System.out.println("          /,'-' | |__  __| |__ _ _  _ ___    ~,\\ ");
        System.out.println("         / ;    | '_ \\/ _` / _` | || /_  /   _; )");
        System.out.println("         | ;    |_.__/\\__,_\\__,_|\\_, /_”_/    ; |");
        System.out.println("         |\\ ~,,,                |__/      ,‘“‘  |");
        System.out.println("         | '-._  ~~ ,,,            ,,,~~ `  _.-'|");
        System.out.println("         |     '-.__   ~~~~~~~~~~~   __.- '`    |");
        System.out.println("         |\\         ` '----------' `           _|");
        System.out.println("           '-._                           __.-'");
        System.out.println("               '-.__ _____________ __.-'");

        System.out.println("\nthe birthday calendar app.");

        System.out.print("\nPress enter to continue.");
        input.nextLine();
    }

    public static void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
        }
    }
}
