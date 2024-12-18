package backend;

import java.io.IOException;
import java.time.LocalDateTime;
import org.json.*;
import utils.Utilities;

public class Message {
    private String content;          // Text of the message
    private User sender;             // User who sent the message
    private LocalDateTime timestamp; // Time the message was sent
    private Database database;

    // Create time from frontend
    public Message(User sender, String message) {
        this.database = Database.getInstance();
        this.content = message;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }

    // Load message from database
    public Message(JSONObject json) {
        this.database = Database.getInstance();
        this.content = json.getString("content");
        String UserID = json.getString("sender");
        this.sender = database.getUser(UserID);
        this.timestamp = Utilities.y_M_d_hh_mmToDate(json.getString("timestamp"));
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public String getTimestamp() {
        return Utilities.DataTo_y_M_d_hh_mm(timestamp);
    }

    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        data.put("content", content);
        data.put("sender", sender.getUserId());
        data.put("timestamp", Utilities.DataTo_y_M_d_hh_mm(timestamp));
        return data;
    }
}
