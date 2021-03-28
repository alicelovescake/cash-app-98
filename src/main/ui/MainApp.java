package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

//CITATION: Structure of this GUI is modeled after SimpleDrawingPlayer
//          URL: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
public class MainApp extends JFrame implements ActionListener, ItemListener {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 800;
    static final String LOGIN_CARD = "login";
    static final String ACCOUNT_CARD = "createAccount";
    static final String WELCOME_CARD = "welcomeUser";
    static final String MENU_CARD = "menuOptions";
    static final String ADD_CASH_CARD = "addCash";
    static final String CASH_OUT_CARD = "cashOut";
    static final String PURCHASE_CARD = "makePurchase";
    static final String CREDIT_CARDS_CARD = "updateCreditCards";
    static final String ADD_CREDIT_CARD_CARD = "addCreditCardPage";

    static final String CREATE_ACCOUNT = "submitAccountDetails";
    static final String ADD_CASH = "Add Cash";
    static final String CONFIRM_CASH_MOVEMENT = "confirmDeposit";
    static final String CASH_OUT = "Cash Out";
    static final String MAKE_PURCHASE = "Make Purchase";
    static final String CONFIRM_PURCHASE = "confirmPurchase";
    static final String REQUEST_MONEY = "Request Money";
    static final String SEND_MONEY = "Send Money";
    static final String UPDATE_CREDIT_CARDS = "Update Credit Cards";
    static final String ADD_CREDIT_CARD = "addCreditCard";
    static final String REMOVE_CREDIT_CARD = "removeCreditCard";
    static final String CONFIRM_ADD_CREDIT_CARD = "confirmAddCreditCard";
    static final String TRANSACTION_HISTORY = "View Transaction History";
    static final String SAVE_LOGOUT = "Save and Log out";
    static final String SHOW_MENU = "return to menu";

    private JPanel appCards;
    private JList creditCardsJList;
    private DefaultListModel listModel;
    private JButton returnToMenuButton;

    private List<CreditCard> creditCardList = new ArrayList<CreditCard>();
    CreditCard card1 = new CreditCard("VISA", 424, 2024, 5);
    CreditCard card2 = new CreditCard("MASTERCARD", 11111, 2029, 2);


    private User testUserSender = new PersonalUser("$alicelovescake", "Vancouver",
            "Alice", "Zhao");
    private User testUserReceiver = new PersonalUser("$boblovespizza", "Vancouver",
            "Bob", "Marley");
    private Account testSender = new Account(testUserSender, 100.0);
    private Account testReceiver = new Account(testUserReceiver, 500.0);
    private Transaction testTransaction = new Transaction(testReceiver, testSender, 100.0,
            Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
    private Transaction testTransaction2 = new Transaction(testSender, testReceiver, 50.0,
            Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
    private List<Transaction> transactions = new ArrayList<>();

    public MainApp() {
        super("Cash App '98");
        initializeFields();
        initializeGraphics();
    }

    //MODIFY: this
    //EFFECTS: sets and instantiate
    public void initializeFields() {
        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setActionCommand(SHOW_MENU);
        returnToMenuButton.addActionListener(this);
    }

    //MODIFY: this
    //EFFECTS: draws on JFrame window for CashApp and populate with cardlayout with different options
    public void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);

        getContentPane().setBackground(new Color(228, 233, 216));
        initializeAppPages();
        setVisible(true);
    }


