package backend;

import java.util.ArrayList;

public class Conversation {
    private User user1;
    private User user2;
    private ArrayList<Message> messages;

    public Conversation(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<Message>();
    }
}
