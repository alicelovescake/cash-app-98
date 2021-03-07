package persistence;

import model.Account;
import model.CreditCard;
import model.Transaction;
import model.User;
import model.boosts.Boost;
import model.boosts.BoostType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// checks stored JSON info in different account objects
public class JsonAccountTest {
    protected void checkUser(String name, String location, String id, User.UserType type, User user) {
        assertEquals(name, user.getUsername());
        assertEquals(location, user.getLocation());
        assertEquals(id, user.getId());
        assertEquals(type, user.getUserType());
    }

    protected void checkCreditCard(String cardType, int cardNumber,
                                   int expiryYear, int expiryMonth, CreditCard card) {
        assertEquals(cardType, card.getCardType());
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(expiryMonth, card.getExpiryMonth());
        assertEquals(expiryYear, card.getExpiryYear());
    }

    protected void checkTransaction(Account recipient, Account sender, String id, LocalDate date, double amount,
                                    Transaction.Status status, Transaction.Type type, Transaction transaction) {
        assertEquals(recipient.getId(), transaction.getRecipientAccount().getId());
        assertEquals(sender.getId(), transaction.getSenderAccount().getId());
        assertEquals(id, transaction.getId());
        assertEquals(date, transaction.getDate());
        assertEquals(amount, transaction.getAmount());
        assertEquals(status, transaction.getStatus());
        assertEquals(type, transaction.getType());
    }

    protected void checkBoost(BoostType type, Set<Boost> boosts) {
        List<BoostType> types = new ArrayList<>();
        for (Boost b : boosts) {
            types.add(b.getBoostType());
        }
        assertTrue(types.contains(type));
    }


}
