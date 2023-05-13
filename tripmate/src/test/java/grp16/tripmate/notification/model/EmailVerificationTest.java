package grp16.tripmate.notification.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailVerificationTest {

    private IVerification verification;

    public EmailVerificationTest() {
        verification = new EmailVerificationMock();
    }

    @Test
    void sendUniqueCodeTest() throws Exception {
        String sendTo = "arpitpatel1501@gmail.com";
        String subject = "Tripmate subject";
        String body = "Tripmate notification body";
        boolean result = verification.sendUniqueCode(sendTo, body, subject);
        Assertions.assertTrue(result);
    }

    @Test
    void sendUniqueCodeNegativeTest() throws Exception {
        String sendTo = null;
        String subject = null;
        String body = null;
        boolean result = verification.sendUniqueCode(sendTo, body, subject);
        Assertions.assertFalse(result);
    }

    @Test
    void verifyCodeTest() throws InvalidTokenException {
        String code = "1111";
        boolean result = verification.verifyCode(code);
        Assertions.assertTrue(result);
    }

    @Test
    void verifyCodeNegativeTest() throws InvalidTokenException {
        String code = "22222";
        boolean result = verification.verifyCode(code);
        Assertions.assertFalse(result);
    }
}