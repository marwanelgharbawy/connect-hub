package friendManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface RequestManagerI {
    void removeFriendRequest(FriendRequest friendRequest);
    void sendFriendRequest(User sender, User receiver) throws IOException;
    void acceptFriendRequest(FriendRequest friendRequest) throws IOException;
    void cancelRequest(FriendRequest friendRequest) throws IOException;
    void addFriendRequest(FriendRequest friendRequest);
    FriendRequest getReceivedRequest(String senderId);
    ArrayList<FriendRequest> getReceivedRequests();
    ArrayList<FriendRequest> getSentRequests();
}
