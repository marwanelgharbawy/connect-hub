package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public class SuggestionManagerC implements SuggestionManagerI {
    private final ArrayList<User> friendsOfFriends; //List of friends based on mutual friends
    private final ArrayList<User> hiddenSuggestions; // Removed suggestions
    private final Database database;
    SuggestionManagerC() throws IOException {
        friendsOfFriends = new ArrayList<>();
        hiddenSuggestions = new ArrayList<>();
        database = Database.getInstance();
    }

    @Override
    public void removeSuggestion(User user) {
        friendsOfFriends.remove(user);
        hiddenSuggestions.add(user);
        System.out.println(user.getUsername());
    }

    @Override
    public ArrayList<User> getFriendsOfFriends(User user) {
        // Suggest friends of friends, handle the case if the friends are mutual or finding the main user
        for (User friend : user.getFriendManager().getFriends()) {
            for (User friendOfFriend : friend.getFriendManager().getFriends()) {
                if (!user.getFriendManager().getFriends().contains(friendOfFriend)
                        && !friendOfFriend.equals(user)
                        && !friendsOfFriends.contains(friendOfFriend)
                        && !FriendUtils.havePendingRequest(user,friendOfFriend)
                        && !hiddenSuggestions.contains(friendOfFriend)) {
                    friendsOfFriends.add(friendOfFriend);
                    System.out.println("FOF:"+friendOfFriend.getUsername());
                }
            }
        }
        return friendsOfFriends;
    }


    @Override
    public void refuseSuggestion(User mainUser, User user) throws IOException {
        removeSuggestion(user);
        hiddenSuggestions.add(user);
        database.saveUser(mainUser);
    }
}
