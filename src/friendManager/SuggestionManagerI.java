package friendManager;

import backend.User;

import java.util.ArrayList;

public interface SuggestionManagerI {
    ArrayList<User> suggestFriends(User user);
}
