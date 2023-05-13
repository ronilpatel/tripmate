package grp16.tripmate.user.persistence;

import grp16.tripmate.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

class UserQueryGeneratorTest {

    IUserQueryGenerator queryGenerator = UserQueryGenerator.getInstance();

    @Test
    void getUserByUsername() {

        String expectedQuery = "SELECT `id`,    `first_name`,    `last_name`,    `email`,    `password`,    `birthdate`,    `gender` FROM `User` where email = 'email@mail.com'";


        Assertions.assertEquals(expectedQuery, queryGenerator.getUserByUsername("email@mail.com"));
    }

    @Test
    void expectedQuery() {
        String expectedQuery = "SELECT `id`,    `first_name`,    `last_name`,    `email`,    `password`,    `birthdate`,    `gender`FROM `User` where id = 1";
        Assertions.assertEquals(expectedQuery, queryGenerator.getUserByUserID(1));

    }

    @Test
    void createUser() throws NoSuchAlgorithmException, ParseException {
        User user = new User();

        user.setId(1);
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setUsername("username");
        user.setPassword("password");
        user.setBirthDate("1999-02-01");
        user.setGender("Male");

        String expectedQuery = "INSERT INTO `User`" +
                "(" + UserDbColumnNames.ID + "," +
                UserDbColumnNames.FIRSTNAME + "," +
                UserDbColumnNames.LASTNAME + "," +
                UserDbColumnNames.USERNAME + "," +
                UserDbColumnNames.PASSWORD + "," +
                UserDbColumnNames.BIRTHDATE + "," +
                UserDbColumnNames.GENDER + ") " +
                "VALUES" +
                " (\"" + 1 + "\"," +
                "\"" + "firstname" + "\"," +
                "\"" + "lastname" + "\"," +
                "\"" + "username" + "\"," +
                "\"" + user.getPassword() + "\"," +
                "\"" + user.dateToSQLDate(user.getBirthDate()) + "\"," +
                "\"" + "Male" + "\");";


        Assertions.assertEquals(expectedQuery, queryGenerator.createUser(user));


    }

    @Test
    void changeUserDetails() throws NoSuchAlgorithmException {
        User user = new User();
        user.setPassword("password");
        user.setGender("Female");
        user.setFirstname("first_name");
        user.setLastname("last_name");
        user.setId(1);

        String expectedQuery = "update " +
                UserDbColumnNames.TABLE_NAME + " set " +
                UserDbColumnNames.PASSWORD + " = '" + user.getPassword() + "'" + "," +
                UserDbColumnNames.GENDER + " = '" + "Female" + "'" + "," +
                UserDbColumnNames.FIRSTNAME + " = '" + "first_name" + "'" + "," +
                UserDbColumnNames.LASTNAME + " = '" + "last_name" + "'" +
                " where " +
                UserDbColumnNames.ID + " = " + 1;


        Assertions.assertEquals(expectedQuery, queryGenerator.changeUserDetails(user));
    }
}