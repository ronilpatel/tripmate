package grp16.tripmate.user.model.encoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// Ref: https://www.javatpoint.com/how-to-encrypt-password-in-java
public class PasswordEncoder implements IPasswordEncoder {

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        /* MessageDigest instance for hashing using SHA256 */
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static IPasswordEncoder passwordEncoder;

    private PasswordEncoder() {

    }

    public static IPasswordEncoder getInstance() {
        if (passwordEncoder == null) {
            passwordEncoder = new PasswordEncoder();
        }
        return passwordEncoder;
    }


    private static String toHexString(byte[] hash) {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String encodeString(String str) throws NoSuchAlgorithmException {
        return toHexString(getSHA(str));
    }
}