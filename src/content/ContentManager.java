package content;

import backend.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentManager {
    private ArrayList<Post> posts;
    private ArrayList<Story> stories;
    User user;

    public ContentManager(User user, JSONArray postsJson, JSONArray storiesJson) {
        this.user = user;
        this.posts = new ArrayList<>();
        this.stories = new ArrayList<>();
        for(Object post: postsJson){
            Post post_ = new Post(user.getUserId(), (JSONObject) post);
            posts.add(post_);
        }

        for(Object story: storiesJson){
            Story story_ = new Story(user.getUserId(), (JSONObject)story);
            stories.add(story_);
        }
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
