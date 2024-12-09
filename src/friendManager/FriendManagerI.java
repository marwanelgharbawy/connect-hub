package friendManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface FriendManagerI {
    RequestManagerI getRequestManager();
    BlockManagerI getBlockManager();
    SuggestionManagerI getSuggestionManager();
    ArrayList<User> getFriends();
    void addFriend(User mainUser,User user);
    void removeFriend(User mainUser, User removedUser);
    void confirmRemove(User mainUser, User removedUser)throws IOException;
}
