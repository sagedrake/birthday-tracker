package ui;

import model.Birthday;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

// represents a window that displays detailed information about one particular birthday
public class DisplayBirthdayWindow implements ActionListener {
    Birthday birthday;
    private JFrame frame;
    private JPanel infoPanel;

    private JButton deleteButton;
    private JButton mainMenuButton;

    // EFFECTS: create new window to display information about the given birthday
    public DisplayBirthdayWindow(Birthday birthday) {
        this.birthday = birthday;

        setUpFrame();
        displayBirthdayInformation();
        setUpButtons();

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame for the window, including its size and location on screen
    private void setUpFrame() {
        frame = new JFrame();
        frame.setTitle("View Birthday");
        frame.setSize(ViewCalendarWindow.WINDOW_WIDTH, ViewCalendarWindow.WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // center the window on screen
        frame.getContentPane().setBackground(Color.WHITE);

        ImageIcon cakeImage = new ImageIcon("./data/cakeIcon.png");
        frame.setIconImage(cakeImage.getImage());

        frame.addWindowListener(new WindowAdapter() {

            // MODIFIES: this, ViewCalendarWindow
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
    // EFFECTS: creates and adds "Delete" and "Main Menu" buttons to the window
    private void setUpButtons() {
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        frame.add(deleteButton, BorderLayout.SOUTH);

        // Source: screenshot of the Google Chrome back button
        ImageIcon backIcon = new ImageIcon("./data/backIcon.png");
        mainMenuButton = new JButton(backIcon);
        mainMenuButton.addActionListener(this);
        frame.add(mainMenuButton, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds information about the birthday to the window
    private void displayBirthdayInformation() {
        JLabel nameLabel = new JLabel("Name: " + birthday.getName());
        JLabel birthdateLabel = new JLabel("Birthdate: " + birthday.dateToString());
        JLabel ageLabel = new JLabel("Age: " + birthday.ageAsString());
        JLabel daysUntilBirthdayLabel = new JLabel("Days until birthday: " + birthday.daysUntil());
        JLabel interestsLabel = new JLabel("Interests: " + birthday.interestsToString());
        JLabel giftIdeasLabel = new JLabel("Gift ideas: " + birthday.giftIdeasToString());

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(birthdateLabel);
        infoPanel.add(ageLabel);
        infoPanel.add(daysUntilBirthdayLabel);
        infoPanel.add(interestsLabel);
        infoPanel.add(giftIdeasLabel);

        frame.add(infoPanel);
    }

    // MODIFIES: this, ViewCalendarWindow
    // EFFECTS: If the delete button was pressed, delete the birthday if the user confirms the deletion
    //                or do nothing if the user does not confirm.
    //          Otherwise, if the main menu button was pressed, display the calendar window and dispose of
    //                this window.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this birthday?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
                ViewCalendarWindow.deleteBirthday(birthday);
                ViewCalendarWindow.display();
            }
        } else if (e.getSource() == mainMenuButton) {
            frame.dispose();
            ViewCalendarWindow.display();
        }
    }
}
