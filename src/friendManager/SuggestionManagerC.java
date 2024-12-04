package friendManager;

import backend.User;

import java.util.ArrayList;

public class SuggestionManagerC implements SuggestionManagerI {
    @Override
    public ArrayList<User> suggestFriends(User user) {
        ArrayList<User> suggestions = new ArrayList<>();
        // Suggest friends of friends, handle the case if the friends are mutual or finding the main user
        for (User friend : user.getFriendManager().getFriends()) {
            for (User friendOfFriend : friend.getFriendManager().getFriends()) {
                if (!user.getFriendManager().getFriends().contains(friendOfFriend)
                        && !friendOfFriend.equals(user)) {
                    suggestions.add(friendOfFriend);
                }
            }
        }
        return suggestions;
    }
}
