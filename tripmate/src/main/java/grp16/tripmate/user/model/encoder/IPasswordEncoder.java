package grp16.tripmate.user.model.encoder;

import java.security.NoSuchAlgorithmException;

public interface IPasswordEncoder {
    String encodeString(String str) throws NoSuchAlgorithmException;
}
