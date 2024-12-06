package backend;
import content.ContentManager;
import friendManager.FriendManagerC;
import friendManager.FriendManagerFactory;
import friendManager.FriendManagerI;

import content.Post;
import content.Story;

import friendManager.*;


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
    private ContentManager contentManager;

    public User() throws IOException {
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile("", "", "");
    }
    public User(String username, String email, String password, LocalDate dateOfBirth) throws IOException {
        this.userId = Utilities.generateId();
        this.email = email;
        this.username = username;
        this.password = Utilities.hashPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.online = true;
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile("", "", "");
    }

    public User(JSONObject credentials) throws IOException {
        userId = credentials.getString("id");
        username = credentials.getString("username");
        email = credentials.getString("email");
        password = credentials.getString("password");
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.profile = new Profile("", "", "");
    }

    public void setUserData(JSONObject userData) throws IOException {
        dateOfBirth = Utilities.y_M_dToDate(userData.getString("dateOfBirth"));
        online = userData.getBoolean("online");
        String profile_img_path = userData.getString("profile-photo");
        String cover_img_path = userData.getString("cover-photo");
        profile.setProfilePhoto(profile_img_path);
        profile.setCoverPhoto(cover_img_path);

        JSONArray postJsonArray = userData.getJSONArray("posts");
        JSONArray storiesJsonArray = userData.getJSONArray("stories");
        contentManager = new ContentManager(this, postJsonArray, storiesJsonArray);

        Database database = Database.getInstance();
        // friends
        setFriends(database,userData);
        // blocked
        setBlocked(database,userData);
        // requests
        setRequests(database,userData);
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

    public ContentManager getContentManager(){
        return contentManager;
    }

    public Profile getProfile(){
        return profile;
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
        loadFriends(data);
        /* blocked */
        loadBlocked(data);
        /* friend requests */
        loadRequests(data);
        /* posts */
        data.put("posts", contentManager.postsToJsonArray());
        /* stories */
        data.put("stories", contentManager.storiesToJsonArray());

        return data;
    }
    public void setFriends(Database database,JSONObject userData){
        JSONArray friends = userData.getJSONArray("friends");
        for (Object friend : friends) {
            String friend_id = (String) friend;
            User friend_ = database.getUser(friend_id);
            if (friend_ != null) {
                if (!FriendUtils.isDuplicate(friend_, friendManager.getFriends())) {
                    friendManager.addFriend(this,friend_);
                }
            }
        }

    }
    public void setBlocked(Database database,JSONObject userData){
        JSONArray blocked = userData.getJSONArray("blocked");
        for (Object blockedUser : blocked) {
            String blockedUserId = (String) blockedUser;
            User blockedUser_ = database.getUser(blockedUserId);
            if (blockedUser_ != null) {
                if (!FriendUtils.isDuplicate(blockedUser_, friendManager.getBlockManager().getBlockedUsers())) {
                    friendManager.getBlockManager().appendBlock(this, blockedUser_);
                }
            }
        }
    }
    public void setRequests(Database database,JSONObject userData){
        JSONArray requests = userData.getJSONArray("requests");
        for (Object request : requests) {
            String senderId = (String) request;
            User sender = database.getUser(senderId);
            {
                if (sender != null) {
                    FriendRequest friendRequest = friendManager.getRequestManager().getReceivedRequest(senderId);
                    if (friendRequest == null) {
                        friendManager.getRequestManager().addFriendRequest(new FriendRequest(sender, this));
                    }
                }
            }

        }

    }

    public void loadFriends(JSONObject data){
        JSONArray friends = new JSONArray();
        for (User user : friendManager.getFriends()) {
            friends.put(user.getUserId());
        }
        data.put("friends", friends);

    }
    public void loadBlocked(JSONObject data){
        JSONArray blocked = new JSONArray();
        for (User user : friendManager.getBlockManager().getBlockedUsers()) {
            blocked.put(user.getUserId());
        }
        data.put("blocked", blocked);

    }
    public void loadRequests(JSONObject data){
        JSONArray friendRequests = new JSONArray();
        for (FriendRequest friendRequest : friendManager.getRequestManager().getReceivedRequests()) {
            friendRequests.put(friendRequest.getReceiver().userId); // Each friend request is linked to its sender
        }
        data.put("requests", friendRequests);

    }

}
