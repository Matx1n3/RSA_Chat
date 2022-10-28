import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        Encrypter enc = Encrypter.getInstance();
        System.out.println("Mensaje: ");
        String message = sc.nextLine();
        System.out.println("Encripted message: ");
        BigInteger[] encMes = enc.encryptMessage(message);
        for (int i = 0; i < encMes.length; i++){
            System.out.println(encMes[i]);
        }
        System.out.println(enc.decryptMessage(encMes));




    }







}