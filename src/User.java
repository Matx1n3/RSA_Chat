import java.math.BigInteger;

public class User {
    private String username;
    private BigInteger n;
    private BigInteger r;
    private BigInteger s;

    public User(String username_in) {
        username = username_in;
        BigInteger[] keyArr = CryptoOps.generateKeys();
        n = keyArr[0];
        r = keyArr[1];
        s = keyArr[2];
        keyArr[0] = BigInteger.valueOf(0);
        keyArr[1] = BigInteger.valueOf(0);
        keyArr[2] = BigInteger.valueOf(0);
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
