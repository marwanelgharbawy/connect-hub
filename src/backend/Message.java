package backend;

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
}
