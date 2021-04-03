package ui;

import model.Account;
import persistence.JsonAccountReader;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

//class that creates welcome page that contains create account and login options
public class WelcomePage extends JPanel implements ActionListener, Page {
    MainApp app;
    JButton createAccButton = new JButton("Create Account");
    JButton loginButton = new JButton("Login");
    private AudioInputStream audioInputStream = AudioSystem
            .getAudioInputStream(new File("./data/ch-ching.wav").getAbsoluteFile());
    private Clip clip = AudioSystem.getClip();

    // EFFECTS: constructor that creates welcome page, adds component listener and audio clip
    public WelcomePage(MainApp app) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.app = app;

        try {
            clip.open(audioInputStream);
        } catch (Exception exception) {
            System.out.println(exception);
        }

        createPage();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                removeAll();

                createPage();

                revalidate();
                repaint();
            }
        });

        createAccButton.addActionListener(this);
        loginButton.addActionListener(this);
    }

    //MODIFY: this
    //EFFECTS: creates welcome page with logo and create account/login button
    @Override
    public void createPage() {
        try {
            ImageIcon imageIcon = new ImageIcon("./data/logo.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance(400, 180,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newimg);

            JLabel picLabel = new JLabel(imageIcon);

            add(picLabel);
        } catch (Exception e) {
            System.out.println(e);
        }

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(new JLabel("Our mission is to create an inclusive economy"));
        add(new JLabel("by helping you send, receive, and spend money easier"));

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(createAccButton);
        add(loginButton);

        setOpaque(false);

        try {
            clip.stop();
            clip.setMicrosecondPosition(0);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    //MODIFY: this
    //EFFECTS: loads account from JSON file if it exists, create account with given input for create Account Button
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        JsonAccountReader jsonAccountReader = this.app.getJsonReader();

        try {
            clip.start();
        } catch (Exception exception) {
            System.out.println(exception);
        }

        if (e.getSource() == createAccButton) {
            cl.show(this.app.getContainer(), Pages.CREATE_ACCOUNT.name());
        } else if (e.getSource() == loginButton) {
            try {
                Account account = jsonAccountReader.read();

                this.app.setUser(account.getUser());
                this.app.getUser().setAccount(account);

                this.app.setStatus("Welcome back " + account.getUser().getUsername()
                        + "! Your info was successfully loaded!");

                cl.show(this.app.getContainer(), Pages.MENU.name());

            } catch (IOException exception) {
                this.app.setStatus("Oops! We were unable to read from your file!");
                cl.show(this.app.getContainer(), Pages.CREATE_ACCOUNT.name());
            }
        }
    }
}
