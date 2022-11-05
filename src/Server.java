import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    static ServerSocket serverSocket = null;
    static  Socket clientSocket = null;
    static  BufferedReader in = null;
    static  PrintWriter out = null;
    static String serverName = "server00";

    static ArrayList<ServerUser> userArrayList;
    public static void main(String[] args) throws IOException {

        userArrayList = new ArrayList<ServerUser>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Server name: ");
        serverName = sc.nextLine();
        System.out.print("Enter the port: ");
        String port = sc.nextLine();
        serverSocket = new ServerSocket(Integer.valueOf(port));
        System.out.println("Server is listening on port " + port);

        Thread userWelcomer = new Thread() {
            public void run() {
                while (true) {
                    try {
                        if (serverSocket.isBound()) {
                            userArrayList.add(new ServerUser(serverSocket.accept(), userArrayList.size() - 1));
                            userArrayList.get(userArrayList.size() - 1).sendServersPublicKey();
                            userArrayList.get(userArrayList.size() - 1).setUsersPublicKey();
                            userArrayList.get(userArrayList.size() - 1).send(serverName);
                            userArrayList.get(userArrayList.size() - 1).setUsersUsername();
                            Boolean unameTaken = false;
                            for (int i = 0; i < userArrayList.size()-1; i++) {
                                if (userArrayList.get(i).getUsername().equals(userArrayList.get(userArrayList.size() - 1).getUsername())){
                                    userArrayList.get(userArrayList.size()-1).send("/leftUsername is already taken");
                                    userArrayList.get(userArrayList.size()-1).closeConnection();
                                    userArrayList.remove(userArrayList.size()-1);
                                    unameTaken = true;
                                }
                            }
                            if (!unameTaken) {
                                System.out.println("@" + userArrayList.get(userArrayList.size() - 1).getUsername() + " has joined the room.");
                                userArrayList.get(userArrayList.size() - 1).send("Welcome to " + serverName + "!");
                                userArrayList.get(userArrayList.size() - 1).send("Online users: " + userArrayList);
                                for (int i = 0; i < userArrayList.size() - 1; i++) {
                                    userArrayList.get(i).send("/joined@" + userArrayList.get(userArrayList.size() - 1).getUsername() + " joined the room.");
                                }
                            }
                        }
                        sleep(100);
                    } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                    }

                }
            }
        };

        Thread messageManager = new Thread(){
            public void run(){
                while (true) {
                    try {
                        for (int i = 0; i < userArrayList.size(); i++) {
                            if (userArrayList.get(i).incomingMessage()) {
                                //System.out.println("User " + userArrayList.get(i).getUsername() + " has sent a message.");
                                String message = userArrayList.get(i).receive();
                                switch (message) {
                                    case "":
                                        break;
                                    case "/usersonline":
                                        userArrayList.get(i).send("Online users: " + userArrayList);
                                        break;
                                    case "/exit":
                                        System.out.println("@" + userArrayList.get(i).getUsername() + " has left the room.");
                                        for (int j = 0; j < i; j++) {
                                            userArrayList.get(j).send("/left@" + userArrayList.get(i).getUsername() + " has left the room.");
                                        }
                                        for (int j = i + 1; j < userArrayList.size(); j++) {
                                            userArrayList.get(j).send("/left@" + userArrayList.get(i).getUsername() + " has left the room.");
                                        }
                                        userArrayList.get(i).send("/leftBye!");
                                        userArrayList.get(i).closeConnection();
                                        userArrayList.remove(i);
                                        break;
                                    default:
                                        System.out.println("@" + userArrayList.get(i).getUsername() + ": " + message);
                                        for (int j = 0; j < i; j++) {
                                            userArrayList.get(j).send("/message@" + userArrayList.get(i).getUsername() + ": " + message);
                                        }
                                        for (int j = i + 1; j < userArrayList.size(); j++) {
                                            userArrayList.get(j).send("/message@" + userArrayList.get(i).getUsername() + ": " + message);
                                        }
                                }
                            }
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        userWelcomer.start();
        messageManager.start();
    }
}
