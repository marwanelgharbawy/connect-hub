package backend;

import org.json.*;

import java.util.ArrayList;

public class Conversation {
    private final User user1;
    private final User user2;
    private final ArrayList<Message> messages;
    private final Database database;

    // Loading conversation from database
    public Conversation(User user1, User user2, ArrayList<Message> messages) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
        this.database = Database.getInstance();
    }

    // New conversation if it doesn't exist
    public Conversation(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<Message>();
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
        save();
    }

    private void save() {
        database.saveConversation(this);
    }

    // Converts conversation into a JSON array of messages
    public JSONArray toJSON() {;
        JSONArray messagesArray = new JSONArray();
        for (Message message : messages) {
            messagesArray.put(message.toJSON());
        }
        return messagesArray;
    }

    public void printConversation() {
        System.out.println("Conversation between " + user1.getUsername() + " and " + user2.getUsername());
        for (Message message : messages) {
            System.out.println(message.getSender().getUsername() + ": " + message.getContent() + " at: " + message.getTimestamp());
        }
    }
}
