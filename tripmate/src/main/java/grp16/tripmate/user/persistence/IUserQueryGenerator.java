package grp16.tripmate.user.persistence;

import grp16.tripmate.user.model.User;

public interface IUserQueryGenerator {

    String getUserByUsername(String username);

    String getUserByUserID(int userid);

    String createUser(User user);

    String changeUserDetails(User user);
    String changeUserPassword(String email, String password);
}
