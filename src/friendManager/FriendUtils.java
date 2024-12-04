package friendManager;

import user.User;

import java.util.ArrayList;

public class FriendUtils {


        // Method to check if users are already friends
        public static boolean isAlreadyFriends(User sender, User receiver) {
            // Check if both users are friends (present in each other's friend list)
            return sender.getFriendManager().getFriends().contains(receiver)
                    && receiver.getFriendManager().getFriends().contains(sender);
        }

        // Method to check if users have blocked each other
        public static boolean isBlocked(User sender, User receiver) {
            // Check if either user has blocked the other
            return sender.getFriendManager().getBlockManager().getBlockedUsers().contains(receiver)
                    || receiver.getFriendManager().getBlockManager().getBlockedUsers().contains(sender);
        }
        // Check for duplicates in the friends or blocked users lists
        public static boolean isDuplicate(User key, ArrayList<User> users){
            for(User user: users){
                if(user.equals(key)) {
                    return true;
                }
            }
            return false;
        }


}
