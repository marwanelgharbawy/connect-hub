package content;

import backend.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentManager {
    private ArrayList<Post> posts;
    private ArrayList<Story> stories;
    User user;

    public ContentManager(User user){
        this.user = user;
        this.posts = new ArrayList<>();
        this.stories = new ArrayList<>();
    }

    public void setPosts(JSONArray postsJson){
        for(Object post: postsJson){
            Post post_ = new Post(user.getUserId(), (JSONObject) post);
            posts.add(post_);
        }
    }

    public void setStories(JSONArray storiesJson){
        for(Object story: storiesJson){
            Story story_ = new Story(user.getUserId(), (JSONObject)story);
            stories.add(story_);
        }
    }

    public ArrayList<Post> getPosts(){
        return posts;
    }

    public ArrayList<Story> getStories(){
        return stories;
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public void addStory(Story story){
        stories.add(story);
    }

    public JSONArray postsToJsonArray(){
        JSONArray jsonArray = new JSONArray();
        for(Post post: posts){
            jsonArray.put(post.toJSONObject());
        }
        return jsonArray;
    }

    public JSONArray storiesToJsonArray(){
        JSONArray jsonArray = new JSONArray();
        for(Story story: stories){
            jsonArray.put(story.toJSONObject());
        }
        return jsonArray;
    }
}
