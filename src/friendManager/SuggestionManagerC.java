package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
        friendsOfFriends.clear();
        // Handle the case if the user has no friends yet
        if(user.getFriendManager().getFriends().isEmpty()){
            HashSet<User> userSet = new HashSet<>(Arrays.asList(database.getUsers()));
            userSet.remove(user);
            userSet.removeIf(suggestion -> FriendUtils.havePendingRequest(user, suggestion)
                    || hiddenSuggestions.contains(suggestion)
                    || FriendUtils.isBlocked(user, suggestion));
            return new ArrayList<>(userSet);
        }
        // Suggest friends of friends, handle the case if the friends are mutual or finding the main user
        for (User friend : user.getFriendManager().getFriends()) {
            System.out.println("Friend:"+friend.getUsername());
            for (User friendOfFriend : friend.getFriendManager().getFriends()) {
                System.out.println("FOF:"+friendOfFriend.getUsername());
                if (!FriendUtils.isAlreadyFriends(user,friendOfFriend)
                        && !friendOfFriend.equals(user)
                        && !friendsOfFriends.contains(friendOfFriend)
                        && !FriendUtils.havePendingRequest(user,friendOfFriend)
                        && !hiddenSuggestions.contains(friendOfFriend)
                        && !FriendUtils.isBlocked(user,friendOfFriend)) {
                    friendsOfFriends.add(friendOfFriend);
                    System.out.println("FOF added:"+friendOfFriend.getUsername());
                }
            }
        }
        return friendsOfFriends;
    }

    @Override
    public boolean suggestionsContain(User user) {
        for(User suggestion : getFriendsOfFriends(user)){
            if(user.getUserId().equals(suggestion.getUserId())){
                return true;
            }
        }
        return false;
    }


    @Override
    public void refuseSuggestion(User mainUser, User user) throws IOException {
        removeSuggestion(user);
        hiddenSuggestions.add(user);
        database.saveUser(mainUser);
    }
}
