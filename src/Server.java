import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;

        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        Manager manager = Manager.getInstance(username);

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is listening on port 5000");
            clientSocket = serverSocket.accept();
            System.out.println("A new user has connected to the server!");
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println(manager.getPublicKey());
            out.flush();
            System.out.println("Public key has been sent");
            manager.setOtherUserPublicKey(in.readLine());
            System.out.println("Other user's public key has been received and set");
            out.println(manager.sendMessage(manager.getUsername()));
            out.flush();
            System.out.println("Username sent");
            manager.setOtherUsername(manager.receiveMessage(in.readLine()));
            System.out.println("Other username received");

            while (true){
                if (sc.hasNext()) {
                    out.println(manager.sendMessage(sc.nextLine()));
                    out.flush();
                }
                System.out.println("@" + manager.getOtherUsername() + ": " + manager.receiveMessage(in.readLine()));

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
