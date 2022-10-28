import java.math.BigInteger;

public class Encrypter {
    //Singleton instance
    static private Encrypter onlyInstance = null;
    //Class for making ops
    private CryptoOps op = new CryptoOps();
    //Public key
    private BigInteger n;
    private BigInteger r;
    //Private key
    private BigInteger s;
    //Other variables
    private BigInteger p;
    private BigInteger q;
    private BigInteger m;

    private Encrypter(){
        p = op.getNextPrime(op.RandomBigInteger());
        q = op.getNextPrime(op.RandomBigInteger());
        n = p.multiply(q);
        m = p.add(BigInteger.valueOf(-1)).multiply(q.add(BigInteger.valueOf(-1)));
        r = op.bigRelativePrime(m);
        s = r.modInverse(m);
    }

    public static Encrypter getInstance(){
        if (onlyInstance == null){
            onlyInstance = new Encrypter();
        }
        return  onlyInstance;
    }

    private int[] encodeMessage(String message){
        char[] messageChar = message.toCharArray();
        int[] encodedMessage = new int[messageChar.length];
        for (int i = 0; i < messageChar.length; i++){
            //System.out.println((int)messageChar[i] + " ");
            encodedMessage[i] = (int)messageChar[i];
        }
        return encodedMessage;
    }

    private String decodeMessage(BigInteger[] encodedMessage){
        String decodedMessage = "";
        for (int i = 0; i < encodedMessage.length; i++){
            decodedMessage = decodedMessage + (char)encodedMessage[i].intValue();
        }
        return decodedMessage;
    }

    private BigInteger[] encryptEncodedMessage(int[] encodedMessage){
        BigInteger[] encryptedArr = new BigInteger[encodedMessage.length];
        for (int i = 0; i < encodedMessage.length; i++){
            encryptedArr[i] = BigInteger.valueOf(encodedMessage[i]).modPow(r, n);
        }
        return encryptedArr;
    }

    public BigInteger[] encryptMessage(String message){
        return encryptEncodedMessage(encodeMessage(message));
    }

    private BigInteger[] decryptEncodedMessage(BigInteger[] encryptedMessage){
        BigInteger[] decryptedMessage = new BigInteger[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++){
            decryptedMessage[i] = encryptedMessage[i].modPow(s, n);
        }
        return decryptedMessage;
    }

    public String decryptMessage(BigInteger[] encryptedMessage){
        return decodeMessage(decryptEncodedMessage(encryptedMessage));
    }

}
