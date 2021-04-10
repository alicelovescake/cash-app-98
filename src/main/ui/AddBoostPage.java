package ui;

import model.Account;
import model.CreditCard;
import model.boosts.Boost;
import model.boosts.FoodieBoost;
import model.boosts.HighRollerBoost;
import model.boosts.ShopaholicBoost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;

public class AddBoostPage extends JPanel implements Page, ActionListener, ItemListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Boost");
    private JButton addBoostButton = new JButton("Add New Boost");
    private JButton removeBoostButton = new JButton("Remove Boost");
    private JList boostJList;
    private DefaultListModel listModel;
    private Set<Boost> boostList;
    private JComboBox boostComboList;
    private JLabel addBoostLabel;
    private String[] boostStrings = {"Click me", "High Roller Boost", "Shopaholic Boost", "Foodie Boost"};
    private Boost shopaholicBoost = new ShopaholicBoost();
    private Boost highRollerBoost = new HighRollerBoost();
    private Boost foodieBoost = new FoodieBoost();
    private JLabel status;
    private String shopaholicStatus = "<html>"
            + "You'll get " + "<b><font color=red>5% cashback</font></b>" + " on every retail purchase";
    private String highRollerStatus = "<html>"
            + "You'll get " + "<b><font color=red>10% cashback</font></b>" + " on every purchase > $1,000";
    private String foodieStatus = "<html>"
            + "You'll get " + "<b><font color=red>3% cashback</font></b>" + " on every yummy purchase";


    public AddBoostPage(MainApp app) {
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

        confirmButton.addActionListener(this);
        addBoostButton.addActionListener(this);
        removeBoostButton.addActionListener(this);
        status = new JLabel("Your current boosts");
//        Font f = status.getFont();
//        status.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: creates page that displays available boosts
    @Override
    public void createPage() {

        new PageTitle(this, "Your Boosts");
        listModel = new DefaultListModel();

        initializeComboList();
        add(status);

        if (this.app.getUser() != null) {
            boostList = this.app.getUser().getAccount().getBoosts();

            for (Boost b : boostList) {
                listModel.addElement(b.getBoostType());
            }

            boostJList = new JList(listModel);
            boostJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            boostJList.setPreferredSize(new Dimension(400, 150));

            add(boostJList);

            add(Box.createRigidArea(new Dimension(400, 25)));
        }


        add(addBoostLabel);
        add(boostComboList);
        add(Box.createRigidArea(new Dimension(400, 25)));

        add(removeBoostButton);

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(new ReturnToMenuButton(this.app.getContainer()));


    }

    public void initializeComboList() {
        //Create combo box
        boostComboList = new JComboBox(boostStrings);
        boostComboList.setMaximumRowCount(3);
        boostComboList.addItemListener(this);

        //Create label
        addBoostLabel = new JLabel("SELECT YOUR BOOST:");
        addBoostLabel.setForeground(Color.DARK_GRAY);
        add(Box.createRigidArea(new Dimension(400, 25)));
    }
    //MODIFY: this
    // EFFECTS: action listener that listens to add btn to redirects page & remove btn to remove boost

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeBoostButton) {
            int index = boostJList.getSelectedIndex();

            if (index >= 0) {
                Object boostToRemove = listModel.remove(index);
                boostJList.clearSelection();

                Set<Boost> existingBoosts = this.app.getUser().getAccount().getBoosts();
                if (existingBoosts.contains(boostToRemove)) {
                    existingBoosts.remove(boostToRemove);
                }
            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == boostComboList) {
            int selectedBoost = boostComboList.getSelectedIndex();
            processSelection(selectedBoost);
        }
    }

    private void processSelection(int selectedBoost) {
        Account userAccount = this.app.getUser().getAccount();


        switch (selectedBoost) {
            case 1:
                if (!listModel.contains("HIGHROLLER")) {
                    addHighRollerBoost(userAccount);
                }
                break;
            case 2:
                if (!listModel.contains("SHOPAHOLIC")) {
                    addShopaholicBoost(userAccount);
                }
                break;
            case 3:
                if (!listModel.contains("FOODIE")) {
                    addFoodieBoost(userAccount);
                }
                break;
        }
    }

    private void addHighRollerBoost(Account userAccount) {
        userAccount.addBoost(highRollerBoost);
        listModel.addElement("HIGHROLLER");
        status.setText(highRollerStatus);
    }

    private void addShopaholicBoost(Account userAccount) {
        userAccount.addBoost(shopaholicBoost);
        listModel.addElement("SHOPAHOLIC");
        status.setText(shopaholicStatus);
    }

    private void addFoodieBoost(Account userAccount) {
        userAccount.addBoost(foodieBoost);
        listModel.addElement("FOODIE");
        status.setText(foodieStatus);
    }
}



