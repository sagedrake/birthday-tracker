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

public class ViewCalendarWindow implements ListSelectionListener {
    private static JFrame frame;
    private static Calendar calendar;
    private static final String SOURCE_FILE_NAME = "./data/calendar.json";

    private static JList list;
    private static DefaultListModel listModel;

    private JButton addButton;

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 400;

    public ViewCalendarWindow(boolean readFromFile) {
        if (readFromFile) {
            JsonReader reader = new JsonReader(SOURCE_FILE_NAME);
            try {
                calendar = reader.read();
            } catch (IOException e) {
                System.out.println("Your calendar could not be loaded from file"); // TODO
            }
        } else {
            calendar = new Calendar();
        }
        setUpFrame();
        setUpBirthdayList();
        setUpButtons();
        frame.setVisible(true);
    }

    public static void addBirthday(Birthday b) {
        calendar.addBirthday(b);
        listModel.addElement(b);
    }

    public static void display() {
        frame.setVisible(true);
    }

    public static void deleteBirthday(Birthday birthday) {
        list.clearSelection();
        listModel.removeElement(birthday);
        calendar.deleteBirthday(birthday.getName());
    }

    public void setUpFrame() {
        frame = new JFrame();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // center the window on screen

        frame.addWindowListener(new WindowAdapter() {
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

    private void setUpButtons() {
        addButton = new JButton("Add new birthday");

        ButtonListener listener = new ButtonListener();
        addButton.addActionListener(listener);

        frame.add(addButton, BorderLayout.SOUTH);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // if list is -1 that means no value is selected
        if (list.getSelectedIndex() != -1) {
            frame.setVisible(false);
            new DisplayBirthdayWindow((Birthday) list.getSelectedValue());
            list.clearSelection();
        }
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                frame.setVisible(false);
                new AddBirthdayWindow();
            }
        }
    }

    public static void main(String[] args) {
        new ViewCalendarWindow(true);
    }
}
