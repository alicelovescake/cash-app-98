package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage extends JPanel implements ActionListener {
    JButton button;
    JPanel app;

    static final String ADD_CASH = "Add Cash";
    static final String CASH_OUT = "Cash Out";
    static final String MAKE_PURCHASE = "Make Purchase";
    static final String REQUEST_MONEY = "Request Money";
    static final String UPDATE_CREDIT_CARDS = "Update Credit Cards";
    static final String SEND_MONEY = "Send Money";
    static final String TRANSACTION_HISTORY = "View Transaction History";
    static final String SAVE_LOGOUT = "Save and Log out";

    //EFFECTS: constructor to create menu card for to display options to users
    public MenuPage(JPanel app) {
        this.app = app;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addMenuButton(ADD_CASH);
        addMenuButton(CASH_OUT);
        addMenuButton(MAKE_PURCHASE);
        addMenuButton(REQUEST_MONEY);
        addMenuButton(SEND_MONEY);
        addMenuButton(UPDATE_CREDIT_CARDS);
        addMenuButton(TRANSACTION_HISTORY);
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
        CardLayout cl = (CardLayout) (app.getLayout());
        switch (command) {
            case ADD_CASH:
                cl.show(app, Pages.DEPOSIT.name());
                break;
            case CASH_OUT:
                cl.show(app, Pages.WITHDRAW.name());
                break;
            case MAKE_PURCHASE:
                cl.show(app, Pages.PURCHASE.name());
                break;
            case REQUEST_MONEY:
                cl.show(app, Pages.REQUEST.name());
                break;
            case SEND_MONEY:
                cl.show(app, Pages.SEND.name());
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
                cl.show(app, Pages.CREDIT_CARD.name());
                break;
            case TRANSACTION_HISTORY:
                cl.show(app, Pages.TRANSACTION.name());
                break;
            case SAVE_LOGOUT:
                cl.show(app, Pages.WELCOME.name());
                break;
        }
    }
}
