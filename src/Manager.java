public class Manager {
    private static User user;
    private static OtherUser otherUser;
    private static Manager onlyInstance = null;

    private Manager(){
        user = User.getInstance();
        otherUser = OtherUser.getnstance();
    }

    private Manager(String username_in){
        user = User.getInstance(username_in);
        otherUser = OtherUser.getnstance();
    }

    public static Manager getInstance(){
        if (onlyInstance == null){
            onlyInstance = new Manager();
        }
        return onlyInstance;
    }

    public static Manager getInstance(String username_in){
        if (onlyInstance == null){
            onlyInstance = new Manager(username_in);
        }
        return onlyInstance;
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
}
