package grp16.tripmate.user.persistence;

import grp16.tripmate.user.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserPersistenceMock {
    private static final Map<Integer, User> userDB = new HashMap<>();

    public boolean insertUser(User user) {
        return userDB.put(user.getId(), user) == null;
    }

    public boolean updateUser(User user) throws Exception {
        return userDB.put(user.getId(), user) != null;
    }

    public User getUserById(int userid) throws Exception {
        return userDB.get(userid);
    }

    public User getUserByUsername(String username) throws NoSuchAlgorithmException {
        for (int key : userDB.keySet()) {
            if (userDB.get(key).getUsername().equals(username)) {
                return userDB.get(key);
            }
        }
        return null;
    }

    public boolean changeUserPassword(String email, String password) throws NoSuchAlgorithmException {
        for (int key : userDB.keySet()) {
            if (userDB.get(key).getUsername().equals(email)) {
                userDB.get(key).setPassword(password);
                return true;
            }
        }
        return true;
    }
}