package backend;

import org.json.*;

import java.io.IOException;
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
}