    //MODIFY: this
    //EFFECTS: Creates card layout to welcome user to create account or login
    public void initializeAppPages() {
        JPanel welcomeCard = createWelcomeCard();
        JPanel createAccountCard = createAccountCard();
        JPanel createMenuCard = createMenuCard();
        JPanel addCashCard = moveCashCard("Deposit");
        JPanel cashOutCard = moveCashCard("Withdrawal");
        JPanel createPurchaseCard = createMoneyExchangeCard("Purchase");
        JPanel requestMoneyCard = createMoneyExchangeCard("Request");
        JPanel sendMoneyCard = createMoneyExchangeCard("Send");
        JPanel updateCreditCards = updateCreditCards();
        JPanel addCreditCard = addCreditCards();
        JPanel transactionHistory = transactionHistory();

        appCards = new JPanel(new CardLayout());
        appCards.add(welcomeCard, WELCOME_CARD);
        appCards.add(createAccountCard, ACCOUNT_CARD);
        appCards.add(createMenuCard, MENU_CARD);
        appCards.add(addCashCard, ADD_CASH_CARD);
        appCards.add(cashOutCard, CASH_OUT_CARD);
        appCards.add(createPurchaseCard, PURCHASE_CARD);
        appCards.add(requestMoneyCard, REQUEST_MONEY);
        appCards.add(sendMoneyCard, SEND_MONEY);
        appCards.add(updateCreditCards, CREDIT_CARDS_CARD);
        appCards.add(addCreditCard, ADD_CREDIT_CARD_CARD);
        appCards.add(transactionHistory, TRANSACTION_HISTORY);
        add(appCards, BorderLayout.CENTER);
    }

    // MODIFY: this
    // EFFECTS: creates welcome cards for create account and login

    public JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel();

        welcomeCard.add(displayWelcomeLabel());

        JButton createAccButton = new JButton("Create Account");
        createAccButton.setActionCommand(ACCOUNT_CARD);
        createAccButton.addActionListener(this);

        JButton loginButton = new JButton("Login");
        loginButton.setActionCommand(LOGIN_CARD);
        loginButton.addActionListener(this);

        welcomeCard.add(createAccButton);
        welcomeCard.add(loginButton);

