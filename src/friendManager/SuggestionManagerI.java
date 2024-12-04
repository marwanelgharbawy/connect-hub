package friendManager;

import user.User;

import java.util.ArrayList;

public interface SuggestionManagerI {
    ArrayList<User> suggestFriends(User user);
}
