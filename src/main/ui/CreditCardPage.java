package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class CreditCardPage extends JPanel implements ActionListener {
    private MainApp app;
    private JButton addCreditCardButton = new JButton("Add New Card");
    private JButton removeCardButton = new JButton("Remove Card");
    private JList creditCardsJList;
    private DefaultListModel listModel;
    private List<CreditCard> creditCardList;

    //EFFECTS: constructor to create credit card page that displays options for updating credit cards
    public CreditCardPage(MainApp app) {
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
    }

    public void createPage() {
        listModel = new DefaultListModel();

        addCreditCardButton.addActionListener(this);
        add(addCreditCardButton);

        if (this.app.getUser() != null) {
            creditCardList = this.app.getUser().getAccount().getCreditCards();

            for (CreditCard c : creditCardList) {
                listModel.addElement(c.getCardNumber());
            }

            creditCardsJList = new JList(listModel);
            creditCardsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            add(creditCardsJList);
        }

        removeCardButton.addActionListener(this);
        add(new ReturnToMenuButton(this.app.getContainer()));
        add(removeCardButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == addCreditCardButton) {
            cl.show(this.app.getContainer(), Pages.ADD_CREDIT_CARD.name());
        } else if (e.getSource() == removeCardButton) {
            int index = creditCardsJList.getSelectedIndex();

            if (index >= 0) {
                Object cardToRemove = listModel.remove(index);
                creditCardsJList.clearSelection();

                List<CreditCard> existingCreditCards = this.app.getUser().getAccount().getCreditCards();
                for (int i = 0; i < existingCreditCards.size(); i++) {
                    CreditCard c = existingCreditCards.get(i);
                    if (Long.toString(c.getCardNumber()).equals(cardToRemove.toString())) {
                        existingCreditCards.remove(c);
                    }
                }
            }
        }
    }
}
