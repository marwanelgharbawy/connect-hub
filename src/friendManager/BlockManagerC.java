package friendManager;

import backend.User;

import java.util.ArrayList;

public class BlockManagerC implements BlockManagerI{
    private final ArrayList<User> blockedUsers;// Blocked users
    private final FriendManagerI friendManager;
    public BlockManagerC(FriendManagerI friendManager) {
        this.friendManager = friendManager;
        blockedUsers = new ArrayList<>();
    }

    @Override
    public void blockUser(User mainUser,User userToBlock) {
        // Check for duplicates and remove user from friend list if they are friends
        if(!FriendUtils.isDuplicate(userToBlock,blockedUsers)){
            blockedUsers.add(userToBlock);
            friendManager.removeFriend(mainUser,userToBlock);
        }
    }

    @Override
    public void unblockUser(User userToUnblock) {
        blockedUsers.remove(userToUnblock);
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
