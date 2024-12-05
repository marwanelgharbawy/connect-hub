package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public class BlockManagerC implements BlockManagerI{
    private final ArrayList<User> blockedUsers;// Blocked users
    private final FriendManagerI friendManager;
    private final Database database;
    public BlockManagerC(FriendManagerI friendManager) throws IOException {
        this.friendManager = friendManager;
        blockedUsers = new ArrayList<>();
        database = Database.getInstance();
    }

    @Override
    public void appendBlock(User mainUser, User userToBlock) {
        // Check for duplicates and remove user from friend list if they are friends
        if(!FriendUtils.isDuplicate(userToBlock,blockedUsers)){
            blockedUsers.add(userToBlock);
            friendManager.removeFriend(mainUser,userToBlock);
        }
    }

    @Override
    public void removeBlock(User mainUser, User userToUnblock) {
        blockedUsers.remove(userToUnblock);
    }

    @Override
    public void blockUser(User mainUser, User userToBlock) throws IOException {
        appendBlock(mainUser,userToBlock);
        database.saveUser(mainUser);
        database.saveUser(userToBlock);

    }

    @Override
    public void unblockUser(User mainUser, User userToUnBlock) throws IOException {
        removeBlock(mainUser,userToUnBlock);
        database.saveUser(mainUser);
    }

    @Override
    public boolean isBlocked(User user) {
        return blockedUsers.contains(user);
    }
    @Override
    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }
}
