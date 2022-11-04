import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        String serverName = "server00";

        ArrayList<ServerUser> userArrayList = new ArrayList<ServerUser>();

        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        //Manager manager = Manager.getInstance(username);
        serverSocket = new ServerSocket(5000);
        System.out.println("Server is listening on port 5000");

        while (true) {
            try {
                if (serverSocket.isBound()) {
                    userArrayList.add(new ServerUser(serverSocket.accept(), userArrayList.size()));
                    System.out.println("A new user has connected to the server!");
                    userArrayList.get(userArrayList.size() - 1).sendServersPublicKey();
                    System.out.println("Public key has been sent");
                    userArrayList.get(userArrayList.size() - 1).setUsersPublicKey();
                    System.out.println("Other user's public key has been received and set");
                    userArrayList.get(userArrayList.size() - 1).send(serverName);
                    System.out.println("Server name sent");
                    userArrayList.get(userArrayList.size() - 1).setUsersUsername();
                    System.out.println("User's username received");
                    System.out.println("@" + userArrayList.get(userArrayList.size() - 1).getUsername() + " has joined the room.");
                }

                for (int i = 0; i < userArrayList.size(); i++) {
                    if (userArrayList.get(i).incomingMessage()) {
                        String message = userArrayList.get(i).receive();
                        for (int j = 0; j < i; j++) {
                            userArrayList.get(j).send(message);
                        }
                        for (int j = i++; j < userArrayList.size(); j++) {
                            userArrayList.get(j).send(message);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
