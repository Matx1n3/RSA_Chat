import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.nextLine();
        Manager manager = Manager.getInstance(username);

        try {
            clientSocket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected to the server!");
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            manager.setOtherUserPublicKey(in.readLine());
            System.out.println("Other user's public key has been received and set");
            out.println(manager.getPublicKey());
            out.flush();
            System.out.println("Public key has been sent");
            manager.setOtherUsername(manager.receiveMessage(in.readLine()));
            System.out.println("Other username received");
            out.println(manager.sendMessage(manager.getUsername()));
            out.flush();
            while (true){
                System.out.println("@" + manager.getOtherUsername() + ": " + manager.receiveMessage(in.readLine()));
                if (sc.hasNextLine()) {
                    out.println(manager.sendMessage(sc.nextLine()));
                    out.flush();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
