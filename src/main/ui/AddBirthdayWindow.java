package ui;

import model.Birthday;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class AddBirthdayWindow implements ActionListener {
    private JFrame frame;
    private JPanel panel;

    private JTextField nameText;
    private JTextField birthdateText;
    private JTextField interestsText;
    private JTextField giftIdeasText;

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

    private void setUpFrame() {
        frame = new JFrame();
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

    public void makeTextFieldLabels() {
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JLabel birthdateLabel = new JLabel("Birthdate");
        birthdateLabel.setBounds(10, 50, 80, 25);
        panel.add(birthdateLabel);

        JLabel interestsLabel = new JLabel("Interests");
        interestsLabel.setBounds(10, 80, 80, 25);
        panel.add(interestsLabel);

        JLabel giftIdeasLabel = new JLabel("Gift Ideas");
        giftIdeasLabel.setBounds(10, 110, 80, 25);
        panel.add(giftIdeasLabel);
    }

    public void makeTextFields() {
        nameText = new JTextField(25);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        birthdateText = new JTextField(25);
        birthdateText.setBounds(100, 50, 165, 25);
        panel.add(birthdateText);

        interestsText = new JTextField(25);
        interestsText.setBounds(100, 80, 165, 25);
        panel.add(interestsText);

        giftIdeasText = new JTextField(25);
        giftIdeasText.setBounds(100, 110, 165, 25);
        panel.add(giftIdeasText);
    }

    public void makeButtons() {
        JButton addButton = new JButton("Add Birthday");
        addButton.setBounds(10, 140, 150, 25);
        panel.add(addButton);
        addButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameText.getText();

        String date = birthdateText.getText();
        String[] dateArray = date.split("/");
        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[2]);

        String interestsString = interestsText.getText();
        String giftIdeasString = giftIdeasText.getText();
        ArrayList<String> interests = new ArrayList<>(Arrays.asList(interestsString.split("\"\\\\s*,\\\\s*\"")));
        ArrayList<String> giftIdeas = new ArrayList<>(Arrays.asList(giftIdeasString.split("\"\\\\s*,\\\\s*\"")));

        Birthday bday = new Birthday(name, day, month, year, interests, giftIdeas);
        ViewCalendarWindow.addBirthday(bday);
        ViewCalendarWindow.display();

        frame.dispose();
    }

    public static void main(String[] args) {
        new AddBirthdayWindow();
    }
}
