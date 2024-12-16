package backend;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class Conversation {
    private final User user1;
    private final User user2;
    private final ArrayList<Message> messages = new ArrayList<Message>();
    private final Database database;

    // Normal constructor
    public Conversation(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.database = Database.getInstance();
    }

    // Load from database
    public Conversation(JSONObject json) {
        this.database = Database.getInstance();
        this.user1 = database.getUser(json.getString("user1"));
        this.user2 = database.getUser(json.getString("user2"));
        JSONArray jsonMessages = json.getJSONArray("messages");
        for (int i = 0; i < jsonMessages.length(); i++) {
            this.messages.add(new Message(jsonMessages.getJSONObject(i)));
        }
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
}
