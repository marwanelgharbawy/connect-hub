package Group;

import content.ContentFields;
import content.Post;

import java.util.ArrayList;

public class GroupContent {
    ArrayList<Post> posts;
    private static GroupContent instance;

    private GroupContent(){
        this.posts = new ArrayList<>();
    }

    public static GroupContent getInstance(){
        if (instance == null)
            return new GroupContent();
        return instance;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post){
        this.posts.add(post);
    }

    public void removePost(Post post){
        this.posts.remove(post);
    }

    public void editPost(Post post, ContentFields field){
        post.setContentFields(field);
    }
}
