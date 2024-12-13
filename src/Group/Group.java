package Group;

import java.io.IOException;
import java.util.ArrayList;


import Group.MembershipManager.MembershipRequest;
import Group.MembershipManager.MembershipRequestManager;
import backend.CurrentUser;
import backend.Database;
import backend.User;
import content.Post;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.*;

public class Group {

    private String name;
    private String description;
    private Picture groupPhoto;
    private final GroupContent groupContent;
    private ArrayList<User> members;
    private ArrayList<User> admins;
    private PrimaryAdmin primaryAdmin;
    private final String groupId;
    private final MembershipRequestManager membershipManager;
    private final GroupNotifManager groupNotifManager;


    // Constructor when creating a new group from the frontend (New Group)
    public Group(String name, String description, String groupPhotoPath, User primaryAdmin) throws IOException {
        this.name = name;
        this.description = description;
        this.groupPhoto = new Picture(groupPhotoPath);
        this.groupId = Utilities.generateId();
        this.primaryAdmin = new PrimaryAdmin(primaryAdmin, this);
        this.groupContent = new GroupContent(this);
        this.membershipManager = new MembershipRequestManager(this);
        this.groupNotifManager = new GroupNotifManager(this);
        members = new ArrayList<>();
        admins = new ArrayList<>();
    }

