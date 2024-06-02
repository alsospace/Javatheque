package fr.javatheque.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A utility class for password encryption and verification.
 */
public class PasswordUtil {

    /**
     * Generates a random salt.
     *
     * @return The generated salt.
     */
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * Encrypts the given password.
     *
     * @param password The password to encrypt.
     * @return The encrypted password.
     */
    public static String encryptPassword(String password) {
        try {
            byte[] salt = generateSalt();

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(salt);

            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifies the given password against the encrypted password.
     *
     * @param password          The password to verify.
     * @param encryptedPassword The encrypted password.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String password, String encryptedPassword) {
        try {
            String[] parts = encryptedPassword.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hashedPassword = Base64.getDecoder().decode(parts[1]);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(salt);
            byte[] hashedAttempt = messageDigest.digest(password.getBytes());

            return MessageDigest.isEqual(hashedPassword, hashedAttempt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}