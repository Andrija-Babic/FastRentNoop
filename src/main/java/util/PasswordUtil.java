package util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Represents the PasswordUtil component used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class PasswordUtil {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    // =====================================================
    // HASH PASSWORD
    // =====================================================

    /**
     * Performs the hashpassword operation.
     *
     * @param password supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static String hashPassword(
            String password) {

        try {

            byte[] salt =
                    new byte[SALT_LENGTH];

            SecureRandom random =
                    new SecureRandom();

            random.nextBytes(salt);

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            ITERATIONS,
                            KEY_LENGTH
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] hash =
                    factory.generateSecret(spec)
                            .getEncoded();

            spec.clearPassword();

            return ITERATIONS
                    + ":"
                    + Base64.getEncoder()
                    .encodeToString(salt)
                    + ":"
                    + Base64.getEncoder()
                    .encodeToString(hash);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Greška prilikom hashiranja lozinke.",
                    e
            );
        }
    }

    // =====================================================
    // VERIFY PASSWORD
    // =====================================================

    /**
     * Performs the verifypassword operation.
     *
     * @param password supplied value used by this operation
     * @param storedHash supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean verifyPassword(
            String password,
            String storedHash) {

        if (password == null
                || storedHash == null
                || storedHash.isBlank()) {

            return false;
        }

        try {

            String[] parts =
                    storedHash.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations =
                    Integer.parseInt(parts[0]);

            byte[] salt =
                    Base64.getDecoder()
                            .decode(parts[1]);

            byte[] expectedHash =
                    Base64.getDecoder()
                            .decode(parts[2]);

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            salt,
                            iterations,
                            expectedHash.length * 8
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] actualHash =
                    factory.generateSecret(spec)
                            .getEncoded();

            spec.clearPassword();

            if (actualHash.length
                    != expectedHash.length) {

                return false;
            }

            int difference = 0;

            for (int i = 0;
                 i < actualHash.length;
                 i++) {

                difference |=
                        actualHash[i]
                                ^ expectedHash[i];
            }

            return difference == 0;

        } catch (Exception e) {

            return false;
        }
    }
}
