package friendManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface SuggestionManagerI {
    ArrayList<User> getFriendsOfFriends(User user);
    boolean suggestionsContain(User user);
    void removeSuggestion(User user);
    void refuseSuggestion(User mainUser,User user) throws IOException;
}
