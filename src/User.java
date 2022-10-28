import java.math.BigInteger;

public class User {
    private String username;
    private static User onlyInstance;
    private BigInteger n;
    private BigInteger r;
    private BigInteger s;

    private User(String username_in) {
        username = username_in;
        BigInteger[] keyArr = CryptoOps.generateKeys();
        n = keyArr[0];
        r = keyArr[1];
        s = keyArr[2];
        keyArr[0] = BigInteger.valueOf(0);
        keyArr[1] = BigInteger.valueOf(0);
        keyArr[2] = BigInteger.valueOf(0);
    }

    public static User getInstance(){
        if (onlyInstance == null){
            onlyInstance = new User("anonUser");
        }
        return onlyInstance;
    }

    public static User getInstance(String username_in){
        if (onlyInstance == null){
            onlyInstance = new User(username_in);
        }
        return onlyInstance;
    }

    public String decrypt(String encryptedMessage_in){
        return CryptoOps.decryptMessage(encryptedMessage_in, n, s);
    }

    public String getUsername(){
        return username;
    }

    public String getPublicKey(){
        return n + ";" + r;
    }
}
