package backend;

import Group.Group;
import Group.GroupRole;
import content.Post;
import content.Story;

import notificationManager.Notification;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CurrentUser{
    private ArrayList<Post> newsFeedPosts;
    private ArrayList<Story> newsFeedStories;
    private final User user;
    private final HashMap<String, GroupRole> groups;


    public CurrentUser(User user){
        this.user = user;
        this.groups = new HashMap<>();
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Post> getNewsFeedPosts() throws IOException {
        newsFeedPosts = new ArrayList<>();
        newsFeedPosts.addAll(user.getContentManager().getPosts());

        for(User friend: user.getFriendManager().getFriends()){
            newsFeedPosts.addAll(friend.getContentManager().getPosts());
        }

        for(String group_id: user.getUserGroups()){
            Group group = Database.getInstance().getGroup(group_id);
            newsFeedPosts.addAll(group.getGroupContent().getPosts());
        }

        //sort posts
        newsFeedPosts.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return newsFeedPosts;
    }

    public ArrayList<Story> getNewsFeedStories(){
        newsFeedStories = new ArrayList<>();

        for(Story story: user.getContentManager().getStories()){
            if(!story.isDue()){
                newsFeedStories.add(story);
            }
        }

        for (User friend: user.getFriendManager().getFriends()){
            for(Story story: friend.getContentManager().getStories()){
                if(!story.isDue()){
                    newsFeedStories.add(story);
                }
            }
        }

        //sort stories
        newsFeedStories.sort(Comparator.comparing(Story::getTimestamp).reversed());
        return newsFeedStories;
    }

    public void addGroup(Group group, GroupRole role){
        groups.put(group.getGroupId(), role);
    }

    public void removeGroup(Group group){
        groups.remove(group.getGroupId());
    }

    public void changeRole(Group group, GroupRole role){
        groups.put(group.getGroupId(), role);
    }


    public ArrayList<Notification> getNotifications() throws IOException {
        user.getNotifsManager().populateFriendRequests();
        ArrayList<Notification> notifs = user.getNotifsManager().getAllNotifications();
        notifs.sort(Comparator.comparing(Notification::getNotifDate).reversed());
        return notifs;
    }

}
