package friendManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface SuggestionManagerI {
    void suggestFriend(User friend, User suggestedFriend) throws IOException;
    public ArrayList<User> getFriendsOfFriends(User user);
    ArrayList<User> getSuggestions();
    void removeSuggestion(User user);
    void addSuggestion(User user);
    void refuseSuggestion(User mainUser,User user) throws IOException;
}
