package hg.community.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isMatch(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }
}
