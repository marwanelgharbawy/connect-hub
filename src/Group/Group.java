package Group;

import java.io.IOException;
import java.util.ArrayList;


import Group.MembershipManager.MembershipRequestManager;
import Group.MembershipManager.MembershipRequestStatus;
import backend.CurrentUser;
import backend.User;
import content.Post;

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
        this.membershipManager = new MembershipRequestManager();
        this.groupNotifManager = new GroupNotifManager(this);
    }

    // Constructor when loading a group from the database
    public Group(String groupId) throws IOException {
        this.groupContent = new GroupContent(this);
        this.groupId = groupId;
        this.membershipManager = new MembershipRequestManager();
        this.groupNotifManager = new GroupNotifManager(this);
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
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroupPhoto(String groupPhotoPath) throws IOException {
        this.groupPhoto = groupPhoto.setImage(groupPhotoPath);
    }

    public boolean isMember(User user) {
        return members.contains(user);
    }

    public boolean isAdmin(User user) {
        return admins.contains(user);
    }

    public boolean isPrimaryAdmin(User user) {
        return user == primaryAdmin.getUser();
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
    }

    public void removeMember(User user) {
        this.members.remove(user);
    }

    public void removeMember(CurrentUser currentUser){
        this.members.remove(currentUser.getUser());
        removeGroupFromCurrentUser(currentUser);
    }

    public void addMember(Member member) {
        this.members.add(member.getUser());
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void addMember(CurrentUser currentUser){
        this.members.add(currentUser.getUser());
        addGroupToCurrentUser(currentUser);
    }

    public GroupContent getGroupContent() {
        return groupContent;
    }

    public GroupNotifManager getGroupNotifManager(){
        return groupNotifManager;
    }

    public void addPost(Post post) {
        this.groupContent.addPost(post);
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
    }

    public void addAdmin(CurrentUser currentUser) throws IOException {
        members.remove(currentUser.getUser());
        currentUser.changeRole(this, new Admin(currentUser.getUser(), this));
    }

    public void removeAdmin(Admin admin) {
        members.add(admin.getUser());
        admins.remove(admin.getUser());
    }

    public void removeAdmin(CurrentUser user){
        members.add(user.getUser());
        admins.remove(user.getUser());
        user.changeRole(this, new Member(user.getUser(), this));
    }

    public void removeAdmin(User user) {
        members.add(user);
        admins.remove(user);
    }

    public void setGroupData(JSONObject data) throws IOException {
        this.name = (String) data.get("name");
        this.description = (String) data.get("description");
        this.groupPhoto = new Picture((String) data.get("group-photo")); // Takes the path of the image
        // TODO: Parse JSON for the following attributes
        this.primaryAdmin = new PrimaryAdmin(this, (String) data.get("primary-admin"));
//        this.admins = new ArrayList<>();
//        this.members = new ArrayList<>();
        this.groupNotifManager.setGroupNotifs(data.getJSONObject("notifications"));
    }

    public JSONObject getGroupData() throws IOException {
        JSONObject data = new JSONObject();
        data.put("name", name);
        data.put("description", description);
        data.put("group-photo", groupPhoto.getImagePath());

        // THIS MIGHT NEED TO BE CHANGED AFTER MERGING
        data.put("primary-admin", primaryAdmin.getUser().getUserId());

        // data.put("admins", /*admins*/ );
        // data.put("members", /*members*/ );
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
