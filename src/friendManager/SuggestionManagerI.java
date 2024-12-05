package friendManager;

import backend.User;

import java.util.ArrayList;

public interface SuggestionManagerI {
    void suggestAllFriendsOfFriends(User user);
    void suggestFriend(User friend, User suggestedFriend);
    ArrayList<User> getFriendsOfFriends();
    ArrayList<User> getSuggestions();
    void removeSuggestion(User user);
}
