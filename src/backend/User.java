package backend;

import Group.*;
import content.ContentManager;

import friendManager.*;

import notificationManager.NotificationsManager;
import searchManager.SearchManagerC;
import utils.Utilities;

import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.time.LocalDateTime;

import org.json.*;

public class User {
    private final FriendManagerC friendManager;
    private final SearchManagerC searchManager;
//    private final MembershipRequestManager membershipManager;
    private String userId;
    private String email;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    boolean online;
    private final Profile profile;
    private final ContentManager contentManager;
    private final NotificationsManager notifsManager;
    private final HashMap<String, LocalDateTime> groupID_to_joiningDate;

    // Constructors

    // Empty constructor for creating a new user
    public User() throws IOException {
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.searchManager = new SearchManagerC();
//        this.membershipManager = new MembershipRequestManager();
        this.profile = new Profile(this, "", "icons/profile-icon.jpeg", "");
        this.contentManager = new ContentManager(this);
        this.notifsManager = new NotificationsManager(this);
        this.groupID_to_joiningDate = new HashMap<>();
    }

    // Constructor for creating a new user
    // This comes from the registration form (Frontend Constructor)
    public User(String username, String email, String password, LocalDate dateOfBirth) throws IOException {
        this.userId = Utilities.generateId();
        this.email = email;
        this.username = username;
        this.password = Utilities.hashPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.online = true;
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.searchManager = new SearchManagerC();
//        this.membershipManager = new MembershipRequestManager();
        this.profile = new Profile(this,"", "icons/profile-icon.jpeg", "");
        this.contentManager = new ContentManager(this);
        this.notifsManager = new NotificationsManager(this);
        this.groupID_to_joiningDate = new HashMap<>();
    }

    // Constructor for creating a user from the database's JSON object containing CREDENTIALS
    // This comes from the users.json file (Backend Constructor)
    public User(JSONObject credentials) throws IOException {
        userId = credentials.getString("id");
        username = credentials.getString("username");
        email = credentials.getString("email");
        password = credentials.getString("password");
        this.friendManager = FriendManagerFactory.createFriendManager();
        this.searchManager = new SearchManagerC();
//        this.membershipManager = new MembershipRequestManager();
        this.profile = new Profile(this, "", "icons/profile-icon.jpeg", "");
        this.contentManager = new ContentManager(this);
        this.notifsManager = new NotificationsManager(this);
        this.groupID_to_joiningDate = new HashMap<>();
    }

    @Override
    public String toString(){
        return getUsername();
    }

    // Set user's data from the database's JSON object
    public void setUserData(JSONObject userData) throws IOException {
        dateOfBirth = Utilities.y_M_dToDate(userData.getString("dateOfBirth"));
        online = userData.getBoolean("online");
        String profile_img_path = userData.getString("profile-photo");
        String cover_img_path = userData.getString("cover-photo");
        String bio = userData.getString("bio");
        profile.loadProfile(profile_img_path, cover_img_path, bio);

        // Removed setters since they update the database, we don't want that while loading

        JSONArray postJsonArray = userData.getJSONArray("posts");
        JSONArray storiesJsonArray = userData.getJSONArray("stories");
        contentManager.setPosts(postJsonArray);
        contentManager.setStories(storiesJsonArray);

        Database database = Database.getInstance();
        // friends
        setFriends(database,userData);
        // blocked
        setBlocked(database,userData);
        // requests
        setRequests(database,userData);
        /* groups */
        setGroups(userData);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return this.userId.equals(user.userId);
    }

    public void joinGroup(String group_id){
        groupID_to_joiningDate.put(group_id, LocalDateTime.now());
        saveUser();
    }

    public void leaveGroup(String group_id){
        groupID_to_joiningDate.remove(group_id);
        saveUser();
    }

    public boolean isMemberInGroup(String group_id){
        return  groupID_to_joiningDate.containsKey(group_id);
    }

    public boolean isOnline() {
        return online;
    }

    public String[] getUserGroups(){
        return groupID_to_joiningDate.keySet().toArray(new String[0]);
    }

    public LocalDateTime getJoiningTime(String group_id){
        return groupID_to_joiningDate.get(group_id);
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
        saveUser();
    }

    public void setOnline(boolean online) {
        this.online = online;
        saveUser();
    }

    public void setEmail(String email) {
        if (Utilities.validateUsername(email)) {
            this.email = email;
            saveUser();
        } else {
            System.out.println("Invalid email, operation canceled.");
        }
    }

