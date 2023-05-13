package grp16.tripmate.user.model;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.user.model.factory.IUserFactory;
import grp16.tripmate.user.model.factory.UserFactory;
import grp16.tripmate.user.persistence.UserPersistenceMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
class UserTest {
    private final IUserFactory userFactory;

    private User user;

    private final UserPersistenceMock userPersistence;

    UserTest() throws NoSuchAlgorithmException, ParseException {
        userFactory = UserFactory.getInstance();
        createTestUser();
        userPersistence = new UserPersistenceMock();
    }

    private void createTestUser() throws NoSuchAlgorithmException, ParseException {
        user = (User) userFactory.makeNewUser();
        user.setUsername("uname");
        user.setPassword("password");
        user.setId(1);
        user.setLastname("lastname");
        user.setFirstname("firstname");
        user.setBirthDate("1999-01-11");
        user.setGender("Male");
    }

    @Test
    void testToStringTest() {
        Assertions.assertEquals(user.toString(), user.toString());
    }

    @Test
    void createUserPositiveTest() {
        Assertions.assertTrue(userPersistence.insertUser(user));
    }

    @Test
    void createUserNegativeTest() {
        Assertions.assertFalse(userPersistence.insertUser(user));
    }

    @Test
    void validateUserPositiveTest() throws NoSuchAlgorithmException {
        userPersistence.insertUser(user);
        Assertions.assertEquals(user, userPersistence.getUserByUsername("uname"));
    }

    @Test
    void validateUserNegativeTest() throws NoSuchAlgorithmException {
        Assertions.assertNull(userPersistence.getUserByUsername("sharshil1299@gmail.com"));
    }

    @Test
    void dateToSQLDateTest() {
        String SQLBirthDate = user.dateToSQLDate(user.getBirthDate());
        Assertions.assertEquals("1999-01-11", SQLBirthDate);
    }

    @Test
    void changeUserDetailsPositiveTest() throws Exception {
        Assertions.assertTrue(userPersistence.updateUser(user));
    }

    @Test
    void changeUserDetailsNegativeTest() throws Exception {
        user.setId(4);
        Assertions.assertFalse(userPersistence.updateUser(user));
    }

    @Test
    void checkUserExistTest() throws NoSuchAlgorithmException {
        userPersistence.insertUser(user);
        Assertions.assertEquals(user, userPersistence.getUserByUsername("uname"));
    }

    @Test
    void checkUserExistNegativeTest() throws NoSuchAlgorithmException {
        Assertions.assertNull(userPersistence.getUserByUsername("arpitpatel@gmail.com"));
    }

    @Test
    void changeUserPassword() throws NoSuchAlgorithmException {
        String email = "arpitpatel1501@gmail.com";
        String password = "newpassword";
        Assertions.assertTrue(userPersistence.changeUserPassword(email, password));
    }
}