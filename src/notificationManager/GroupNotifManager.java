package notificationManager;

import backend.User;

import java.util.ArrayList;

public class GroupNotifManager {
    private final String group_id;
    private final ArrayList<GroupStatusNotif> groupStatusNotifs;
    private final ArrayList<NewGroupPostNotif> groupPostNotifs;
    private final ArrayList<NewGroupUserNotif> groupUserNotifs;

    public GroupNotifManager(String group_id) {
        this.group_id = group_id;
        this.groupStatusNotifs = new ArrayList<>();
        this.groupPostNotifs = new ArrayList<>();
        this.groupUserNotifs = new ArrayList<>();
    }

    public void addNewUserNotif(User new_user){

    }

    public void addStatusChangeNotif(){

    }

    public void addNewPostNotif(){

    }

    public ArrayList<Notification> getAllNotifications(){
        ArrayList<Notification> notifs = new ArrayList<>();
        notifs.addAll(groupStatusNotifs);
        notifs.addAll(groupPostNotifs);
        notifs.addAll(groupUserNotifs);
        return notifs;
    }
}
