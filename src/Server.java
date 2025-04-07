import java.util.*;

public class Server {
    private List<User> users;
    private String domain;
    private Network network;
    private HashMap<String, List<Message>> inbox;
    private HashMap<String, User> loggedInUsers;

    public Server(String domain, Network network) {
        this.domain = domain;
        this.network = network;
        this.users = new ArrayList<>();
        this.inbox = new HashMap<>();
        this.loggedInUsers = new HashMap<>();
    }

    // Method below are for the server to manage users
    public boolean register(String username, String nickname, String password) {
        // TODO: Check if the username is already taken
        // because the address for the user will be username@domain

        for(User user : users) {
            if(user.getName().equals(username)) {return false;}
        }

        // TODO: Create a new User object and add it to the users list
        // nickname is the display name of the user
        // Remember to create an empty list for the user's inbox
        // check HashMap.put

        User new_user = new User(nickname, username+"@"+domain, password);
        users.add(new_user);
        List<Message> messages = new ArrayList<>();
        inbox.put(new_user.getEmail(),messages);


        return true;
    }

    public String login(String address, String password) {
        // TODO: Check if the address exists and the password is correct
        // If not, return null

        boolean found = false;
        boolean correct = false;
        User loggedInUser = null;
        for(User user: users) {
           if(user.getEmail().equals(address))
           {
               found = true;
               if(user.checkPassword(password))
               {
                   loggedInUser = user;
               }
               break;
           }
        }
        if(!found) return null;



        // Add the user to the loggedInUsers map
        // and create a token for the user
        String token = UUID.randomUUID().toString();

        loggedInUsers.put(token, loggedInUser);


        return token;
    }

    public String sendMessage(String token, Message message) {
        // TODO: verify the token is valid
        // if not, return "Invalid token"

        loggedInUsers.get(token);


        // TODO: set the sender of the message

        // TODO: send the message by calling the send method

        return "Message sent successfully!";
    }

    public String getEmailList(String token) {
        // TODO: verify the token is valid

        // TODO: get the user from the tokens

        // TODO: get the user's inbox and return the list of messages
        // Hint: only return the titles of the messages
        return "Email list";
    }

    public String getEmail(String token, int index) {
        // TODO: verify the token is valid

        // TODO: check index and return the message at the index
        // Hint: toString is already implemented in the Message class
        return "Message";
    }

    // Method below are for the server to send and receive messages
    public boolean send(Message message) {
        // TODO: get the recipients of the message
        // for each recipient, simply call the receive method
        // in the recipient's server.
        // If the receive method returns false, return false
        // Note that the recipient's server can be found in the network
        return true;
    }

    public boolean receive(Message message, String recipient) {
        // TODO: verify the recipient is in the users list
        // then add the message to the recipient's inbox
        // if not, return false
        return true;
    }

    public String toString() { return domain; }
}
