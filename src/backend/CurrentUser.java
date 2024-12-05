package backend;

import content.Post;
import content.Story;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class CurrentUser extends User{
    ArrayList<Post> newsFeedPosts;
    ArrayList<Story> newsFeedStories;
    public CurrentUser() throws IOException {
        super();
    }

    public CurrentUser(String username, String email, String password, LocalDate dateOfBirth) throws IOException {
        super(username, email, password, dateOfBirth);
    }

    public CurrentUser(JSONObject credentials) throws IOException {
        super(credentials);
    }

    public ArrayList<Post> getNewsFeedPosts(){
        newsFeedPosts = new ArrayList<>();
        newsFeedPosts.addAll(this.getContentManager().getPosts());

        for(User friend: this.getFriendManager().getFriends()){
            newsFeedPosts.addAll(friend.getContentManager().getPosts());
        }

        //sort posts
        newsFeedPosts.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return newsFeedPosts;
    }

    public ArrayList<Story> getNewsFeedStories(){
        newsFeedStories = new ArrayList<>();

        for(Story story: this.getContentManager().getStories()){
            if(!story.isDue()){
                newsFeedStories.add(story);
            }
        }

        for (User friend: this.getFriendManager().getFriends()){
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

}
