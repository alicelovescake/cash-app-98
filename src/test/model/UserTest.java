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
        assertEquals(User.UserType.PERSONAL, testPersonalUser.getUserType());
        assertEquals(5, testPersonalUser.getCashBackForReferral());
        assertEquals(5, testPersonalUser.getReferralCountForReward());

        assertTrue(testPersonalUser.getAccount().getId() != null);
    }


    @Test
    void testReferSingleFriend() {
        assertTrue(testPersonalUser.referFriend("bestfriend@gmail.com"));
        assertEquals(1, testPersonalUser.getReferralCount());
        assertFalse(testPersonalUser.referralReward());
    }

    @Test
    void testReferMultipleFriendsReward() {
        assertTrue(testPersonalUser.referFriend("bestfriend@gmail.com"));
        assertTrue(testPersonalUser.referFriend("bestfriend2@gmail.com"));
        assertTrue(testPersonalUser.referFriend("bestfriend3@gmail.com"));
        assertTrue(testPersonalUser.referFriend("bestfriend4@gmail.com"));
        assertTrue(testPersonalUser.referFriend("bestfriend5@gmail.com"));
        assertTrue(testPersonalUser.referFriend("bestfriend6@gmail.com"));
        assertFalse(testPersonalUser.referFriend("bestfriend6@gmail.com"));
        assertFalse(testPersonalUser.referFriend("aa"));

        assertEquals(1, testPersonalUser.getReferralCount());
        assertEquals(5, testPersonalUser.account.getBalance());

    }

    @Test
    void testBusinessUserReferFalse(){
        assertFalse(testBusinessUser.referFriend("hello@gmail.com"));
        assertFalse(testBusinessUser.referralReward());
    }


}
