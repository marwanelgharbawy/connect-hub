package notificationManager;

import backend.User;
import friendManager.FriendRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsManager {
    private User user;
    private final ArrayList<FriendRequestNotif> friendRequestNotifs;
    
    /**
     * key: group_id value: groupNotifManager
     * */
    private final HashMap<String, GroupNotifManager> group_NotifManager;


    public NotificationsManager(User user) {
        this.user = user;
        this.friendRequestNotifs = new ArrayList<>();
        this.group_NotifManager = new HashMap<>();
    }


    /**
    * call it only when the user is the current user
    */
    public void populateFriendRequests(){
        for (FriendRequest friendRequest : user.getFriendManager().getRequestManager().getReceivedRequests()) {
            User sender_user = friendRequest.getSender();
            FriendRequestNotif notif = new FriendRequestNotif(sender_user);
            friendRequestNotifs.add(notif);
        }
    }
}