    // Constructor when loading a group from the database
    public Group(String groupId) throws IOException {
        this.groupContent = new GroupContent(this);
        this.groupId = groupId;
        this.membershipManager = new MembershipRequestManager(this);
        this.groupNotifManager = new GroupNotifManager(this);
        members = new ArrayList<>();
        admins = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Picture getGroupPhoto() {
        return groupPhoto;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public PrimaryAdmin getPrimaryAdmin() {
        return primaryAdmin;
    }

    public void setName(String name) {
        this.name = name;
        saveGroup();
    }

    public void setDescription(String description) {
        this.description = description;
        saveGroup();
    }

    public void setGroupPhoto(String groupPhotoPath) throws IOException {
        this.groupPhoto = groupPhoto.setImage(groupPhotoPath);
        saveGroup();
    }

    public boolean isMember(User user) {
        return members.contains(user);
    }

    public boolean isAdmin(User user) {
        return admins.contains(user);
    }

    public boolean isPrimaryAdmin(User user) {
        return user.getUserId().equals(primaryAdmin.getUser().getUserId());
    }

    public boolean isInGroup(User user){
        return  isMember(user) || isAdmin(user) || isPrimaryAdmin(user);
    }

    private void addGroupToCurrentUser(CurrentUser user) {
        user.addGroup(this, new Member(user.getUser(), this));
    }

    private void removeGroupFromCurrentUser(CurrentUser user) {
        user.removeGroup(this);
    }

    private void changeRoleFromCurrentUer(CurrentUser user, GroupRole role){
        user.changeRole(this, role);
    }

    public void removeMember(Member member) {
        this.members.remove(member.getUser());
        saveGroup();
    }

    public void removeMember(User user) {
        this.members.remove(user);
        saveGroup();
    }

    public void removeMember(CurrentUser currentUser){
        this.members.remove(currentUser.getUser());
        removeGroupFromCurrentUser(currentUser);
        saveGroup();
    }

    public void addMember(Member member) {
        this.members.add(member.getUser());
        saveGroup();
    }

    public void addMember(User user) {
        this.members.add(user);
        saveGroup();
    }

    public void addMember(CurrentUser currentUser){
        this.members.add(currentUser.getUser());
        addGroupToCurrentUser(currentUser);
        saveGroup();
    }

    public GroupContent getGroupContent() {
        return groupContent;
    }

    public GroupNotifManager getGroupNotifManager(){
        return groupNotifManager;
    }

    public void addPost(Post post) {
        this.groupContent.addPost(post);
        saveGroup();
    }

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public void addAdmin(Member member) {
        members.remove(member.getUser());
        admins.add(member.getUser());
    }

    public void addAdmin(User user) {
        members.remove(user);
        admins.add(user);
        saveGroup();
    }

    public void addAdmin(CurrentUser currentUser) throws IOException {
        members.remove(currentUser.getUser());
        currentUser.changeRole(this, new Admin(currentUser.getUser(), this));
        saveGroup();
    }

    public void removeAdmin(Admin admin) {
        members.add(admin.getUser());
        admins.remove(admin.getUser());
        saveGroup();
    }

    public void removeAdmin(CurrentUser user){
        members.add(user.getUser());
        admins.remove(user.getUser());
        user.changeRole(this, new Member(user.getUser(), this));
        saveGroup();
    }

    public void removeAdmin(User user) {
        members.add(user);
        admins.remove(user);
        saveGroup();
    }

    private void setRequests(JSONObject data) throws IOException {
        JSONArray requests =  data.getJSONArray("requests");
        membershipManager.clearRequests();
        for(Object request: requests){
            String user_id = (String) request;
            User user = Database.getInstance().getUser(user_id);
            if(user != null){
                membershipManager.getMembershipRequests().add(new MembershipRequest(user, this));
            }
        }
    }
    private void setAdmins(JSONObject data) throws IOException {
        JSONArray adminsJson = data.getJSONArray("admins");
        this.admins.clear();
        for(Object admin: adminsJson){
            String user_id = (String) admin;
            this.admins.add(Database.getInstance().getUser(user_id));
        }
    }
    private void setMembers(JSONObject data) throws IOException{
        JSONArray memberJson = data.getJSONArray("members");
        this.members.clear();
        for(Object member: memberJson){
            String user_id = (String) member;
            this.members.add(Database.getInstance().getUser(user_id));
        }
    }

    private void loadRequest(JSONObject data){
        JSONArray requestJson = new JSONArray();
        for(MembershipRequest request: this.membershipManager.getGroupRequests()){
            requestJson.put(request.getSender().getUserId());
        }
        data.put("requests", requestJson);
    }
    private void loadAdmins(JSONObject data){
        JSONArray adminsJson = new JSONArray();
        for(User admin: this.admins){
            adminsJson.put(admin.getUserId());
        }
        data.put("admins", adminsJson);
    }
    private void loadMembers(JSONObject data){
        JSONArray membersJson = new JSONArray();
        for(User member: this.members){
            membersJson.put(member.getUserId());
        }
        data.put("members", membersJson);
    }

    public void saveGroup(){
        try {
            Database.getInstance().saveGroup(this);
        } catch (IOException e){
            System.out.println("Database error");
        }
    }

    public void setGroupData(JSONObject data) throws IOException {
        this.name = (String) data.get("name");
        this.description = (String) data.get("description");
        this.groupPhoto = new Picture((String) data.get("group-photo")); // Takes the path of the image
        this.members.clear();
        this.primaryAdmin = new PrimaryAdmin(this, (String) data.get("primary-admin"));

        setAdmins(data);
        setMembers(data);
        setRequests(data);
        this.groupNotifManager.setGroupNotifs(data.getJSONObject("notifications"));
    }

    public JSONObject getGroupData() throws IOException {
        JSONObject data = new JSONObject();
        data.put("name", name);
        data.put("description", description);
        data.put("group-photo", groupPhoto.getImagePath());

        // THIS MIGHT NEED TO BE CHANGED AFTER MERGING
        data.put("primary-admin", primaryAdmin.getUser().getUserId());

        loadAdmins(data);
        loadMembers(data);
        loadRequest(data);

        data.put("notifications", groupNotifManager.toJSONObject());
        return data;
    }

    public MembershipRequestManager getMembershipManager() {
        return membershipManager;
    }
  
    public boolean includeUser(Member member) {
        return members.contains(member);
    }

    public String getGroupId() {
        return groupId;
    }
}
