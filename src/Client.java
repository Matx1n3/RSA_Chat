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
            String otherPKey = in.readLine();
            System.out.println("Received otherpKey = " + otherPKey);
            manager.setOtherUserPublicKey(otherPKey);
            System.out.println("Other user's public key has been received and set");
            out.println(manager.getPublicKey());
            out.flush();
            System.out.println("Public key has been sent");
            System.out.println("Public key = " + manager.getPublicKey());
            manager.setOtherUsername(manager.receiveMessage(in.readLine()));
            System.out.println("Servername received");
            out.println(manager.sendMessage(manager.getUsername()));
            out.flush();
            System.out.println("Username sent");
            System.out.println("Sent username = " + manager.getUsername());

            while (true){
                if (in.ready()){
                    System.out.println(manager.receiveMessage(in.readLine()));
                }
                if (sc.hasNextLine()){
                    out.println(sc.nextLine());
                    out.flush();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
