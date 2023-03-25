package ui;

import model.Birthday;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DisplayBirthdayWindow implements ActionListener {
    Birthday birthday;
    private JFrame frame;
    private JPanel infoPanel;

    private JButton deleteButton;
    private JButton mainMenuButton;

    public DisplayBirthdayWindow(Birthday birthday) {
        this.birthday = birthday;

        setUpFrame();
        displayBirthdayInformation();
        setUpButtons();

        frame.setVisible(true);
    }

    private void setUpFrame() {
        frame = new JFrame();
        frame.setTitle("View Birthday");
        frame.setSize(ViewCalendarWindow.WINDOW_WIDTH, ViewCalendarWindow.WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // center the window on screen

        frame.addWindowListener(new WindowAdapter() {
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

    private void setUpButtons() {
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        frame.add(deleteButton, BorderLayout.SOUTH);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(this);
        frame.add(mainMenuButton, BorderLayout.NORTH);
    }

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

    public static void main(String[] args) {
        ArrayList<String> snoopyInterests = new ArrayList<>();
        snoopyInterests.add("Reading");
        snoopyInterests.add("Dancing");
        snoopyInterests.add("Sleeping");
        ArrayList<String> snoopyGiftIdeas = new ArrayList<>();
        snoopyGiftIdeas.add("War and Peace");
        snoopyGiftIdeas.add("Root beer");
        Birthday testBirthday = new Birthday("Snoopy", 8, 10, 1950, snoopyInterests, snoopyGiftIdeas);

        new DisplayBirthdayWindow(testBirthday);
    }

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
