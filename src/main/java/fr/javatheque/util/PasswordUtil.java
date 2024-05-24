package fr.javatheque.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String encryptPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = hashPassword(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        return Base64.getEncoder().encodeToString(concat(salt, hash));
    }

    public static boolean verifyPassword(String passwordToVerify, String hashedPassword) {
        byte[] decodedHash = Base64.getDecoder().decode(hashedPassword);
        byte[] salt = extractSalt(decodedHash);
        byte[] hash = hashPassword(passwordToVerify.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        return MessageDigest.isEqual(hash, extractHash(decodedHash));
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA256");
            digest.reset();
            digest.update(salt);
            byte[] hash = digest.digest(new String(password).getBytes());
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                hash = digest.digest(concat(hash, new byte[]{(byte) i}));
            }
            return ByteUtils.truncate(hash, keyLength / 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithme de hachage non supporté", e);
        }
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static byte[] extractSalt(byte[] decodedHash) {
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(decodedHash, 0, salt, 0, SALT_LENGTH);
        return salt;
    }

    private static byte[] extractHash(byte[] decodedHash) {
        byte[] hash = new byte[decodedHash.length - SALT_LENGTH];
        System.arraycopy(decodedHash, SALT_LENGTH, hash, 0, hash.length);
        return hash;
    }

    private static class ByteUtils {
        public static byte[] truncate(byte[] data, int length) {
            byte[] truncated = new byte[length];
            System.arraycopy(data, 0, truncated, 0, length);
            return truncated;
        }
    }
}
