package backend;

import org.json.*;

import java.util.ArrayList;

public class Conversation {
    private final User user1;
    private final User user2;
    private final ArrayList<Message> messages;
    private final Database database;

    public Conversation(User user1, User user2, ArrayList<Message> messages) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
        this.database = Database.getInstance();
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        data.put("user1", user1.getUserId());
        data.put("user2", user2.getUserId());
        JSONArray messagesArray = new JSONArray();
        for (Message message : messages) {
            messagesArray.put(message.toJSON());
        }
        data.put("messages", messagesArray);
        return data;
    }
}
