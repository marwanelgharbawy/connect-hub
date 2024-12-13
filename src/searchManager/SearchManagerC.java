package searchManager;

import backend.Database;
import backend.User;
import friendManager.FriendUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import Group.*;

public class SearchManagerC implements SearchManagerI{
    private final ArrayList<User> searchedUsers;
//    private ArrayList<Group> searchedGroups;
    public SearchManagerC(){
        this.searchedUsers = new ArrayList<>();
//        this.searchedGroups = new ArrayList<>();
    }
    @Override
    public void search(User user, String searchKey) throws IOException {
        // Initialize the search results holders
        searchedUsers.clear();
//        searchedGroups.clear();
        // Get database instance
        Database database = Database.getInstance();
        // Get all users to append the ones with similar usernames
        User[] users = database.getUsers();
        // Get all groups to append the ones with similar groups
//        Group[] groups = database.getGroups();
        // Add users to the list if they are not blocked
        for(User searchedUser: users){
            System.out.println(searchedUser.getUsername());
            if (!FriendUtils.isBlocked(user,searchedUser)){
                if(searchedUser.getUsername().toLowerCase().contains(searchKey.toLowerCase())){
                    searchedUsers.add(searchedUser);
                }
            }
        }
        // Add searched groups to the list
        /*
        for(Group searchedGroup : groups){
        if(searchedGroup.getName().toLowerCase().contains(searchKey.toLowerCase())){
                    searchedGroups.add(searchedGroup);
                }
        }
         */
    }

    @Override
    public User[] getUserSearchResults() {
        return searchedUsers.toArray(new User[0]);
    }
    /*
    @Override
    public Group[] getGroupSearchResults() {
        return searchedGroups.toArray(new Group[0]);
    }
     */

}