    public void setUsername(String username) {
        if (Utilities.validateUsername(username)) {
            this.username = username;
            saveUser();
        } else {
            System.out.println("Invalid username, operation canceled.");
        }
    }

    public void setPassword(String password) {
        this.password = Utilities.hashPassword(password);
        saveUser();
    }

    public FriendManagerI getFriendManager() {
        return friendManager;
    }

    public ContentManager getContentManager(){
        return contentManager;
    }

    public SearchManagerC getSearchManager() {
        return searchManager;
    }

    public Profile getProfile(){
        return profile;
    }

    public NotificationsManager getNotifsManager() {
        return notifsManager;
    }

    public JSONObject getCredentials(){
        JSONObject credentials = new JSONObject();
        credentials.put("id", userId);
        credentials.put("username", username);
        credentials.put("email", email);
        credentials.put("password", password);
        return credentials;
    }

    // This method gets user's data in program to be saved in the database
    public JSONObject getUserData(){
        JSONObject data = new JSONObject();
        data.put("dateOfBirth", Utilities.DateTo_y_M_d(dateOfBirth));
        data.put("online", online);
        data.put("profile-photo", profile.getProfile_img_path());
        data.put("cover-photo", profile.getCover_img_path());
        data.put("bio", profile.getBio());

        // Fill JSON object with user's data from the current running program
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
        /* groups */
        loadGroups(data);
        return data;
    }

    // This method sets the user's friends after loading it from the database
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

    // This method set the user's blocked users after loading it from the database
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

    // This method set the user's friend requests after loading it from the database
    public void setRequests(Database database,JSONObject userData){
        JSONArray requests = userData.getJSONArray("requests");
        for (Object request : requests) {
            JSONObject json = (JSONObject)request;
            String senderId = json.getString("sender-id");
            LocalDateTime date = Utilities.y_M_d_hh_mmToDate(json.getString("date"));
            User sender = database.getUser(senderId);        
            if (sender != null) {
                FriendRequest friendRequest = friendManager.getRequestManager().getReceivedRequest(senderId);
                if (friendRequest == null) {
                    friendManager.getRequestManager().addFriendRequest(new FriendRequest(sender, this, date));
                }
            }         
        }
    }

    // This method set the user's groups and joining date after loading it from the database
    public void setGroups(JSONObject userData){
        JSONArray groups = userData.getJSONArray("groups"); // Array containing JSON Objects
        for(Object jsonGroup : groups){
            JSONObject groupJson = (JSONObject) jsonGroup;
            String group_id = groupJson.getString("group-id");
            LocalDateTime joining_date = Utilities.y_M_d_hh_mmToDate(groupJson.getString("joining-date"));
            groupID_to_joiningDate.put(group_id, joining_date);
        }
    }

    public void loadGroups(JSONObject data){
        JSONArray groups = new JSONArray();
        for(String group_id: groupID_to_joiningDate.keySet()){
            JSONObject groupJson = new JSONObject();
            groupJson.put("group-id", group_id);
            LocalDateTime joining_date = groupID_to_joiningDate.get(group_id);
            groupJson.put("joining-date" , Utilities.DataTo_y_M_d_hh_mm(joining_date));
            groups.put(groupJson);
        }
        data.put("groups", groups);
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
            friendRequests.put(friendRequest.toJSONObject()); // Each friend request is linked to its sender
        }
        data.put("requests", friendRequests);

    }

    // If dateOfBirth, online, profile-photo, cover-photo, friends, blocked,
    // requests, posts, stories are updated, save to file immediately
    public void saveUser() {
        try {
            Database.getInstance().saveUser(this);
        } catch (IOException e) {
            System.out.println("Database error");
        }
    }

    // If email, username, or password is changed, update the credentials in the database
    private void updateCredentials() {
        try {
            Database.getInstance().updateCredentials();
        } catch (IOException e) {
            System.out.println("Database error.");
        }
    }

    public boolean isRequestSent(User desiredUser){
        for(FriendRequest request: friendManager.getRequestManager().getSentRequests())
            if (request.getReceiver() == desiredUser)
                return true;
        return false;
    }

    public boolean isRequestReceived(User desiredUser){
        return desiredUser.isRequestSent(this);
    }
  
    public void createGroup(Group group) throws IOException {
        groupID_to_joiningDate.put(group.getGroupId(), LocalDateTime.now());
        Database.getInstance().saveGroup(group); // Save the group in the database
        saveUser();                              // To update its group array in the database
    }
}
