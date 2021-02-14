package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private PersonalUser testPersonalUser;
    private BusinessUser testBusinessUser;

    @BeforeEach
    void setup() {
        testPersonalUser = new PersonalUser
                ("$ilovecashapp", "Victoria", "Wonder", "Woman");
        testBusinessUser = new BusinessUser
                ("$yummyfood", "Vancouver", "Indian Express", BusinessUser.BusinessType.RESTAURANT);
    }

    @Test
    void testConstructor() {
        assertEquals("$ilovecashapp", testPersonalUser.getUsername());
        assertEquals("$yummyfood", testBusinessUser.getUsername());
        assertEquals("Vancouver", testBusinessUser.getLocation());
        assertTrue(testPersonalUser.getId().length() > 0);

        assertTrue(testPersonalUser.getAccount().getId() != null);
    }


    @Test
    void testReferSingleFriend() {
        testPersonalUser.referFriend("bestfriend@gmail.com");
        assertEquals(1, testPersonalUser.getReferralCount());
        assertEquals(2, testPersonalUser.referFriend("bestfriend10@gmail.com"));
    }

    @Test
    void testReferMultipleFriends() {
        testPersonalUser.referFriend("bestfriend@gmail.com");
        testPersonalUser.referFriend("bestfriend2@gmail.com");
        testPersonalUser.referFriend("bestfriend3@gmail.com");
        testPersonalUser.referFriend("bestfriend4@gmail.com");
        testPersonalUser.referFriend("bestfriend5@gmail.com");
        testPersonalUser.referFriend("bestfriend6@gmail.com");
        testPersonalUser.referFriend("aa");
        assertEquals(1, testPersonalUser.getReferralCount());
        if (testPersonalUser.getReferralCount() >= testPersonalUser.getReferralCountForReward()) {
            assertEquals(5, testPersonalUser.account.getBalance());
        }

    }


}
