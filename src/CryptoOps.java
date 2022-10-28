import java.math.BigInteger;

public abstract class CryptoOps {

    public static boolean isPrime(BigInteger n){
        for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(new BigInteger("2"))) != 1; i = i.add(new BigInteger("1"))){
            //System.out.println("i = " + i);
            if (n.mod(i).equals(new BigInteger("0"))){
                System.out.println(n + " isn't prime, divisible by " + i);
                return false;
            }
        }
        return true;
    }

    public static BigInteger getNextPrime(BigInteger n){
        BigInteger a = n.nextProbablePrime();
        return a;
        /**
         * Ensure it's a prime number
         if (isPrime(a)){
         return a;
         }
         else {
         return getNextPrime(n.add(new BigInteger("1")));
         }
         **/
    }

    public static BigInteger RandomBigInteger() {
        BigInteger returnBigInt = new BigInteger("0");
        for (int i = 0; i < 100; i++){
            returnBigInt = returnBigInt.multiply(BigInteger.valueOf(10));
            returnBigInt = returnBigInt.add(BigInteger.valueOf((int) (Math.random()*10)));
        }
        return  returnBigInt;
    }

    public static BigInteger RandomBigInteger(int threshold) {
        BigInteger returnBigInt = new BigInteger("0");
        for (int i = 0; i < threshold; i++){
            returnBigInt = returnBigInt.multiply(BigInteger.valueOf(10));
            returnBigInt = returnBigInt.add(BigInteger.valueOf((int) (Math.random()*10)));
        }
        return  returnBigInt;
    }

    public static BigInteger bigRelativePrime(BigInteger m){
        BigInteger relPrime = getNextPrime(RandomBigInteger());
        if ((m.gcd(relPrime)).equals(BigInteger.valueOf(1))){
            return relPrime;
        }
        else{
            //System.out.println("Not coprime, recalculating...");
            return bigRelativePrime(m);
        }
    }

    public static BigInteger[] generateKeys(){
        BigInteger p = CryptoOps.getNextPrime(CryptoOps.RandomBigInteger());
        BigInteger q = CryptoOps.getNextPrime(CryptoOps.RandomBigInteger());
        BigInteger n = p.multiply(q);
        BigInteger m = p.add(BigInteger.valueOf(-1)).multiply(q.add(BigInteger.valueOf(-1)));
        BigInteger r = CryptoOps.bigRelativePrime(m);
        BigInteger s = r.modInverse(m);
        BigInteger[] returnArr = new BigInteger[3];
        returnArr[0] = n;
        returnArr[1] = r;
        returnArr[2] = s;
        return returnArr;
    }

    private static int[] encodeMessage(String message){
        char[] messageChar = message.toCharArray();
        int[] encodedMessage = new int[messageChar.length];
        for (int i = 0; i < messageChar.length; i++){
            //System.out.println((int)messageChar[i] + " ");
            encodedMessage[i] = (int)messageChar[i];
        }
        return encodedMessage;
    }

    private static String decodeMessage(BigInteger[] encodedMessage){
        String decodedMessage = "";
        for (int i = 0; i < encodedMessage.length; i++){
            decodedMessage = decodedMessage + (char)encodedMessage[i].intValue();
        }
        return decodedMessage;
    }

    private static BigInteger[] encryptEncodedMessage(int[] encodedMessage, BigInteger n, BigInteger r){
        BigInteger[] encryptedArr = new BigInteger[encodedMessage.length];
        for (int i = 0; i < encodedMessage.length; i++){
            encryptedArr[i] = BigInteger.valueOf(encodedMessage[i]).modPow(r, n);
        }
        return encryptedArr;
    }

    public static String encryptMessage(String message, BigInteger n, BigInteger r){
        return bigIntArrToStr(encryptEncodedMessage(encodeMessage(message), n, r));
    }

    private static String bigIntArrToStr(BigInteger[] bigIntArr){
        String returnStr = "";
        for (int i = 0; i < bigIntArr.length; i++){
            returnStr = returnStr + bigIntArr[i] + ";";
        }
        return returnStr;
    }

    private static BigInteger[] decryptEncodedMessage(BigInteger[] encryptedMessage, BigInteger n, BigInteger s){
        BigInteger[] decryptedMessage = new BigInteger[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++){
            decryptedMessage[i] = encryptedMessage[i].modPow(s, n);
        }
        return decryptedMessage;
    }

    public static String decryptMessage(String encryptedMessage, BigInteger n, BigInteger s){
        return decodeMessage(decryptEncodedMessage(stringToBigIntArr(encryptedMessage), n, s));
    }

    private static BigInteger[] stringToBigIntArr(String stringEncryptedMessage_in){
        String[] sepStr = stringEncryptedMessage_in.split(";");
        BigInteger[] returnArr = new BigInteger[sepStr.length];
        for (int i = 0; i < returnArr.length; i++){
            returnArr[i] = new BigInteger(sepStr[i]);
        }
        return returnArr;
    }
}
