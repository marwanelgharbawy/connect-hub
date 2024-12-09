package friendManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface BlockManagerI {
    void appendBlock(User mainUser, User userToBlock);
    void removeBlock(User mainUser, User userToUnblock) ;
    void blockUser(User mainUser, User userToBlock) throws IOException;
    void unblockUser(User mainUser, User userToBlock) throws IOException;
    ArrayList<User> getBlockedUsers();
}
