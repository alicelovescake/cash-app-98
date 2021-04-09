package ui;

import model.CreditCard;
import model.boosts.Boost;

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

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: creates page that displays available boosts
    @Override
    public void createPage() {

        new PageTitle(this, "Your Boosts");
        listModel = new DefaultListModel();

        initializeComboList();

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
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == addBoostButton) {
            cl.show(this.app.getContainer(), Pages.ADD_CREDIT_CARD.name());
        } else if (e.getSource() == removeBoostButton) {
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
            String selectedBoost = boostComboList.getSelectedItem().toString();
        }
    }

}



