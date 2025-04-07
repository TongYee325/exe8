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
                   correct = true;
               }
               break;
           }
        }
        if(!found||!correct) return null;



        // Add the user to the loggedInUsers map
        // and create a token for the user
        String token = UUID.randomUUID().toString();

        loggedInUsers.put(token, loggedInUser);


        return token;
    }

    public String sendMessage(String token, Message message) {
        // TODO: verify the token is valid
        // if not, return "Invalid token"

        if(loggedInUsers.get(token)==null)return "Invalid token";

        // TODO: set the sender of the message

        message.setSender(loggedInUsers.get(token));

        // TODO: send the message by calling the send method

        if(this.send(message)) return "Message sent successfully!";
        else return "Message not sent!";
    }

    public String getEmailList(String token) {
        // TODO: verify the token is valid

        if(loggedInUsers.get(token)==null)return "Invalid token";

        // TODO: get the user from the tokens

        User loggedInUser = loggedInUsers.get(token);

        // TODO: get the user's inbox and return the list of messages
        // Hint: only return the titles of the messages

        StringBuilder info = new StringBuilder();
        for(int i =0;i<inbox.get(loggedInUser.getEmail()).size();i++)
        {
            info.append("["+i+"]"+" ");
            info.append(inbox.get(loggedInUser.getEmail()).get(i).getTitle());
            info.append("\n");
        }
        return info.toString();
    }

    public String getEmail(String token, int index) {
        // TODO: verify the token is valid

        if(loggedInUsers.get(token)==null)return "Invalid token";

        // TODO: check index and return the message at the index
        // Hint: toString is already implemented in the Message class

        return inbox.get(loggedInUsers.get(token).getEmail()).get(index).toString();

    }

    // Method below are for the server to send and receive messages
    public boolean send(Message message) {
        // TODO: get the recipients of the message
        // for each recipient, simply call the receive method
        // in the recipient's server.
        // If the receive method returns false, return false
        // Note that the recipient's server can be found in the network


        boolean send = false;
        for(String recipient : message.getRecipients()) {
            for(Server eachServer : network.getServers()) {
                if(eachServer.toString().equals(recipient.split("@")[1])) {
                    send = eachServer.receive(message,recipient);
                }
            }
        }

        return send;
    }

    public boolean receive(Message message, String recipient) {
        // TODO: verify the recipient is in the users list
        // then add the message to the recipient's inbox
        // if not, return false

        boolean receive = false;
        for(User eachUser : users)
        {
            if(eachUser.getEmail().equals(recipient))
            {
                inbox.get(eachUser.getEmail()).add(message);
                receive = true;
            }
        }

        return receive;
    }

    public String toString() { return domain; }
}
