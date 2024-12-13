package notificationManager;

import Group.Group;
import backend.Database;
import backend.User;
import friendManager.FriendRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsManager {
    private User user;
    private final ArrayList<FriendRequestNotif> friendRequestNotifs;
    
    /**
     * key: group_id value: groupNotifManager
     * */


    public NotificationsManager(User user) {
        this.user = user;
        this.friendRequestNotifs = new ArrayList<>();
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

    public ArrayList<Notification> getAllNotifications() throws IOException {
        ArrayList<Notification> notifs = new ArrayList<>();
        notifs.addAll(friendRequestNotifs);
        for(String group_id: user.getUserGroups()){
            LocalDateTime date = user.getJoiningTime(group_id);
            Group group = Database.getInstance().getGroup(group_id);
            notifs.addAll(group.getGroupNotifManager().getAllNotifications(user, date));
        }
        return notifs;
    }
}
