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
    static final String ACCOUNT_PAGE = "createAccount";
    static final String WELCOME_CARD = "welcomeUser";
    static final String MENU_CARD = "menuOptions";
    static final String CASH_OUT_CARD = "cashOut";
    static final String PURCHASE_CARD = "makePurchase";
    static final String CREDIT_CARDS_CARD = "updateCreditCards";
    static final String ADD_CREDIT_CARD_CARD = "addCreditCardPage";

    static final String CREATE_ACCOUNT = "submitAccountDetails";
    static final String CONFIRM_CASH_MOVEMENT = "confirmDeposit";
    static final String CONFIRM_PURCHASE = "confirmPurchase";
    static final String ADD_CREDIT_CARD = "addCreditCard";
    static final String REMOVE_CREDIT_CARD = "removeCreditCard";
    static final String CONFIRM_ADD_CREDIT_CARD = "confirmAddCreditCard";
    static final String SHOW_MENU = "return to menu";

    private JPanel app;
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
        app = new JPanel(new CardLayout());

        JPanel welcomePage = new WelcomePage(app);
        JPanel createAccountPage = new AccountPage(app);
        JPanel createMenuPage = new MenuPage(app);
        JPanel addCashPage = new DepositPage(app);
        JPanel cashOutPage = new WithdrawalPage(app);
        JPanel createPurchasePage = new PurchasePage(app);
        JPanel requestMoneyPage = new RequestMoneyPage(app);
        JPanel sendMoneyPage = new SendMoneyPage(app);
        JPanel creditCardPage = new CreditCardPage(app);
        JPanel addCreditCard = addCreditCards();
        JPanel transactionHistoryPage = new TransactionHistoryPage(app);


        app.add(welcomePage, Pages.WELCOME.name());
        app.add(createAccountPage, Pages.CREATE_ACCOUNT.name());
        app.add(createMenuPage, Pages.MENU.name());
        app.add(addCashPage, Pages.DEPOSIT.name());
        app.add(cashOutPage, Pages.WITHDRAW.name());
        app.add(createPurchasePage, Pages.PURCHASE.name());
        app.add(requestMoneyPage, Pages.REQUEST.name());
        app.add(sendMoneyPage, Pages.SEND.name());

        app.add(creditCardPage, Pages.CREDIT_CARD.name());
        app.add(addCreditCard, ADD_CREDIT_CARD_CARD);
        app.add(transactionHistoryPage, Pages.TRANSACTION.name());
        add(app, BorderLayout.CENTER);
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
        CardLayout cl = (CardLayout) (app.getLayout());

        switch (command) {
            case ACCOUNT_PAGE:
                cl.show(app, String.valueOf(Pages.CREATE_ACCOUNT));
                break;
            case CREATE_ACCOUNT:
            case CONFIRM_PURCHASE:
            case CONFIRM_CASH_MOVEMENT:
            case SHOW_MENU:
            case CONFIRM_ADD_CREDIT_CARD:
                cl.show(app, MENU_CARD);
                break;
//            case ADD_CASH:
//                cl.show(appPages, ADD_CASH_CARD);
//                break;
//            case CASH_OUT:
//                cl.show(appPages, CASH_OUT_CARD);
//                break;
            default:
                processExtraCommands(command, cl);
        }
    }

    public void processExtraCommands(String command, CardLayout cl) {
        switch (command) {
//            case MAKE_PURCHASE:
//                cl.show(appPages, PURCHASE_CARD);
//                break;
//            case SEND_MONEY:
//                cl.show(appPages, SEND_MONEY);
//                break;
//            case REQUEST_MONEY:
//                cl.show(appPages, REQUEST_MONEY);
//                break;
//            case UPDATE_CREDIT_CARDS:
//                cl.show(appPages, CREDIT_CARDS_CARD);
//                break;
            case ADD_CREDIT_CARD:
                cl.show(app, ADD_CREDIT_CARD_CARD);
                break;
            case REMOVE_CREDIT_CARD:
                int index = creditCardsJList.getSelectedIndex();
                listModel.remove(index);
                break;
//            case TRANSACTION_HISTORY:
//                cl.show(appPages, TRANSACTION_HISTORY);
//                break;
        }


    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }


}
