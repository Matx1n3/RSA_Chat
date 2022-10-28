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
            while (true){
                out.println(manager.sendMessage(sc.nextLine()));
                out.flush();
                System.out.println(manager.receiveMessage(in.readLine()));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
