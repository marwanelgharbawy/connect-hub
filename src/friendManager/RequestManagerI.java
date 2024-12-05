package friendManager;

import backend.User;

import java.util.ArrayList;

public interface RequestManagerI {
    public void sendFriendRequest(User sender, User receiver);
    void acceptFriendRequest(FriendRequest friendRequest);
    void cancelRequest(FriendRequest friendRequest);
    void addReceivedRequest(FriendRequest friendRequest);
    void addSentRequest(FriendRequest friendRequest);
    FriendRequest getReceivedRequest(String senderId);
    FriendRequest getSentRequest(String receiverID);
    ArrayList<FriendRequest> getReceivedRequests();
    ArrayList<FriendRequest> getSentRequests();
}
