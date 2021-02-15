package ui;

import model.*;

import java.util.Locale;
import java.util.Scanner;

import static model.BusinessUser.BusinessType.*;

//Cash App
public class CashApp {
    private Scanner input;
    private User user;

    private User cashAppUser = new BusinessUser("cashapp", "Vancouver, BC", "CashApp", OTHER);
    private Account cashAppAccount = new Account(cashAppUser, 1000000.00);

    //EFFECTS: runs the cash app
    public CashApp() {
        input = new Scanner(System.in);

        runCreateAccountFlow();

        runApp();
    }

    //MODIFY: this
    //EFFECTS: enters app loop to interact with user
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThanks for using CashApp, don't forget to tell your friends!");
    }

    //MODIFY: this
    //EFFECTS: process user input to create new user and account
    private void runCreateAccountFlow() {
        displayWelcomeMessage();
        String command = input.next();
        command = command.toLowerCase();
        processAccountCreation(command);
    }

    //MODIFY: this
    //EFFECTS: processes user command to create account
    private void processAccountCreation(String command) {
        if (command.equals("p")) {
            createPersonalAccount();
        } else if (command.equals("b")) {
            createBusinessAccount();
        } else {
            System.out.println("Please select p or b to create an account!");
        }
    }

    //MODIFY: this
    //EFFECTS: processes user command to run app flows
    private void processCommand(String command) {
        switch (command) {
            case "b":
                runCheckBalanceFlow();
                break;
            case "f":
                runFundAccountFlow();
                break;
            case "w":
                runWithdrawFundsFlow();
                break;
            case "s":
                runSendMoneyFlow();
                break;
            case "r":
                runRequestMoneyFlow();
                break;
            case "c":
                break;
            case "h":
                runTransactionHistoryFlow();
                break;
            default:
        }
    }

    //EFFECTS: display welcome message to user
    private void displayWelcomeMessage() {
        System.out.println("Welcome to Cash App '98! \n");
        System.out.println("Our mission is to create an inclusive economy "
                + "by helping you send, receive, and spend money easier \n");
        System.out.println("Would you like to create a personal or business account? \n");
        System.out.println("\tp -> personal");
        System.out.println("\tb -> business");
    }

    //EFFECTS: display menu
    private void displayMenu() {
        System.out.println("\nHow can we help you today?\n");
        System.out.println("Select from the following options:\n");
        System.out.println("\tb -> check balance");
        System.out.println("\tf -> fund your account");
        System.out.println("\tw -> withdraw funds");
        System.out.println("\ts -> send money");
        System.out.println("\tr -> request money");
        System.out.println("\tc -> update credit cards");
        System.out.println("\th -> view transaction history");
        System.out.println("\tq -> quit app");
    }

    //MODIFY: this
    //EFFECTS: process user command to create personal account
    private void createPersonalAccount() {
        System.out.println("\nWhat username would you like?");
        String username = input.next();
        username = username.toLowerCase();

        System.out.println("\nWhat is your first name?");
        String firstName = input.next();
        firstName = firstName.toLowerCase();

        System.out.println("\nWhat is your last name?");
        String lastName = input.next();
        lastName = lastName.toLowerCase();

        System.out.println("\nWhere are you from?");
        String location = input.next();
        location = location.toLowerCase();

        user = new PersonalUser(username, location, firstName, lastName);

        System.out.println("\nIt's great for you to join us from "
                + location + "!" + " Your username $" + username + " is ready to be used!\uD83C\uDF89");
    }

    //MODIFY: this
    //EFFECTS: process user command to create business account
    private void createBusinessAccount() {
        System.out.println("\nWhat username would you like?");
        String username = input.next();
        username = username.toLowerCase();

        System.out.println("\nWhat is your company name?");
        String companyName = input.next();
        companyName = companyName.toLowerCase();

        System.out.println("\nWhere is your company headquartered?");
        String location = input.next();
        location = location.toLowerCase();

        System.out.println("\nSelect your type of business");
        System.out.println("\tc -> cafe");
        System.out.println("\tg -> grocery");
        System.out.println("\tr -> retailer");
        System.out.println("\te -> restaurant");
        System.out.println("\to -> other");
        String typeInput = input.next();
        typeInput = typeInput.toLowerCase();

        BusinessUser.BusinessType type = processBusinessType(typeInput);
        user = new BusinessUser(username, location, companyName, type);

        System.out.println("\nIt's great for you to join us from "
                + location + "!" + " Time to take " + companyName + " to the next level! \uD83C\uDF89");
    }

    private BusinessUser.BusinessType processBusinessType(String input) {
        BusinessUser.BusinessType type;
        switch (input) {
            case "c":
                type = CAFE;
                break;
            case "g":
                type = GROCERY;
                break;
            case "r":
                type = RETAILER;
                break;
            case "e":
                type = RESTAURANT;
                break;
            default:
                type = OTHER;
        }
        return type;
    }

    private void runCheckBalanceFlow() {
        System.out.println("\nYour balance is $" + user.getAccount().getBalance() + ".");
    }

    private void runFundAccountFlow() {
        if (user.getAccount().getCreditCards().size() == 0) {
            runAddCreditCardFlow();
        }

        System.out.println("\nHow much would you like to deposit?");
        int depositAmount = input.nextInt();

        CreditCard creditCard = (CreditCard) user.getAccount().getCreditCards().get(0);

        user.getAccount().deposit(creditCard, depositAmount);

        System.out.println("\nWe have deposited $" + depositAmount + " into your account.");
        System.out.println("\nYour new balance is $" + user.getAccount().getBalance() + ".");
    }

    private void runWithdrawFundsFlow() {
        if (user.getAccount().getCreditCards().size() == 0) {
            runAddCreditCardFlow();
        }

        System.out.println("\nYour balance is $" + user.getAccount().getBalance() + ".");

        System.out.println("\nHow much would you like to withdraw?");
        int withdrawAmount = input.nextInt();

        CreditCard creditCard = (CreditCard) user.getAccount().getCreditCards().get(0);

        if (user.getAccount().withdraw(creditCard, withdrawAmount)) {
            System.out.println("\nThe withdraw was successful. It make take a few days until you see it on your card.");
        } else {
            System.out.println("\nDarn, it looks like you don't have enough funds for that!");
        }
    }

    private void runAddCreditCardFlow() {
        System.out.println("\nIt looks like you don't have a credit card on your account. Let's add one now.");

        CreditCard creditCard = new CreditCard("", 0, 0, 0);

        while (!creditCard.getIsValid()) {
            System.out.println("\nWho issued your credit card (VISA, MasterCard)?");
            String creditCardType = input.next();
            creditCardType = creditCardType.toLowerCase();

            System.out.println("\nWhat is your credit card number?");
            int creditCardNumber = input.nextInt();

            System.out.println("\nWhat is your credit card expiry year (ie: 2020)?");
            int creditCardExpiryYear = input.nextInt();

            System.out.println("\nWhat is your credit card expiry month (ie: 6)?");
            int creditCardExpiryMonth = input.nextInt();

            creditCard = new CreditCard(creditCardType, creditCardNumber,
                    creditCardExpiryYear, creditCardExpiryMonth);

            if (creditCard.getIsValid()) {
                user.getAccount().addCreditCard(creditCard);

                System.out.println("\nYour credit card has been added, nice!");
            } else {
                System.out.println("\nHmm... that card isn't valid. Let's try that again.");
            }
        }
    }

    private void runSendMoneyFlow() {
        System.out.println("\nWe're glad you want to try out our send money flow.");
        System.out.println("\nCashApp is currently in development so you can only send to our account.");

        // Prompt the user for input but don't use it until we save usernames in a database/file
        System.out.println("\nWhat is the CashApp username you'd like to send to (psst it's $cashapp)?");
        String username = input.next();

        System.out.println("\nHow much would you like to send?");
        int sendAmount = input.nextInt();

        Transaction transaction = user.getAccount().sendMoney(cashAppAccount, sendAmount);

        if (transaction.getStatus() == Transaction.Status.COMPLETE) {
            System.out.println("\nNice, we got the money. Thanks for helping the development of CashApp.");
            System.out.println("\nYour new balance is $" + user.getAccount().getBalance() + ".");
        } else {
            System.out.println("\nSomething went wrong with the transaction. Perhaps you don't have enough Benjamins.");
        }
    }

    private void runRequestMoneyFlow() {
        System.out.println("\nWe're glad you want to try out our send money flow.");
        System.out.println("\nCashApp is currently in development so you can only request from our account.");

        // Prompt the user for input but don't use it until we save usernames in a database/file
        System.out.println("\nWhat is the CashApp username you'd like to send to (psst it's $cashapp)?");
        String username = input.next();

        System.out.println("\nHow much would you like to request?");
        int requestAmount = input.nextInt();

        Transaction transaction = user.getAccount().requestMoney(cashAppAccount, requestAmount);

        if (transaction.getStatus() == Transaction.Status.FAILED) {
            System.out.println("\nSomething went wrong with the transaction.");
        } else {
            System.out.println("\nYour money request has been processed.");
            System.out.println("\nCheck your transactions to check the status.");
        }
    }

    private void runTransactionHistoryFlow() {
        System.out.println("\nHere are the last 10 transactions in your account:");
    }
}
