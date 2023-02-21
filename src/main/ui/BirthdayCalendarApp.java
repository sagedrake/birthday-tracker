package ui;

import model.Birthday;
import model.Calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BirthdayCalendarApp {
    private Scanner input;
    private Calendar calendar;

    // MODIFIES: this
    // EFFECTS: processes whether app should keep running or quit
    public void runBirthdayCalendarApp() {
        String choice;
        Boolean exitApp = false;

        setUp();
        input.nextLine(); // scan over leftover \n

        while (!exitApp) {
            displayMainMenu();
            choice = input.nextLine();

            if (choice.equals("x")) {
                exitApp = true;
            } else {
                processMenuChoice(choice);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user choice from main menu
    private void processMenuChoice(String choice) {
        if (choice.equals("v")) {
            viewAllBirthdays();
        } else if (choice.equals("a")) {
            addBirthday();
        } else if (choice.equals("s")) {
            searchBirthdays();
        } else {
            System.out.println("Your input is invalid.");
        }
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

        calendar.addBirthday(name, month, dayNum, year, interests, giftIdeas);
        System.out.print("\nThe birthday was successfully added! Press Enter to return to main menu.");
        input.nextLine(); // wait for user to press enter
    }

    // EFFECTS: takes input of comma separated strings and processes them into a list of strings
    private ArrayList<String> inputListOfString() {
        String nextLine = input.nextLine();
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(nextLine.split("\"\\\\s*,\\\\s*\"")));
        return strings;
    }

    // EFFECTS: collects person's birth year from user and returns it, or returns -1 if user chooses to skip
    private int inputBirthYear() {
        System.out.print("\tTheir birth-year in the format yyyy, or enter s to skip this step:");
        String year = input.nextLine();
        if (year.equals("s")) {
            return -1;
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
        System.out.println("\t s -> search for a birthday");
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
        } else {
            System.out.print("This birthday will not be deleted. Press enter to return to main menu.");
            input.nextLine();
        }
    }

    // EFFECTS: prints all relevant information about the inputted birthday to the screen
    public void displayBirthday(Birthday b) {
        System.out.println("Name: " + b.getName());
        System.out.println("Birthday: " + b.dateToString());
        System.out.println("Birth Year: " + b.yearToString());
        System.out.println("Interests: " + b.interestsToString());
        System.out.println("Gift Ideas: " + b.giftIdeasToString());
    }

    // MODIFIES: this
    // EFFECTS: sets up scanner and user's birthday
    public void setUp() {
        input = new Scanner(System.in);
        System.out.println("Welcome to your birthday calendar :)\n\n");
        System.out.print("\nPlease enter your birthday in the format mm/dd:");
        input.useDelimiter("/|\\n");
        int month = input.nextInt();
        int dayNum = input.nextInt();
        calendar = new Calendar(new Birthday("You", month, dayNum, -1,
                new ArrayList<>(), new ArrayList<>()));
    }
}
