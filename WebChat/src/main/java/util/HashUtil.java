package util;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Filip
 * 
 */

public final class HashUtil {

    private static final int iterations = 100;
    private static final int keyLen = 64;
    /*
     * Returns a hashed password
    */
    public static String hashPassword(String password, byte[] salt) {
        SecretKeyFactory f;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, keyLen));
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } 
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Returns a new random salt.
    */
    public static byte[] getNewSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }
    /*
     * Converts the byte[] salt to a string
    */
    public static String saltToString(byte[] salt){
        return Base64.getEncoder().encodeToString(salt);
    }
    
     /*
     * Converts the string salt to a byte[]
    */
    public static byte[] saltToByte(String salt){
        return Base64.getDecoder().decode(salt);
    }	
}
