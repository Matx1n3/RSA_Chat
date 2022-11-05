public class Manager {
    private User user;
    private OtherUser otherUser;

    public Manager(){
        user = new User("stillAnon");
        otherUser = new OtherUser("notNameYet");
    }

    public Manager(String username_in){
        user = new User(username_in);
        otherUser = new OtherUser("notNameYet");
    }

    public String sendMessage(String message_in){
        return otherUser.encrypt(message_in);
    }

    public String receiveMessage(String message_in){
        return user.decrypt(message_in);
    }

    public String getUsername(){
        return user.getUsername();
    }

    public void setOtherUserPublicKey(String publicKey){
        otherUser.setPublicKey(publicKey);
    }

    public String getPublicKey(){
        return user.getPublicKey();
    }

    public void setOtherUsername(String username_in){
        System.out.println("Setting username to " + username_in);
        otherUser.setUsername(username_in);
    }

    public String getOtherUsername(){
        return otherUser.getUsername();
    }
}
