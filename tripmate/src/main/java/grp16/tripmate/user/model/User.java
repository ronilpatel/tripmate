package grp16.tripmate.user.model;

import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.persistence.UserDbColumnNames;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;
import grp16.tripmate.user.model.encoder.PasswordEncoder;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.user.model.factory.UserFactory;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User implements IUser {
    private final ILogger logger;

    private String username;
    private String password;
    private int id;
    private String firstname;
    private String lastname;
    private Date birthDate;
    private String gender;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) throws ParseException {
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
    }

    public void setBirthDateAsDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        password = PasswordEncoder.getInstance().encodeString(password);
        this.password = password;
    }

    public void setPasswordWithOutEncoding(String password) {
        this.password = password;
    }

    public User() {
        logger = UserFactory.getInstance().makeLogger(this);
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", id=" + id + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", birthDate=" + birthDate + ", gender='" + gender + '\'' + '}';
    }

    public boolean validateUser(IUserPersistence userDatabase, IPasswordEncoder passwordEncoder) throws InvalidUsernamePasswordException, NoSuchAlgorithmException {
        User userFromDb = mapToUser(userDatabase.getUserByUsername(this.getUsername()));
        boolean isValidUser = userFromDb.getUsername().equals(this.getUsername()) && userFromDb.getPassword().equals(this.getPassword());
        if (isValidUser) {
            logger.info("Current User: " + userFromDb);
            SessionManager.getInstance().setValue(UserDbColumnNames.ID, userFromDb.getId());
            SessionManager.getInstance().setValue(UserDbColumnNames.USERNAME, userFromDb.getUsername());
            SessionManager.getInstance().setValue(UserDbColumnNames.FIRSTNAME, userFromDb.getFirstname());
            SessionManager.getInstance().setValue(UserDbColumnNames.LASTNAME, userFromDb.getLastname());
        } else {
            throw new InvalidUsernamePasswordException();
        }
        return true;
    }

    @Override
    public boolean createUser(IUserPersistence userDatabase) {
        return userDatabase.insertUser(this);
    }

    public String dateToSQLDate(Date date) {
        if (date != null) {
            // Ref: https://theopentutorials.com/examples/java/util/date/how-to-convert-java-util-date-to-mysql-date-format/
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
        return "";
    }

    public boolean changeUserDetails(IUserPersistence userDatabase) throws Exception {
        this.setId(SessionManager.getInstance().getLoggedInUserId());
        return userDatabase.updateUser(this);
    }

    public User getUserById(IUserPersistence userDatabase, int userId) throws Exception {
        return mapToUser(userDatabase.getUserById(userId));
    }

    private List<User> listToUsers(List<Map<String, Object>> results) {
        List<User> users = new ArrayList<>();
        for (Map<String, Object> result : results) {
            users.add(mapToUser(result));
        }
        return users;
    }

    private User mapToUser(Map<String, Object> result) {
        User user = (User) UserFactory.getInstance().makeNewUser();
        user.setUsername((String) result.get(UserDbColumnNames.USERNAME));
        user.setPasswordWithOutEncoding((String) result.get(UserDbColumnNames.PASSWORD));
        user.setId((Integer) result.get(UserDbColumnNames.ID));
        user.setFirstname((String) result.get(UserDbColumnNames.FIRSTNAME));
        user.setLastname((String) result.get(UserDbColumnNames.LASTNAME));
        user.setBirthDateAsDate((Date) result.get(UserDbColumnNames.BIRTHDATE));
        user.setGender((String) result.get(UserDbColumnNames.GENDER));
        return user;
    }

    @Override
    public boolean checkUserExist(IUserPersistence userDatabase, String email) throws Exception {
        Map<String, Object> result = userDatabase.getUserByUsername(email);
        if (result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean changeUserPassword(IUserPersistence userDatabase, String email, String password) throws Exception {
        return userDatabase.changeUserPassword(email, password);
    }
}