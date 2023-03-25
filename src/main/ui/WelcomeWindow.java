package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow implements ActionListener {
    // https://pin.it/7Coc7Lf  cat picture

    private JFrame frame;
    private ImageIcon catImage;
    private JLabel imageLabel;
    private JLabel questionLabel;
    private JButton yesButton;
    private JButton noButton;
    private JPanel buttonPanel;

    public WelcomeWindow() {
        frame = new JFrame();
        frame.setSize(ViewCalendarWindow.WINDOW_WIDTH, ViewCalendarWindow.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center the window on screen

        JLabel questionLabel = new JLabel("Welcome to your birthday calendar app!");
        frame.add(questionLabel, BorderLayout.NORTH);

        catImage = new ImageIcon(new ImageIcon("./data/Cat.jpg").getImage()
                .getScaledInstance(250, 250, Image.SCALE_DEFAULT));
        imageLabel = new JLabel();
        imageLabel.setIcon(catImage);
        frame.add(imageLabel, BorderLayout.CENTER);

        addButtons();

        frame.setVisible(true);
    }

    private void addButtons() {
        questionLabel = new JLabel("Would you like to load your calendar from file?");
        buttonPanel = new JPanel();
        buttonPanel.add(questionLabel);

        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);

        noButton = new JButton("No");
        noButton.addActionListener(this);

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new WelcomeWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        if (e.getSource() == yesButton) {
            new ViewCalendarWindow(true);
        } else {
            new ViewCalendarWindow(false);
        }
    }
}
