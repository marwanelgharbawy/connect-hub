package frontend.friendManager;
import friendManager.FriendRequest;

public class RequestComboBoxItem {
    private final FriendRequest friendRequest;

    // Constructor
    public RequestComboBoxItem(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    // Getter
    public FriendRequest getFriendRequest() {
        return friendRequest;
    }

    // Override toString to return the display text
    @Override
    public String toString() {
        return friendRequest.getSender().getUsername();
    }
}
