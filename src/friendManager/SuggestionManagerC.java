package friendManager;

import backend.User;

import java.util.ArrayList;

public class SuggestionManagerC implements SuggestionManagerI {
    private final ArrayList<User> friendsOfFriends; //List of friends based on mutual friends
    private final ArrayList<User> suggestions; // List of received suggestions
    SuggestionManagerC(){
        friendsOfFriends = new ArrayList<>();
        suggestions = new ArrayList<>();
    }
    @Override
    public ArrayList<User> getFriendsOfFriends() {
        return friendsOfFriends;
    }
    @Override
    public ArrayList<User> getSuggestions() {
        return suggestions;
    }

    @Override
    public void suggestAllFriendsOfFriends(User user) {
        // Suggest friends of friends, handle the case if the friends are mutual or finding the main user
        for (User friend : user.getFriendManager().getFriends()) {
            for (User friendOfFriend : friend.getFriendManager().getFriends()) {
                if (!user.getFriendManager().getFriends().contains(friendOfFriend)
                        && !friendOfFriend.equals(user)) {
                    friendsOfFriends.add(friendOfFriend);
                }
            }
        }
    }

    @Override
    public void suggestFriend(User friend, User suggestedFriend) {
        if(!FriendUtils.isDuplicate(suggestedFriend,friend.getFriendManager().getFriends())
                &&!FriendUtils.isDuplicate(suggestedFriend,friend.getFriendManager().getSuggestionManager().getSuggestions())){
            friend.getFriendManager().getSuggestionManager().getSuggestions().add(suggestedFriend);
        }
    }

    @Override
    public void removeSuggestion(User user) {
        suggestions.remove(user);
    }

    @Override
    public void addSuggestion(User user) {
        suggestions.add(user);
    }
}
