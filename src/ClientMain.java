import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    static Socket clientSocket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static Scanner sc;
    static Manager manager = new Manager();
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_CYAN = "\u001B[36m";

    static String serverIP;
    static String port;

    public static void main(String[] args) {

        sc = new Scanner(System.in);

        System.out.println("------------WELCOME TO RSA_CHAT-------------");
        System.out.println("1. Connect to custom server");
        System.out.println("2. Connect for stored servers");
        System.out.println("3. Look for existing room in local network");
        System.out.println("--------------------------------------------");

        switch (sc.nextLine()){
            default:
            //case 1:
                System.out.print("Enter the server's IP address: ");
                serverIP = sc.nextLine();
                System.out.print("Enter the port: ");
                port = sc.nextLine();
                System.out.print("Username: ");
                manager.setUsername(sc.nextLine());
                break;
            //case 2:
                //Implement
                //break;
            //case 3:
                //Implement
                //break;

        }
        try {
            System.out.println("Conecting...");
            clientSocket = new Socket(serverIP, Integer.valueOf(port));
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            manager.setOtherUserPublicKey(in.readLine());
            out.println(manager.getPublicKey());
            out.flush();
            manager.setOtherUsername(manager.receiveMessage(in.readLine()));
            out.println(manager.sendMessage(manager.getUsername()));
            out.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Thread ts = new Thread(){
            public void run() {
                while(true) {
                    //System.out.print("@" + manager.getUsername() + ": ");
                    if (sc.hasNextLine()) {
                        sendMessage(sc.nextLine());
                    }
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread tc = new Thread(){
            public void run(){
                while(true) {
                    try {
                        if (in.ready()) {
                            String message = manager.receiveMessage(in.readLine());
                            String[] splitedMessage = message.split(":");
                            if (message.startsWith("/left")){
                                System.out.println(ANSI_RED + message.replace("/left", "") + ANSI_RESET);
                            }
                            else if (message.startsWith("/message")){
                                System.out.println(ANSI_YELLOW + splitedMessage[0].replace("/message", "") + ":" + ANSI_RESET + splitedMessage[1]);
                            }
                            else if (message.startsWith("/joined")){
                                System.out.println(ANSI_GREEN + message.replace("/joined", "") + ANSI_RESET);
                            }
                            else {
                                System.out.println(ANSI_CYAN + message + ANSI_RESET);
                            }

                        }
                        sleep(100);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        tc.start();
        ts.start();
    }

    public static void sendMessage(String message){
        out.println(manager.sendMessage(message));
        out.flush();
    }
}