import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerUser {
    final Socket clientSocket;
    final BufferedReader in;
    final PrintWriter out;
    private int id;
    private Manager manager;
    private int fines;

    public ServerUser(Socket clientSocket_in, int id) throws IOException {
       clientSocket = clientSocket_in;
       out = new PrintWriter(clientSocket.getOutputStream());
       in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       this.id = id;
       manager = new Manager(String.valueOf(id));
       manager.setOtherUsername("Anon");
       fines = 0;
    }

    public void send(String message){
        out.println(manager.sendMessage(message));
        out.flush();
    }

    private void sendUnencrypted(String message){
        out.println(message);
        out.flush();
    }

    public String receive() throws IOException {
        try {
            return manager.receiveMessage(in.readLine());
        }
        catch (Exception e){
            fines++;
            if (fines >= 15){
                return "/exit";
            }
            return "";
        }
    }

    private String receiveUnencrypted() throws IOException {
        return in.readLine();
    }

    public boolean incomingMessage() throws IOException {
        return in.ready();
    }

    public void sendServersPublicKey(){
        sendUnencrypted(manager.getPublicKey());
    }

    public void setUsersPublicKey() throws IOException {
        manager.setOtherUserPublicKey(receiveUnencrypted());
    }

    public void setUsersUsername() throws IOException {
        String username = receive();
        if (username != "") {

            manager.setOtherUsername(username);
        }
        else{
            send("Username was empty, asigning \"stupid\" as username");
            manager.setUsername("stupid");
        }
    }

    public String getUsername(){
        return manager.getOtherUsername();
    }

    public String toString(){
        return manager.getOtherUsername();
    }

    public void closeConnection() throws IOException {
        clientSocket.close();
    }
}
