package Group;

import backend.User;
import content.Post;
import notificationManager.GroupStatusNotif;
import notificationManager.NewGroupPostNotif;
import notificationManager.NewGroupUserNotif;
import notificationManager.Notification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public void setGroupNotifs(JSONObject notifsJson) throws IOException {
        JSONArray new_user = notifsJson.getJSONArray("new-user");
        for(Object obj: new_user){
            NewGroupUserNotif notif = new NewGroupUserNotif(group, (JSONObject)obj);
            groupUserNotifs.add(notif);
        }

        JSONArray new_post = notifsJson.getJSONArray("new-post");
        for (Object obj: new_post){
            NewGroupPostNotif notif = new NewGroupPostNotif(group, (JSONObject)obj);
            groupPostNotifs.add(notif);
        }

        JSONArray status_changed = notifsJson.getJSONArray("status-changed");
        for(Object obj: status_changed){
            GroupStatusNotif notif = new GroupStatusNotif(group, (JSONObject) obj);
            groupStatusNotifs.add(notif);
        }
    }

    public void addNewUserNotif(User new_user){
        NewGroupUserNotif notif = new NewGroupUserNotif(group, new_user);
        groupUserNotifs.add(notif);
    }

    public void addStatusChangeNotif(User user){
        GroupStatusNotif notif = new GroupStatusNotif(group, user);
    }

    public void addNewPostNotif(Post post){
        NewGroupPostNotif notif = new NewGroupPostNotif(group, post);
        groupPostNotifs.add(notif);
    }

    private boolean isValidNotification(Notification notif, LocalDateTime date){
        return date.isBefore(notif.getNotifDate());
    }

    public ArrayList<Notification> getAllNotifications(User user, LocalDateTime date){
        ArrayList<Notification> notifs = new ArrayList<>();

        for(GroupStatusNotif notif : groupStatusNotifs){
            if(isValidNotification(notif, date) && notif.getUser().getUserId().equals(user.getUserId()))
                notifs.add(notif);
        }
//        notifs.addAll(groupStatusNotifs);

        for(Notification notif: groupPostNotifs){
            if(isValidNotification(notif, date))
                notifs.add(notif);
        }
//        notifs.addAll(groupPostNotifs);

        for(Notification notif: groupUserNotifs){
            if(isValidNotification(notif, date))
                notifs.add(notif);
        }
//        notifs.addAll(groupUserNotifs);
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
