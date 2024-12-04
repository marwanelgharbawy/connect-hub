package friendManager;

import backend.User;

import java.util.ArrayList;

public interface FriendManagerI {
    RequestManagerI getRequestManager();
    BlockManagerI getBlockManager();
    SuggestionManagerI getSuggestionManager();
    ArrayList<User> getFriends();
    void addFriend(User user);
    void removeFriend(User user);
}
