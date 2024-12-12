package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public class RequestManagerC implements RequestManagerI { // Friends list
    private final ArrayList<FriendRequest> receivedRequests; // Incoming friend requests
    private final ArrayList<FriendRequest> sentRequests;// Sent friend requests
    private final Database database;
    //TODO: Differentiate between sent and received requests in the UIX

    public RequestManagerC() throws IOException {
        this.sentRequests = new ArrayList<>();
        this.receivedRequests = new ArrayList<>();
        database = Database.getInstance();
    }


    @Override
    public void removeFriendRequest(FriendRequest friendRequest) {
        friendRequest.getReceiver().getFriendManager().getRequestManager().getReceivedRequests().remove(friendRequest);
        friendRequest.getSender().getFriendManager().getRequestManager().getSentRequests().remove(friendRequest);

    }

    // Send a friend request if users are not friends and if neither of them blocked the other
    @Override
    public void sendFriendRequest(User sender, User receiver) throws IOException {
        if (!sender.getFriendManager().getFriends().contains(receiver) && !receiver.equals(sender)
                && !FriendUtils.isBlocked(sender,receiver) && !FriendUtils.isAlreadyFriends(sender,receiver) && !FriendUtils.havePendingRequest(sender,receiver)) {
            FriendRequest request = new FriendRequest(sender, receiver);
            receiver.getFriendManager().getRequestManager().addFriendRequest(request);
            if(sender.getFriendManager().getSuggestionManager().suggestionsContain(receiver)){
                sender.getFriendManager().getSuggestionManager().removeSuggestion(receiver);
            }
            database.saveUser(receiver);
        }
    }


    // Accept friend request;
    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) throws IOException {
        // TODO: Handle the access to this method in the UIX
        friendRequest.setStatus("accepted");
        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();
        receiver.getFriendManager().addFriend(receiver,sender);
        sender.getFriendManager().addFriend(sender,receiver);
        removeFriendRequest(friendRequest);
        database.saveUser(sender);
        database.saveUser(receiver);
    }

    // Delete (or cancel) friend request;
    public void cancelRequest(FriendRequest friendRequest) throws IOException {
        friendRequest.setStatus("cancelled");
        removeFriendRequest(friendRequest);
        database.saveUser(friendRequest.getReceiver());
    }
    @Override
    public void addFriendRequest(FriendRequest friendRequest) {
        receivedRequests.add(friendRequest);
        friendRequest.getSender().getFriendManager().getRequestManager().getSentRequests().add(friendRequest);
    }

    @Override
    public FriendRequest getReceivedRequest(String senderId) {
        for (FriendRequest friendRequest: receivedRequests){
            if (friendRequest.getSender().getUserId().equals(senderId))
                return friendRequest;
        }
        return null;
    }
    @Override
    public FriendRequest getSentRequest(String receiverId) {
        for (FriendRequest friendRequest: sentRequests){
            if (friendRequest.getReceiver().getUserId().equals(receiverId))
                return friendRequest;
        }
        return null;
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
