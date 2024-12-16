package backend;

import java.io.IOException;
import java.time.LocalDateTime;
import org.json.*;

public class Message {
    private String content;          // Text of the message
    private User sender;             // User who sent the message
    private LocalDateTime timestamp; // Time the message was sent

    // Create time from frontend
    public Message(User sender, String message) {
        this.content = message;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }

    // Load message from database
    public Message(JSONObject json) {
        this.content = json.getString("content");
        String UserID = json.getString("sender");
        this.sender = Database.getInstance().getUser(UserID);
        this.timestamp = LocalDateTime.parse(json.getString("timestamp"));
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
