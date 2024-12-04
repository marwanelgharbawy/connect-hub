package friendManager;

import backend.User;

import java.util.ArrayList;

public class RequestManagerC implements RequestManagerI { // Friends list
    private final ArrayList<FriendRequest> receivedRequests; // Incoming friend requests
    private final ArrayList<FriendRequest> sentRequests;// Sent friend requests
    //TODO: Differentiate between sent and received requests in the UIX

    public RequestManagerC() {
        this.sentRequests = new ArrayList<>();
        this.receivedRequests = new ArrayList<>();
    }

    // Send a friend request if users are not friends and if neither of them blocked the other
    @Override
    public void sendFriendRequest(User sender, User receiver) {
        if (!sender.getFriendManager().getFriends().contains(receiver) && !receiver.equals(sender)
                && !FriendUtils.isBlocked(sender,receiver) && !FriendUtils.isAlreadyFriends(sender,receiver) && !FriendUtils.isDuplicate(sender,receiver.getFriendManager().getFriends())) {
            FriendRequest request = new FriendRequest(sender, receiver);
            sentRequests.add(request);
            receiver.getFriendManager().getRequestManager().addReceivedRequest(request);
        }
    }


    // Accept friend request;
    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        // TODO: Handle the access to this method in the UIX
        friendRequest.setStatus("accepted");
        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();
        receiver.getFriendManager().addFriend(sender);
        sender.getFriendManager().addFriend(receiver);
    }

    // Delete (or cancel) friend request;
    public void cancelRequest(FriendRequest friendRequest) {
        friendRequest.setStatus("cancelled");
        sentRequests.remove(friendRequest);
        friendRequest.getReceiver().getFriendManager().getRequestManager().getReceivedRequests().remove(friendRequest);

    }
    @Override
    public void addReceivedRequest(FriendRequest friendRequest) {
        receivedRequests.add(friendRequest);
    }

    @Override
    public ArrayList<FriendRequest> getReceivedRequests() {
        return receivedRequests;
    }
    @Override
    public ArrayList<FriendRequest> getSentRequests() {
        return sentRequests;
    }
}
