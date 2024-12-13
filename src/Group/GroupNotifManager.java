package Group;

import backend.User;
import notificationManager.GroupStatusNotif;
import notificationManager.NewGroupPostNotif;
import notificationManager.NewGroupUserNotif;
import notificationManager.Notification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupNotifManager {
    private final Group group;
    private final ArrayList<GroupStatusNotif> groupStatusNotifs;
    private final ArrayList<NewGroupPostNotif> groupPostNotifs;
    private final ArrayList<NewGroupUserNotif> groupUserNotifs;

    public GroupNotifManager(Group group) {
        this.group = group;
        this.groupStatusNotifs = new ArrayList<>();
        this.groupPostNotifs = new ArrayList<>();
        this.groupUserNotifs = new ArrayList<>();
    }

    public void setGroupNotifs(JSONObject notifsJson){
        JSONArray new_user = notifsJson.getJSONArray("new-user");
        // TODO: populate List

        JSONArray new_post = notifsJson.getJSONArray("new-post");
        // TODO: populate List

        JSONArray status_changed = notifsJson.getJSONArray("status-changed");
        // TODO: populate List
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

    public JSONObject toJSONObject(){
        JSONObject notifsJson = new JSONObject();

        JSONArray new_user = new JSONArray();
        for(NewGroupUserNotif notif: groupUserNotifs){
            new_user.put(notif.toJSONObject());
        }
        notifsJson.put("new-user", new_user);

        JSONArray new_post = new JSONArray();
        for(NewGroupPostNotif notif: groupPostNotifs){
            new_post.put(notif.toJSONObject());
        }
        notifsJson.put("new-post", new_post);

        JSONArray status_changed = new JSONArray();
        for(GroupStatusNotif notif: groupStatusNotifs){
            status_changed.put(notif.toJSONObject());
        }
        notifsJson.put("status-changed", status_changed);

        return notifsJson;
    }
}
