package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreditCardPage extends JPanel implements ActionListener {
    private JPanel app;
    private JButton addCreditCardButton = new JButton("Add New Card");
    private JButton removeCardButton = new JButton("Remove Card");
    private JList creditCardsJList;
    private DefaultListModel listModel;

    private List<CreditCard> creditCardList = new ArrayList<CreditCard>();
    CreditCard card1 = new CreditCard("VISA", 424, 2024, 5);
    CreditCard card2 = new CreditCard("MASTERCARD", 11111, 2029, 2);

    //EFFECTS: constructor to create credit card page that displays options for updating credit cards
    public CreditCardPage(JPanel app) {
        this.app = app;
        creditCardList.add(card1);
        creditCardList.add(card2);

        addCreditCardButton.addActionListener(this);
        add(addCreditCardButton);

        listModel = new DefaultListModel();

        for (CreditCard c : creditCardList) {
            listModel.addElement(c.getCardType() + " "
                    + String.valueOf(c.getCardNumber()));
        }

        creditCardsJList = new JList(listModel);
        creditCardsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(creditCardsJList);

        removeCardButton.addActionListener(this);
        add(new ReturnToMenuButton(app));
        add(removeCardButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());
        if (e.getSource() == addCreditCardButton) {
            cl.show(this.app, Pages.ADD_CREDIT_CARD.name());
        } else if (e.getSource() == removeCardButton) {
            int index = creditCardsJList.getSelectedIndex();
            listModel.remove(index);
        }
    }
}
