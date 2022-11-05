import java.math.BigInteger;

public class OtherUser {
    private BigInteger n;
    private BigInteger r;
    private String username;

    public OtherUser(String username_in){
        username = username_in;
    }

    public void setUsername(String username_in){
        username = username_in;
    }

    public void setPublicKey(String publicKey_in){
        String[] publicKeyArr = publicKey_in.split(";");
        n = new BigInteger(publicKeyArr[0]);
        r = new BigInteger(publicKeyArr[1]);
    }

    public String encrypt(String message){
        return CryptoOps.encryptMessage(message, n, r);
    }

    public String getUsername(){
        return username;
    }
}
