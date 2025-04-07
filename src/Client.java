import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Building up the network
        // You know what to do if you have read the Server class
        Network network = new Network();
        network.addServer(new Server("alpha.net", network));
        network.addServer(new Server("beta.net", network));

        System.out.println("Welcome to the network!\n" +
                "c: check available servers\n" +
                "r: register\n" +
                "l: login\n" +
                "s: send message\n" +
                "g: get email(s)\n" +
                "q: logout");
        Scanner scanner = new Scanner(System.in);
        String token = null;
        Server server = null;
        while (true) {
            System.out.print("> ");
            String command = scanner.next();
            switch (command.charAt(0)) {
                case 'c':
                    System.out.println(network);
                    break;
                case 'r':
                    System.out.print("Enter server: ");
                    String servername = scanner.next();
                    Server register_server = Arrays.stream(network.getServers()).filter((s) -> s.toString().equals(servername)).findFirst().orElse(null);
                    if (register_server == null) {
                        System.out.println("Server not found!");
                        break;
                    }
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter nickname: ");
                    String nickname = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    if (register_server.register(username, nickname, password)) {
                        System.out.printf("User registered successfully!\nYour email is %s@%s\n", username, servername);
                    } else {
                        System.out.println("Username already taken!");
                    }
                    break;
                case 'l':
                    System.out.print("Enter address: ");
                    String address = scanner.next();
                    System.out.print("Enter password: ");
                    String pass = scanner.next();
                    String[] parts = address.split("@");
                    Server login_server = Arrays.stream(network.getServers()).filter((server1) -> server1.toString().equals(parts[1])).findFirst().orElse(null);
                    if (login_server == null) {
                        System.out.println("Server not found!");
                        break;
                    }
                    token = login_server.login(address, pass);
                    if (token == null) {
                        System.out.println("Invalid login!");
                    } else {
                        System.out.println("Login successful!");
                        server = login_server;
                    }
                    break;
                case 's':
                    if (token == null && server == null) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.print("Enter title: ");
                    String title = scanner.next();
                    System.out.print("Enter message (end with single '.'): ");
                    String message = inputMessage(scanner);
                    // I'd like it to be multiple recipients
                    // if you've done it, you're a genius
                    System.out.print("Enter recipient: ");
                    String recipient = scanner.next();
                    Message m = new Message(title, new String[]{recipient}, message);
                    System.out.println(server.sendMessage(token, m));
                    break;
                case 'g':
                    if (token == null && server == null) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.println(server.getEmailList(token));
                    System.out.print("Enter index: ");
                    int index = scanner.nextInt();
                    System.out.print(server.getEmail(token, index));
                    break;
                case 'q':
                    System.out.println("Goodbye!");
                    token = null;
                    server = null;
                    break;
                default:
                    System.out.println("Invalid command!");
                    return;
            }
        }
    }

    private static String inputMessage(Scanner scanner) {
        StringBuilder message = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equals(".")) break;
            if (line.isBlank()) continue;
            message.append(line).append("\n");
        }
        return message.toString().strip();
    }
}