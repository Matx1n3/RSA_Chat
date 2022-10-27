import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        System.out.println("Mensaje: ");
        String mensaje = sc.next();
        char[] mensajeChar = mensaje.toCharArray();
        int[] mensajeCodificado = new int[mensajeChar.length];
        for (int i = 0; i < mensajeChar.length; i++){
                System.out.println((int)mensajeChar[i] + " ");
                mensajeCodificado[i] = (int)mensajeChar[i];
        }
        //Generar clave RSA
        BigInteger bigNum;
        bigNum = RandomBigInteger();
        System.out.println(getNextPrime(bigNum));
        BigInteger p = getNextPrime(bigNum);
        System.out.println("p = " + p);


    }

    static boolean isPrime(BigInteger n){
        for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(new BigInteger("2"))) != 1; i = i.add(new BigInteger("1"))){
            //System.out.println("i = " + i);
            if (n.mod(i).equals(new BigInteger("0"))){
                System.out.println(n + " isn't prime, divisible by " + i);
                return false;
            }
        }
        return true;
    }

    static BigInteger getNextPrime(BigInteger n){
        BigInteger a = n.nextProbablePrime();
        return a;
        /**
        if (isPrime(a)){
            return a;
        }
        else {
            return getNextPrime(n.add(new BigInteger("1")));
        }
         **/
    }

    static BigInteger RandomBigInteger() {
        Random rand = new Random();
        int max = 999999999;
        int min = 50000000;
        BigInteger n = new BigInteger(String.valueOf((int)(Math.random()*(max-min+1)+min)));
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.bitLength() < 900 ) {
            result = result.multiply(new BigInteger(n.bitLength(), rand));
            System.out.println("result = " + result);
        }
        return result;
    }
}