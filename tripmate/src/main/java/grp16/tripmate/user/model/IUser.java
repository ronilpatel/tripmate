package grp16.tripmate.user.model;

import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;

public interface IUser {

    boolean validateUser(IUserPersistence userDatabase, IPasswordEncoder passwordEncoder) throws Exception;

    boolean createUser(IUserPersistence userDatabase) throws Exception;

    User getUserById(IUserPersistence userDatabase, int loggedInUserId) throws Exception;
    boolean checkUserExist(IUserPersistence userDatabase, String email) throws Exception;
    boolean changeUserPassword(IUserPersistence userDatabase, String email, String password) throws Exception;
}