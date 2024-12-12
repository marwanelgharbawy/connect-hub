package notificationManager;

import java.util.ArrayList;

public class GroupNotifManager {
    private final ArrayList<GroupStatusChangedNotif> groupStatusChangedNotifs;
    private final ArrayList<NewGroupPostNotif> groupPostNotifs;
    private final ArrayList<NewGroupUserNotif> groupUserNotifs;

    public GroupNotifManager() {
        this.groupStatusChangedNotifs = new ArrayList<>();
        this.groupPostNotifs = new ArrayList<>();
        this.groupUserNotifs = new ArrayList<>();
    }
}
