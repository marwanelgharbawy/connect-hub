package frontend.friendManager;

import backend.User;

public class UserComboBoxItem {
    private final User user;

    // Constructor
    public UserComboBoxItem(User user) {
        this.user = user;
    }

    // Getter
    public User getUser() {
        return user;
    }

    // Override toString to return the display text
    @Override
    public String toString() {
        return user.getUsername();
    }
}
