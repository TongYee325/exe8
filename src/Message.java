import java.util.Arrays;

public class Message {
    private boolean senderSet = false;
    private String title;
    private User sender;
    private String[] recipients;
    private String body;

    public Message(String title, String[] recipients, String body) {
        this.title = title;
        this.recipients = recipients;
        this.body = body;
    }

    public void setSender(User sender) {
        // SAFE ENOUGH
        if (!senderSet) {
            this.sender = sender;
            senderSet = true;
        }
    }

    public String getTitle() { return title; }
    public String[] getRecipients() { return recipients; }

    public String toString() {
        return String.format(
                "Title: %s\nSender: %s\nTo: %s\n\n%s\n",
                title, sender, String.join(", ", recipients), body
        );
    }
}
