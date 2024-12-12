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
        friendRequestNotifs.clear();
        for (FriendRequest friendRequest : user.getFriendManager().getRequestManager().getReceivedRequests()) {
            FriendRequestNotif notif = new FriendRequestNotif(friendRequest);
            friendRequestNotifs.add(notif);
        }
    }

    public ArrayList<Notification> getAllNotifications(){
        ArrayList<Notification> notifs = new ArrayList<>();
        notifs.addAll(friendRequestNotifs);
        for(GroupNotifManager group: group_NotifManager.values())
            notifs.addAll(group.getAllNotifications());
        return notifs;
    }
}