        return welcomeCard;
    }

    //MODIFY: this
    //EFFECTS: displays welcome label as first screen that greets users
    public JLabel displayWelcomeLabel() {
        JLabel welcomeLabel = new JLabel();

        welcomeLabel.setText("Welcome to Cash App '98! Our mission is to create an inclusive economy"
                + " by helping you send, receive, and spend money easier");

        return welcomeLabel;
    }

    //MODIFY: this
    //EFFECTS: creates card for create account page that allows input
    public JPanel createAccountCard() {
        JPanel accountCard = new JPanel();
        accountCard.setLayout(new GridLayout(0, 2, 10, 10));

        JButton submitButton = new JButton("Submit");
        submitButton.setActionCommand(CREATE_ACCOUNT);
        submitButton.addActionListener(this);

        JRadioButton personalAccButton = new JRadioButton("Personal Account");
        JRadioButton businessAccButton = new JRadioButton("Business Account");

        ButtonGroup group = new ButtonGroup();
        group.add(personalAccButton);
        group.add(businessAccButton);

        accountCard.add(personalAccButton);
        accountCard.add(businessAccButton);

        accountCard = generateTextFields(accountCard);

        accountCard.add(submitButton);

        return accountCard;
    }

    //MODIFY: this:
    //EFFECTS: creates text fields for username, first name, last name, and location
    public JPanel generateTextFields(JPanel accountCard) {
        TextField username = new TextField();
        JLabel usernameLabel = new JLabel("Username:");

        TextField firstName = new TextField();
        JLabel firstNameLabel = new JLabel("First Name:");

        TextField lastname = new TextField();
        JLabel lastNameLabel = new JLabel("Last Name:");

        TextField location = new TextField();
        JLabel locationLabel = new JLabel("Location:");

        accountCard.add(usernameLabel);
        accountCard.add(username);

        accountCard.add(firstNameLabel);
        accountCard.add(firstName);

        accountCard.add(lastNameLabel);
        accountCard.add(lastname);

        accountCard.add(locationLabel);
        accountCard.add(location);

        return accountCard;
    }

    //MODIFY: this
    //EFFECTS: creates menu card for to display options to users
    public JPanel createMenuCard() {
        JPanel menuCard = new JPanel();
        menuCard.setLayout(new BoxLayout(menuCard, BoxLayout.Y_AXIS));
        addMenuButton(ADD_CASH, menuCard);
        addMenuButton(CASH_OUT, menuCard);
        addMenuButton(MAKE_PURCHASE, menuCard);
        addMenuButton(REQUEST_MONEY, menuCard);
        addMenuButton(SEND_MONEY, menuCard);
        addMenuButton(UPDATE_CREDIT_CARDS, menuCard);
        addMenuButton(TRANSACTION_HISTORY, menuCard);
        addMenuButton(SAVE_LOGOUT, menuCard);
        return menuCard;
    }

    //MODIFY: this
    //EFFECTS: Create button and label for each menu option
    public void addMenuButton(String text, JPanel card) {
        JButton button = new JButton(text);
        button.setAlignmentX(card.CENTER_ALIGNMENT);
        button.setActionCommand(text);
        button.addActionListener(this);
        card.add(button);
    }

    //MODIFY: this
    //EFFECTS: creates page to make purchase/send money/request money by accepting user input for recipient and amount
    public JPanel createMoneyExchangeCard(String type) {
        JPanel purchaseCard = new JPanel();
        JButton purchaseButton = new JButton("Confirm " + type);
        purchaseButton.setActionCommand(CONFIRM_PURCHASE);
        purchaseButton.addActionListener(this);

        TextField recipientUsername = new TextField();
        recipientUsername.setPreferredSize(new Dimension(100, 30));
        JLabel usernameLabel = new JLabel("Recipient Username:");

        TextField sendAmount = new TextField();
        sendAmount.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel(type + " Amount:");

        purchaseCard.add(usernameLabel);
        purchaseCard.add(recipientUsername);
        purchaseCard.add(amountLabel);
        purchaseCard.add(sendAmount);
        purchaseCard.add(purchaseButton);
        return purchaseCard;
    }

    //MODIFY: this
    //EFFECTS: creates page to deposit or withdraw money into account
    public JPanel moveCashCard(String type) {
        JPanel moveCashCard = new JPanel();
        JPanel creditCardPane = new JPanel();

        String[] creditCards = {"Placeholder Card 1", "Placeholder Card 2"};

        JComboBox cb = new JComboBox(creditCards);

        cb.setEditable(false);
        cb.addItemListener(this);

        creditCardPane.add(cb);

        TextField amountField = new TextField();
        amountField.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel(type + " Amount:");

        JButton confirmButton = new JButton("Confirm " + type);
        confirmButton.setActionCommand(CONFIRM_CASH_MOVEMENT);
        confirmButton.addActionListener(this);

        moveCashCard.add(creditCardPane);
        moveCashCard.add(amountLabel);
        moveCashCard.add(amountField);
        moveCashCard.add(confirmButton);

        return moveCashCard;
    }

    //MODIFY: this
    //EFFECTS: creates page to update and list all credit cards in account
    public JPanel updateCreditCards() {
        creditCardList.add(card1);
        creditCardList.add(card2);
        JPanel creditCardsCard = new JPanel();
        JButton addCreditCardButton = new JButton("Add New Card");
        addCreditCardButton.setActionCommand(ADD_CREDIT_CARD);
        addCreditCardButton.addActionListener(this);
        creditCardsCard.add(addCreditCardButton);

        listModel = new DefaultListModel();

        for (CreditCard c : creditCardList) {
            listModel.addElement(c.getCardType() + " "
                    + String.valueOf(c.getCardNumber()));
        }

        creditCardsJList = new JList(listModel);
        creditCardsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        creditCardsCard.add(creditCardsJList);

        JButton removeCardButton = new JButton("Remove Card");
        removeCardButton.setActionCommand(REMOVE_CREDIT_CARD);
        removeCardButton.addActionListener(this);
        creditCardsCard.add(removeCardButton);

        return creditCardsCard;
    }

    //MODIFY: this
    //EFFECTS: creates page to add a credit card to the account
    public JPanel addCreditCards() {
        JPanel creditCardCard = new JPanel();
        creditCardCard.setLayout(new GridLayout(0, 2, 5, 5));

        TextField typeField = new TextField();
        typeField.setSize(new Dimension(100, 5));
        JLabel typeLabel = new JLabel("Credit Card Type:");

        TextField numField = new TextField();
        numField.setSize(new Dimension(100, 5));
        JLabel numLabel = new JLabel("Credit Card Number:");

        TextField expiryYearField = new TextField();
        expiryYearField.setSize(new Dimension(100, 30));
        JLabel expiryYearLabel = new JLabel("Credit Card Expiry Year:");

        TextField expiryMonthField = new TextField();
        expiryMonthField.setSize(new Dimension(100, 30));
        JLabel expiryMonthLabel = new JLabel("Credit Card Expiry Month:");

        JButton confirmAddCreditCardButton = new JButton("Confirm Add Credit Card");
        confirmAddCreditCardButton.setActionCommand(CONFIRM_ADD_CREDIT_CARD);
        confirmAddCreditCardButton.addActionListener(this);

        creditCardCard.add(typeLabel);
        creditCardCard.add(typeField);
        creditCardCard.add(numLabel);
        creditCardCard.add(numField);
        creditCardCard.add(expiryYearLabel);
        creditCardCard.add(expiryYearField);
        creditCardCard.add(expiryMonthLabel);
        creditCardCard.add(expiryMonthField);
        creditCardCard.add(confirmAddCreditCardButton);

        return creditCardCard;
    }

    //MODIFY: this
    //EFFECTS: Display transaction history page
    public JPanel transactionHistory() {
        transactions.add(testTransaction);
        transactions.add(testTransaction2);

        JPanel transactionHistory = new JPanel();
        transactionHistory.setLayout(new GridLayout(0, 1));

        String[] columnNames = {"DATE", "RECIPIENT", "SENDER", "AMOUNT", "STATUS"};

        Object[][] data = new Object[transactions.size()][columnNames.length];
        for (int i = 0; i < transactions.size(); i++) {
            String[] transactionArray = {
                    String.valueOf(transactions.get(i).getDate()),
                    transactions.get(i).getRecipientUsername(),
                    transactions.get(i).getSenderUsername(),
                    String.valueOf(transactions.get(i).getAmount()),
                    transactions.get(i).getStatus().name(),
            };
            data[i] = transactionArray;
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);

        transactionHistory.add(scrollPane);
        transactionHistory.add(returnToMenuButton);
        return transactionHistory;
    }


    // Starts CashApp
    public static void main(String[] args) {
        new MainApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout) (appCards.getLayout());

        switch (command) {
            case ACCOUNT_CARD:
                cl.show(appCards, ACCOUNT_CARD);
                break;
            case CREATE_ACCOUNT:
            case CONFIRM_PURCHASE:
            case CONFIRM_CASH_MOVEMENT:
            case SHOW_MENU:
            case CONFIRM_ADD_CREDIT_CARD:
                cl.show(appCards, MENU_CARD);
                break;
            case ADD_CASH:
                cl.show(appCards, ADD_CASH_CARD);
                break;
            case CASH_OUT:
                cl.show(appCards, CASH_OUT_CARD);
                break;
            default:
                processExtraCommands(command, cl);
        }
    }

    public void processExtraCommands(String command, CardLayout cl) {
        switch (command) {
            case MAKE_PURCHASE:
                cl.show(appCards, PURCHASE_CARD);
                break;
            case SEND_MONEY:
                cl.show(appCards, SEND_MONEY);
                break;
            case REQUEST_MONEY:
                cl.show(appCards, REQUEST_MONEY);
                break;
            case UPDATE_CREDIT_CARDS:
                cl.show(appCards, CREDIT_CARDS_CARD);
                break;
            case ADD_CREDIT_CARD:
                cl.show(appCards, ADD_CREDIT_CARD_CARD);
                break;
            case REMOVE_CREDIT_CARD:
                int index = creditCardsJList.getSelectedIndex();
                listModel.remove(index);
                break;
            case TRANSACTION_HISTORY:
                cl.show(appCards, TRANSACTION_HISTORY);
                break;
        }


    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }


}
