package backend;

import content.Post;
import content.Story;
import notificationManager.Notification;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class CurrentUser{
    private ArrayList<Post> newsFeedPosts;
    private ArrayList<Story> newsFeedStories;
    private User user;


    public CurrentUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Post> getNewsFeedPosts(){
        newsFeedPosts = new ArrayList<>();
        newsFeedPosts.addAll(user.getContentManager().getPosts());

        for(User friend: user.getFriendManager().getFriends()){
            newsFeedPosts.addAll(friend.getContentManager().getPosts());
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

    public ArrayList<Notification> getNotifications(){
        user.getNotifsManager().populateFriendRequests();
        return user.getNotifsManager().getAllNotifications();
    }
}
