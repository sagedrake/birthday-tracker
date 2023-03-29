package ui;

import model.Birthday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

// represents window where user can input information about a birthday and add this birthday to their calendar
public class AddBirthdayWindow implements ActionListener {
    private JFrame frame;
    private JPanel panel;

    private JTextField nameText;
    private JTextField birthdateText;
    private JTextField yearText;
    private JTextField interestsText;
    private JTextField giftIdeasText;

    // MODIFIES: this
    // EFFECTS: create a new window with fields for entering information about a birthday, and a button
    //           to add the birthday to the user's calendar
    public AddBirthdayWindow() {
        setUpFrame();

        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        makeTextFieldLabels();
        makeTextFields();
        makeButtons();

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame for the window, including its size and location on screen
    private void setUpFrame() {
        frame = new JFrame();
        frame.setSize(ViewCalendarWindow.WINDOW_WIDTH, ViewCalendarWindow.WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // center the window on screen

        ImageIcon cakeImage = new ImageIcon("./data/cakeIcon.png");
        frame.setIconImage(cakeImage.getImage());

        frame.addWindowListener(new WindowAdapter() {

            // MODIFIES: this
            // EFFECTS: When user presses the red X to close the window, display message to ask if they want to save
            //              their calendar.
            //          If they choose Yes, save calendar to file and exit program
            //          If they choose No, just exit the program (don't save)
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save your calendar to file ?", "Save calendar : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    ViewCalendarWindow.saveCalendarToFile();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create the labels for the text fields so the user knows what information to enter where
    private void makeTextFieldLabels() {
        JLabel nameLabel = new JLabel("Name*");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JLabel birthdateLabel = new JLabel("Birthdate*");
        birthdateLabel.setBounds(10, 50, 80, 25);
        panel.add(birthdateLabel);

        JLabel yearLabel = new JLabel("Birth year");
        yearLabel.setBounds(10, 80, 80, 25);
        panel.add(yearLabel);

        JLabel interestsLabel = new JLabel("Interests");
        interestsLabel.setBounds(10, 110, 80, 25);
        panel.add(interestsLabel);

        JLabel giftIdeasLabel = new JLabel("Gift Ideas");
        giftIdeasLabel.setBounds(10, 140, 80, 25);
        panel.add(giftIdeasLabel);
    }

    // MODIFIES: this
    // EFFECTS: Create the text fields where the user can enter the person's name, birthdate, birth year,
    //          interests, and gift ideas
    private void makeTextFields() {
        nameText = new JTextField(25);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        birthdateText = new JTextField(25);
        birthdateText.setBounds(100, 50, 165, 25);
        panel.add(birthdateText);

        yearText = new JTextField(25);
        yearText.setBounds(100, 80, 165, 25);
        panel.add(yearText);

        interestsText = new JTextField(25);
        interestsText.setBounds(100, 110, 165, 25);
        panel.add(interestsText);

        giftIdeasText = new JTextField(25);
        giftIdeasText.setBounds(100, 140, 165, 25);
        panel.add(giftIdeasText);
    }

    // MODIFIES: this
    // EFFECTS: Create button at the bottom of the window to add the birthday to calendar
    private void makeButtons() {
        JButton addButton = new JButton("Add Birthday");
        addButton.setBounds(10, 170, 150, 25);
        panel.add(addButton);
        addButton.addActionListener(this);
    }

    // REQUIRES: birthday was entered in format mm/dd, year was inputted in format yyyy or no year was inputted,
    //              interests and gift ideas were either not inputted or were inputted separated by commas,
    //              and name is different from names of birthdays already in calendar
    // MODIFIES: this, ViewCalendarWindow
    // EFFECTS: When "Add birthday" button is pressed, retrieve information from text fields and add the birthday with
    //              this information to the user's calendar.
    //          Then display the view calendar window and dispose of this window.
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameText.getText();

        String date = birthdateText.getText();
        String[] dateArray = date.split("/");

        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);

        int year;
        // if no year is entered, represent to year as 0 to indicate an unknown year
        if (yearText.getText().equals("")) {
            year = 0;
        } else {
            year = Integer.parseInt(yearText.getText());
        }

        String interestsString = interestsText.getText();
        String giftIdeasString = giftIdeasText.getText();
        ArrayList<String> interests = getStrings(interestsString);
        ArrayList<String> giftIdeas = getStrings(giftIdeasString);

        Birthday bday = new Birthday(name, day, month, year, interests, giftIdeas);
        ViewCalendarWindow.addBirthday(bday);
        ViewCalendarWindow.display();

        frame.dispose();
    }

    // REQUIRES: stringOfStrings is a comma-separated sequence of strings (e.g. "apple,carrot, banana , berry")
    // EFFECTS: If stringOfStrings is "", return an empty list
    //          Otherwise, return a list of all the words that were separated by commas in stringOfStrings
    private ArrayList<String> getStrings(String stringOfStrings) {
        ArrayList<String> strings;
        if (stringOfStrings.equals("")) {
            strings = new ArrayList<>();
        } else {
            strings = new ArrayList<>(Arrays.asList(stringOfStrings.split("\"\\\\s*,\\\\s*\"")));
        }
        return strings;
    }

//    public static void main(String[] args) {
//        new AddBirthdayWindow();
//    }
}
