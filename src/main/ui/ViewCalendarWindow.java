package ui;

import model.Birthday;
import model.Calendar;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents main window where user can see a list of all birthdays in their calendar
public class ViewCalendarWindow implements ListSelectionListener {
    private static JFrame frame;
    private static Calendar calendar;
    private static final String SOURCE_FILE_NAME = "./data/calendar.json";

    private static JList list;
    private static DefaultListModel listModel;

    private JButton addButton;

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 400;

    // EFFECTS: Create new window displaying all birthdays in the user's calendar.
    //          If readFromFile is true, load the calendar from the source file
    //          Otherwise, create a new empty calendar
    public ViewCalendarWindow(boolean readFromFile) {
        if (readFromFile) {
            JsonReader reader = new JsonReader(SOURCE_FILE_NAME);
            try {
                calendar = reader.read();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame,
                        "Your calendar could not be loaded from file.",
                        "Loading error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            calendar = new Calendar();
        }
        setUpFrame();
        setUpBirthdayList();
        setUpButtons();
        frame.setVisible(true);
    }

    // REQUIRES: there is no pre-existing birthday with the same name as b in calendar
    // MODIFIES: this
    // EFFECTS: add given birthday to calendar
    public static void addBirthday(Birthday b) {
        calendar.addBirthday(b);
        listModel.addElement(b);
    }

    // MODIFIES: this
    // EFFECTS: set this window to be visible to the user
    public static void display() {
        frame.setVisible(true);
    }

    // REQUIRES: birthday is already in calendar
    // MODIFIES: this
    // EFFECTS: delete the given birthday from calendar
    public static void deleteBirthday(Birthday birthday) {
        list.clearSelection();
        listModel.removeElement(birthday);
        calendar.deleteBirthday(birthday.getName());
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame for the window, including its size and location on screen
    private void setUpFrame() {
        frame = new JFrame();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
                    saveCalendarToFile();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add list of birthdays (only display name and date) to the window with a scroll bar
    private void setUpBirthdayList() {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        for (Birthday b : calendar.getBirthdays()) {
            listModel.addElement(b);
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);

        JScrollPane listScrollPane = new JScrollPane(list);
        frame.add(listScrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: save calendar to source file; display error message if saving is unsuccessful
    public static void saveCalendarToFile() {
        JsonWriter writer = new JsonWriter(SOURCE_FILE_NAME);
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame,
                    "Your calendar could not be saved.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        writer.write(calendar);
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Create "Add new birthday" button at bottom of window
    private void setUpButtons() {
        addButton = new JButton("Add Birthday");

        ButtonListener listener = new ButtonListener();
        addButton.addActionListener(listener);

        frame.add(addButton, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: When the selected birthday changes:
    //          If there is a birthday selected, create a new window to display information about the birthday
    //              and change this window to not be visible
    //          Otherwise, do nothing
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // if list is -1 that means no value is selected
        if (list.getSelectedIndex() != -1) {
            frame.setVisible(false);
            new DisplayBirthdayWindow((Birthday) list.getSelectedValue());
            list.clearSelection();
        }
    }

    // represents observer that is notified when the add birthday button is pressed
    class ButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: set this window to not be visible, and create a new add birthday window
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new AddBirthdayWindow();
        }
    }

//    public static void main(String[] args) {
//        new ViewCalendarWindow(true);
//    }
}
