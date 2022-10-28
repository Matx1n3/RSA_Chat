import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.nextLine();
        Manager manager = Manager.getInstance(username);
        while (true){
            System.out.print("@" + manager.getUsername() + ": ");
            while(!sc.hasNextLine()){};
            String message = sc.nextLine();
            manager.sendMessage(message);
        }
    }
}