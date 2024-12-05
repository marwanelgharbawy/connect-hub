package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public class SuggestionManagerC implements SuggestionManagerI {
    private final ArrayList<User> friendsOfFriends; //List of friends based on mutual friends
    private final ArrayList<User> suggestions; // List of received suggestions
    private final Database database;
    SuggestionManagerC() throws IOException {
        friendsOfFriends = new ArrayList<>();
        suggestions = new ArrayList<>();
        database = Database.getInstance();
    }
    @Override
    public ArrayList<User> getSuggestions() {
        return suggestions;
    }

    @Override
    public void removeSuggestion(User user) {
        suggestions.remove(user);
        friendsOfFriends.remove(user);
        System.out.println(user.getUsername());
    }

    @Override
    public ArrayList<User> getFriendsOfFriends(User user) {
        // Suggest friends of friends, handle the case if the friends are mutual or finding the main user
        for (User friend : user.getFriendManager().getFriends()) {
            for (User friendOfFriend : friend.getFriendManager().getFriends()) {
                if (!user.getFriendManager().getFriends().contains(friendOfFriend)
                        && !friendOfFriend.equals(user)&& !friendsOfFriends.contains(friendOfFriend)) {
                    if(!FriendUtils.havePendingRequest(user,friendOfFriend)){
                        friendsOfFriends.add(friendOfFriend);
                        System.out.println("No request");
                    }

                }
            }
        }
        return friendsOfFriends;
    }

    @Override
    public void suggestFriend(User friend, User suggestedFriend) throws IOException {
        if(!FriendUtils.isDuplicate(suggestedFriend,friend.getFriendManager().getFriends())
                &&!FriendUtils.isDuplicate(suggestedFriend,friend.getFriendManager().getSuggestionManager().getSuggestions())){
            friend.getFriendManager().getSuggestionManager().getSuggestions().add(suggestedFriend);
        }
        database.saveUser(friend);
    }

    @Override
    public void addSuggestion(User user){
        suggestions.add(user);
    }

    @Override
    public void refuseSuggestion(User mainUser, User user) throws IOException {
        removeSuggestion(user);
        database.saveUser(mainUser);
    }
}
