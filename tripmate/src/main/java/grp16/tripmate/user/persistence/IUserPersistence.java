package grp16.tripmate.user.persistence;

import grp16.tripmate.user.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface IUserPersistence {

    boolean insertUser(User user);

    boolean updateUser(User user) throws Exception;

    Map<String, Object> getUserById(int userid) throws Exception;

    Map<String, Object> getUserByUsername(String username) throws NoSuchAlgorithmException;
    boolean changeUserPassword(String email, String password);
}
