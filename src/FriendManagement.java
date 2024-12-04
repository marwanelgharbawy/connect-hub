import java.util.ArrayList;

public class FriendManagement {
    private final User mainUser;
    private final ArrayList<User> friends; // Friends list
    private final ArrayList<User> suggestions; // Suggestions for friends list
    private final ArrayList<User> blocked; // Blocked users
    private final ArrayList<FriendRequest> receivedRequests; // Incoming friend requests
    private final ArrayList<FriendRequest> sentRequests; // Sent friend requests
    //TODO: Differentiate between sent and received requests in the UIX

    FriendManagement(User user) {
        mainUser = user;
        friends = new ArrayList<>();
        suggestions = new ArrayList<>();
        blocked = new ArrayList<>();
        receivedRequests = new ArrayList<>();
        sentRequests = new ArrayList<>();
    }

    public ArrayList<User> getBlocked() {
        return blocked;
    }

    public ArrayList<User> getSuggestions() {
        populateSuggestions();
        return suggestions;
    }

    public ArrayList<FriendRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public ArrayList<FriendRequest> getSentRequests() {
        return sentRequests;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    // Append user into the blocked list
    public void block(User user) {
        blocked.add(user);
        // Remove the blocked user from the friends list if the user was a friend
        friends.remove(user);
        // TODO: hide blocked users from newsfeed
    }

    // Remove friend from the friends list
    public void unblock(User user) {
        blocked.remove(user);
    }

    // Send a friend request if users are not friends and if neither of them blocked the other
    public void sendFriendRequest( User receiver) {
        if (!isAlreadyFriends(receiver) && !mainUser.equals(receiver)
                && !isBlocked(receiver) && !isRequestDuplicate(receiver)) {
            FriendRequest friendRequest = new FriendRequest(mainUser, receiver);
            receiver.getFriendManagement().receivedRequests.add(friendRequest);
            mainUser.getFriendManagement().sentRequests.add(friendRequest);
        }
    }

    // Accept friend request;
    public void acceptFriendRequest( FriendRequest friendRequest) {
        // TODO: Handle the access to this method in the UIX
        friendRequest.setStatus("accepted");
        User sender = friendRequest.getSender();
        mainUser.getFriendManagement().receivedRequests.remove(friendRequest);
        sender.getFriendManagement().sentRequests.remove(friendRequest);
        mainUser.getFriendManagement().friends.add(friendRequest.getSender());
        sender.getFriendManagement().friends.add(friendRequest.getReceiver());
    }

    // Delete (or cancel) friend request;
    public void cancelRequest( FriendRequest friendRequest) {
        friendRequest.setStatus("cancelled");
        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();
        sender.getFriendManagement().sentRequests.remove(friendRequest);
        receiver.getFriendManagement().receivedRequests.remove(friendRequest);
    }

    public void populateSuggestions() {
        for (User friend : friends) {
            for (User friendOfFriend : friend.getFriendManagement().getFriends()) {
                // Add suggestion if not blocked and check for duplicates
                if (!suggestions.contains(friendOfFriend) && !blocked.contains(friendOfFriend) && !friendOfFriend.equals(mainUser)) {
                    suggestions.add(friendOfFriend);
                }
            }
        }
    }
    // Check if request has already been sent either by the sender or the receiver to each other
    private boolean isRequestDuplicate(User otherUser) {
        for (FriendRequest friendRequest :mainUser.getFriendManagement().getSentRequests()) {
            if (friendRequest.getReceiver().equals(otherUser) && friendRequest.getSender().equals(mainUser) && friendRequest.getStatus().equals("pending")) {
                return true;
            }
        }
        for (FriendRequest friendRequest : mainUser.getFriendManagement().getReceivedRequests()) {
            if (friendRequest.getSender().equals(otherUser) && friendRequest.getReceiver().equals(mainUser) && friendRequest.getStatus().equals("pending")) {
                return true;
            }
        }
        return false;
    }
    private boolean isAlreadyFriends( User otherUser) {
        return mainUser.getFriendManagement().getFriends().contains(otherUser);
    }

    private boolean isBlocked( User otherUser) {
        return mainUser.getFriendManagement().getBlocked().contains(otherUser) || otherUser.getFriendManagement().getBlocked().contains(mainUser);
    }


}
