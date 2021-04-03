package ui;

import persistence.JsonAccountWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;

//class that creates menu card for to display options to users and directs them to other pages
public class MenuPage extends JPanel implements ActionListener, Page{
    JButton button;
    MainApp app;
    JLabel balanceLabel;
    JLabel balanceAmountLabel;
    double balance;

    static final String ADD_CREDIT_CARD = "Add Credit Card";
    static final String ADD_CASH = "Add Cash";
    static final String CASH_OUT = "Cash Out";
    static final String MAKE_PURCHASE = "Make Purchase";
    static final String REQUEST_MONEY = "Request Money";
    static final String UPDATE_CREDIT_CARDS = "Update Credit Cards";
    static final String SEND_MONEY = "Send Money";
    static final String TRANSACTION_HISTORY = "View Transaction History";
    static final String SAVE_LOGOUT = "Save and Log out";

    //Effects: constructor that create page & adds component & action listener to update and revalidate page
    // when component changes.
    public MenuPage(MainApp app) {
        this.app = app;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                removeAll();

                createPage();

                revalidate();
                repaint();
            }
        });

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: Create menu page to display options available to user, including updated current balance, adds to panel
    @Override
    public void createPage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        balance = this.app.getUser() == null ? 0 : this.app.getUser().getAccount().getBalance();

        balanceAmountLabel = new JLabel("$" + balance);
        balanceAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceAmountLabel.setFont(new Font(balanceAmountLabel.getName(), Font.BOLD, 45));

        balanceLabel = new JLabel("BALANCE");
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLabel.setForeground(Color.DARK_GRAY);

        addAppStatus();

        add(balanceAmountLabel);
        add(balanceLabel);
        add(Box.createRigidArea(new Dimension(5, 25)));

        int creditCardCount = this.app.getUser().getAccount().getCreditCards().size();

        if (creditCardCount == 0) {
            addMenuButton(ADD_CREDIT_CARD);
        } else {
            addMenu();
        }
    }

    //MODIFY: this
    //EFFECTS: Adds app status
    public void addAppStatus() {
        add(Box.createRigidArea(new Dimension(5, 25)));
        JLabel appStatus = this.app.getStatus();

        appStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        appStatus.setBackground(new Color(48, 127, 60));
        appStatus.setForeground(Color.WHITE);
        appStatus.setOpaque(true);
        appStatus.setFont(new Font(appStatus.getName(), Font.BOLD, 12));

        add(appStatus);
        add(Box.createRigidArea(new Dimension(5, 25)));
    }

    //MODIFY: this
    //EFFECTS: Adds menu
    public void addMenu() {
        addMenuButton(ADD_CASH);
        addMenuButton(CASH_OUT);
        add(Box.createRigidArea(new Dimension(5, 25)));

        addMenuButton(MAKE_PURCHASE);
        addMenuButton(REQUEST_MONEY);
        addMenuButton(SEND_MONEY);
        add(Box.createRigidArea(new Dimension(5, 25)));

        addMenuButton(UPDATE_CREDIT_CARDS);
        add(Box.createRigidArea(new Dimension(5, 25)));

        addMenuButton(TRANSACTION_HISTORY);
        add(Box.createRigidArea(new Dimension(5, 25)));

        addMenuButton(SAVE_LOGOUT);
    }

    //MODIFY: this
    //EFFECTS: Create button and label for each menu option
    public void addMenuButton(String text) {
        button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setActionCommand(text);
        button.addActionListener(this);
        add(button);
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout) (app.getContainer().getLayout());
        switch (command) {
            case ADD_CASH:
                cl.show(this.app.getContainer(), Pages.DEPOSIT.name());
                break;
            case CASH_OUT:
                cl.show(this.app.getContainer(), Pages.WITHDRAW.name());
                break;
            case MAKE_PURCHASE:
                cl.show(this.app.getContainer(), Pages.PURCHASE.name());
                break;
            case REQUEST_MONEY:
                cl.show(this.app.getContainer(), Pages.REQUEST.name());
                break;
            case SEND_MONEY:
                cl.show(this.app.getContainer(), Pages.SEND.name());
                break;
            default:
                processExtraCommands(command, cl);
        }
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click
    public void processExtraCommands(String command, CardLayout cl) {
        switch (command) {
            case UPDATE_CREDIT_CARDS:
                cl.show(this.app.getContainer(), Pages.CREDIT_CARD.name());
                break;
            case TRANSACTION_HISTORY:
                cl.show(this.app.getContainer(), Pages.TRANSACTION.name());
                break;
            case SAVE_LOGOUT:
                saveAccountInfo();
                cl.show(this.app.getContainer(), Pages.WELCOME.name());
                break;
            case ADD_CREDIT_CARD:
                cl.show(this.app.getContainer(), Pages.ADD_CREDIT_CARD.name());
                break;
        }
    }

    //MODIFY: JSON file
    //EFFECTS: saves account activities to file
    public void saveAccountInfo() {
        JsonAccountWriter writer = this.app.getJsonWriter();

        try {
            writer.open();
            writer.write(this.app.getUser().getAccount());
            writer.close();
            this.app.setStatus("Congrats! Account was successfully saved");
        } catch (FileNotFoundException e) {
            this.app.setStatus("Oops! Something went wrong with saving your file");
        }
    }
}
