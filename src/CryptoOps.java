import java.math.BigInteger;

public class CryptoOps {

    public boolean isPrime(BigInteger n){
        for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(new BigInteger("2"))) != 1; i = i.add(new BigInteger("1"))){
            //System.out.println("i = " + i);
            if (n.mod(i).equals(new BigInteger("0"))){
                System.out.println(n + " isn't prime, divisible by " + i);
                return false;
            }
        }
        return true;
    }

    public BigInteger getNextPrime(BigInteger n){
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

    public BigInteger RandomBigInteger() {
        BigInteger returnBigInt = new BigInteger("0");
        for (int i = 0; i < 100; i++){
            returnBigInt = returnBigInt.multiply(BigInteger.valueOf(10));
            returnBigInt = returnBigInt.add(BigInteger.valueOf((int) (Math.random()*10)));
        }
        return  returnBigInt;
    }

    public BigInteger RandomBigInteger(int threshold) {
        BigInteger returnBigInt = new BigInteger("0");
        for (int i = 0; i < threshold; i++){
            returnBigInt = returnBigInt.multiply(BigInteger.valueOf(10));
            returnBigInt = returnBigInt.add(BigInteger.valueOf((int) (Math.random()*10)));
        }
        return  returnBigInt;
    }

    public BigInteger bigRelativePrime(BigInteger m){
        BigInteger relPrime = getNextPrime(RandomBigInteger());
        if ((m.gcd(relPrime)).equals(BigInteger.valueOf(1))){
            return relPrime;
        }
        else{
            //System.out.println("Not coprime, recalculating...");
            return bigRelativePrime(m);
        }
    }
}
