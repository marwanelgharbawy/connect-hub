package friendManager;

import backend.User;

import java.util.ArrayList;

public interface BlockManagerI {
    void blockUser(User userToBlock);
    void unblockUser(User userToUnblock);
    boolean isBlocked(User user);
    ArrayList<User> getBlockedUsers();
}
