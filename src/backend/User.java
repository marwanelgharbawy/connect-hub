package backend;
import content.Post;
import content.Story;
import friendManager.FriendManagerC;
import friendManager.FriendManagerFactory;
import friendManager.FriendManagerI;

import utils.Utilities;

import java.io.IOException;
import java.time.LocalDate;
import org.json.*;

public class User {
    private final FriendManagerC friendManager;
    String userId;
    String email;
    String username;
    String password;
    LocalDate dateOfBirth;
    boolean online;
    private final Profile profile;

    public User() throws IOException {
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile(this, "", "", "");
    }
    public User(String username, String email, String password, LocalDate dateOfBirth) throws IOException {
        this.userId = Utilities.generateId();
        this.email = email;
        this.username = username;
        this.password = Utilities.hashPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.online = true;
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile(this, "", "", "");
    }

    public User(JSONObject credentials) throws IOException {
        userId = credentials.getString("id");
        username = credentials.getString("username");
        email = credentials.getString("email");
        password = credentials.getString("password");
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile(this, "", "", "");
    }

    public void setUserData(JSONObject userData) throws IOException {
        dateOfBirth = Utilities.y_M_dToDate(userData.getString("dateOfBirth"));
        online = userData.getBoolean("online");
        String profile_img_path = userData.getString("profile-photo");
        String cover_img_path = userData.getString("cover-photo");
        profile.setProfilePhoto(profile_img_path);
        profile.setCoverPhoto(cover_img_path);

        // TODO: profile management: posts, stories

        Database database = Database.getInstance();
        JSONArray friends = userData.getJSONArray("friends");
        for(Object friend: friends){
            String friend_id = (String) friend;
            User friend_ = database.getUser(friend_id);
            friendManager.addFriend(friend_);
        }
    }

    public boolean isOnline() {
        return online;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password){
        this.password = Utilities.hashPassword(password);
    }

    public FriendManagerI getFriendManager() {
        return friendManager;
    }

    public JSONObject getCredentials(){
        JSONObject credentials = new JSONObject();
        credentials.put("id", userId);
        credentials.put("username", username);
        credentials.put("email", email);
        credentials.put("password", password);
        return credentials;
    }

    public JSONObject getUserData(){
        JSONObject data = new JSONObject();
        data.put("dateOfBirth", Utilities.DateTo_y_M_d(dateOfBirth));
        data.put("online", online);
        data.put("profile-photo", profile.getProfile_img_path());
        data.put("cover-photo", profile.getCover_img_path());


        /* friends */
        JSONArray friends = new JSONArray();
        for(User user: friendManager.getFriends()){
            friends.put(user.getUserId());
        }
        data.put("friends", friends);

        /* posts */
        JSONArray posts = new JSONArray();
        // TODO: posts
//        for(Post post: ){
//            posts.put(post.toJSONObject());
//        }
        data.put("posts", posts);

        /* stories */
        JSONArray stories = new JSONArray();
        // TODO: stories
//        for (Story story: ){
//            stories.put(story.toJSONObject());
//        }
        data.put("stories", stories);


        return data;
    }

}
