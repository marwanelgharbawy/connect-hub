package backend;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Group.Group;
import org.json.*;
import utils.Utilities;



public class Database {
    private static Database instance;
    private final String database_folder = "database";
    private final String users_folder = database_folder + "/users";
    private final String users_json_file = database_folder + "/users.json";
    private final String groups_folder = database_folder + "/groups";
    private final String groups_json_file = database_folder + "/groups.json";

    // Group maps
    private Map<String, Group> id_to_group = new HashMap<>();

    // User maps
    private Map<String, User> id_to_user = new HashMap<>();
    private Map<String, User> email_to_user = new HashMap<>();
    private Map<String, User> username_to_user = new HashMap<>();
    private final ArrayList<User> users;
    private CurrentUser currentUser;

    private Database() throws IOException {
        checkExistenceOfDatabase();
        users = new ArrayList<>();
    }

    public static Database getInstance() throws IOException {
        if (instance == null) {
            instance = new Database();
            instance.parseUsersData();
        }
        return instance;
    }

    public User getUser(String user_id) {
        return id_to_user.get(user_id);
    }
    public User[] getUsers(){
        return users.toArray(new User[0]);
    }

    /**
     * check the existence of database folder, users folder and users.json
     */
    private void checkExistenceOfDatabase() throws IOException {
        if (!new File(database_folder).exists()) {
            new File(users_folder).mkdirs();

            FileWriter file = new FileWriter(users_json_file);
            file.write(new JSONArray().toString());
            file.close();
        }
    }

    // TODO: Heavy refactoring for the following 2 methods
    private void parseUsersData() throws IOException {
        // Clear maps
        id_to_user.clear();
        email_to_user.clear();
        username_to_user.clear();
        users.clear();

        // Read users.json
        String data = Files.readString(Path.of(users_json_file));
        JSONArray array = new JSONArray(data);
        System.out.println("Users data loaded successfully from file");

        // Load users' credentials
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            User user = new User(jsonObject);
            id_to_user.put(user.getUserId(), user);
            email_to_user.put(user.getEmail(), user);
            username_to_user.put(user.getEmail(), user);
            System.out.println("Successfully added user: " + user.getUsername());
            users.add(user);
        }

        // Load users' data from each user file
        for (String id : id_to_user.keySet()) {
            String user_file = users_folder + "/" + id + ".json";
            String user_data = Files.readString(Path.of(user_file));
            JSONObject userData = new JSONObject(user_data);
            System.out.println("Setting user data: " + getUser(id).getUsername());
            getUser(id).setUserData(userData);
            System.out.println("Successfully added user data: " + getUser(id).getUsername());
        }
    }

    private void parseGroupsData() throws IOException {
        // Clear maps
        id_to_group.clear();

        // Read groups.json
        String data = Files.readString(Path.of(groups_json_file));
        JSONArray array = new JSONArray(data);
        System.out.println("Groups data loaded successfully from file");

        // Load groups' credentials
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            String groupID = jsonObject.getString("group_id");
            Group group = new Group(groupID);
            id_to_group.put(group.getGroupId(), group);
            System.out.println("Successfully added group: " + group.getName());
        }

        // Load groups' data from each group file
        for (String id : id_to_group.keySet()) {
            String group_file = groups_folder + "/" + id + ".json";
            String group_data = Files.readString(Path.of(group_file));
            JSONObject groupData = new JSONObject(group_data);
            System.out.println("Setting group data: " + getGroup(id).getName());
            getGroup(id).setGroupData(groupData);
            System.out.println("Successfully added group data: " + getGroup(id).getName());
        }
    }

    private Group getGroup(String id) {
        return id_to_group.get(id);
    }

    public CurrentUser getCurrentUser(){
        return this.currentUser;
    }

    // Login user method, returns a string with the error message, or null if it's successful
    public String loginUser(String username, String password) throws IOException {
        parseUsersData();

        // Check if username exists
        if (id_to_user.values().stream().noneMatch(user -> user.getUsername().equals(username))) {
            return "Username does not exist";
        }

        // Get user object by username: Get all users, filter by username, get the first one which is unique
        User user = id_to_user.values().stream().filter(u -> u.getUsername().equals(username)).findFirst().get();

        // Check if password is correct
        // Hash the input password to compare it with the database's hashed password
        password = Utilities.hashPassword(password);

        if (!user.getPassword().equals(password)) {
            return "Incorrect password";
        }
        // put in database that the user is online
        user.setOnline(true);
        writeUserData(user);
        currentUser = new CurrentUser(user);

        return null;
    }

    // Sign up user method, returns a string with the error message, or null if it's successful
    public String signUpUser(String username, String email, String password, LocalDate dateOfBirth) throws IOException {

        parseUsersData();

        if (id_to_user.values().stream().anyMatch(user -> user.getUsername().equals(username))) {
            return "Username already exists";
        }

        // Check if email already exists
        if (email_to_user.containsKey(email)) {
            return "Email already exists";
        }

        // Create user object
        User newUser = new User(username, email, password, dateOfBirth);

        // Add user info to maps
        id_to_user.put(newUser.getUserId(), newUser);
        email_to_user.put(email, newUser);
        username_to_user.put(username, newUser);

        try {
            writeUserData(newUser); // Create a file for the user with its id in the users directory
            writeDataToFiles();     // Update the users.json file for all users
        } catch (IOException e) {
            e.printStackTrace();
            return "Error writing to file";
        }
        currentUser = new CurrentUser(newUser);

        return null;
    }

    // Write user data to a file, can overwrite existing files or add a new user
    private void writeUserData(User user) throws IOException {
        FileWriter file = new FileWriter(users_folder + "/" + user.getUserId() + ".json");
        file.write(user.getUserData().toString(2));
        file.close();
    }

    public void saveUser(User user) throws IOException {
        id_to_user.put(user.getUserId(), user);
        email_to_user.put(user.getEmail(), user);
        username_to_user.put(user.getUsername(), user);
        writeUserData(user);
        writeDataToFiles();
    }

    // Write all users credentials to users.json
    private void writeDataToFiles() throws IOException {
        // Get all users
        JSONArray users = new JSONArray();
        for (User user : id_to_user.values()) {
            users.put(user.getCredentials());
        }

        // Write to file
        try {
            FileWriter file = new FileWriter(users_json_file);
            file.write(users.toString(2));
            file.close();
        } catch (IOException e) {
            System.out.println("Database error.");
        }
    }

    public void updateCredentials() throws IOException {
            writeDataToFiles();
    }

}
