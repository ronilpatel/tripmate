package grp16.tripmate.user.model.encoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    IPasswordEncoder passwordEncoder;

    PasswordEncoderTest() {
        passwordEncoder = PasswordEncoder.getInstance();
    }

    @Test
    void encodeStringTest() throws NoSuchAlgorithmException {
        Assertions.assertEquals("771c7c0fb9e19c9b6d0755c25ae10a890d9302199741c57b18af716a45219883", passwordEncoder.encodeString("P@ss12"));
    }
}