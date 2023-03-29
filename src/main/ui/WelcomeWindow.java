package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the welcome window that first opens when the user starts the program
public class WelcomeWindow implements ActionListener {

    private JFrame frame;
    private ImageIcon catImage;
    private JLabel imageLabel;
    private JLabel questionLabel;
    private JButton loadButton;
    private JButton newButton;
    private JPanel buttonPanel;

    // EFFECTS: display new welcome window with choice to load calendar from file
    public WelcomeWindow() {
        frame = new JFrame();
        frame.setSize(ViewCalendarWindow.WINDOW_WIDTH, ViewCalendarWindow.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center the window on screen

        // Source for cake icon: https://brandeps.com/icon/C/Cake-01
        ImageIcon cakeImage = new ImageIcon("./data/cakeIcon.png");
        frame.setIconImage(cakeImage.getImage());

        JLabel questionLabel = new JLabel("Welcome to your birthday calendar app!");
        frame.add(questionLabel, BorderLayout.NORTH);

        // Source for cat image: https://pin.it/7Coc7Lf
        catImage = new ImageIcon(new ImageIcon("./data/Cat.jpg").getImage()
                .getScaledInstance(250, 250, Image.SCALE_DEFAULT));
        imageLabel = new JLabel();
        imageLabel.setIcon(catImage);
        frame.add(imageLabel, BorderLayout.CENTER, SwingConstants.CENTER);

        addButtons();

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add "Load From File" and "New Calendar" buttons to the window
    private void addButtons() {
        buttonPanel = new JPanel();

        loadButton = new JButton("Load From File");
        loadButton.addActionListener(this);

        newButton = new JButton("New Calendar");
        newButton.addActionListener(this);

        buttonPanel.add(loadButton);
        buttonPanel.add(newButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: create new window to view entire calendar and dispose of this window
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        if (e.getSource() == loadButton) {
            new ViewCalendarWindow(true);
        } else {
            new ViewCalendarWindow(false);
        }
    }

}
