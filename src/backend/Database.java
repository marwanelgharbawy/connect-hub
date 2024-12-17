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
    private final String chats_folder = database_folder + "/chats";
    private final String chats_json_file = database_folder + "/chats.json";

    // User maps
    private final Map<String, User> id_to_user = new HashMap<>();
    private final Map<String, User> email_to_user = new HashMap<>();
    private final Map<String, User> username_to_user = new HashMap<>();
    private CurrentUser currentUser;

    // Group maps
    private final Map<String, Group> id_to_group = new HashMap<>();

    // Chat maps
    private final ArrayList<Conversation> conversations = new ArrayList<>();

    private Database() throws IOException {
        checkExistenceOfDatabase();
    }

    public static Database getInstance() {
        if (instance == null) {
            try {
                instance = new Database();
                instance.parseUsersData();
                instance.parseGroupsData();
                instance.parseChatsData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public User getUser(String user_id) {
        return id_to_user.get(user_id);
    }

    public User[] getUsers(){
        return username_to_user.values().toArray(new User[0]);
    }

    public Group[] getGroups(){return id_to_group.values().toArray(new Group[0]);}

    /**
     * check the existence of database folder, users folder and users.json
     */
    private void checkExistenceOfDatabase() throws IOException {
        if (!new File(database_folder).exists()) {
            new File(users_folder).mkdirs();
            new File(groups_folder).mkdirs();

            FileWriter users_file = new FileWriter(users_json_file);
            FileWriter groups_file = new FileWriter(groups_json_file);
            users_file.write(new JSONArray().toString());
            users_file.close();
            groups_file.write(new JSONArray().toString());
            groups_file.close();
        }
    }

    // TODO: Heavy refactoring for the following 2 methods
    private void parseUsersData() throws IOException {
        // Clear maps
        id_to_user.clear();
        email_to_user.clear();
        username_to_user.clear();

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
            username_to_user.put(user.getUsername(), user);
            System.out.println("Successfully added user: " + user.getUsername());
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
        JSONArray idsArray = new JSONArray(data); // Array of JSON objects representing groups IDs
        System.out.println("Groups data loaded successfully from file");

        // Load groups' credentials
        for (Object obj : idsArray) {
            String groupID = (String) obj;
            Group group = new Group(groupID);
            id_to_group.put(group.getGroupId(), group);
            System.out.println("Successfully added group: " + group.getName());

            // Load each group's data from the file
            String group_file = groups_folder + "/" + groupID + ".json";
            String group_data = Files.readString(Path.of(group_file));
            // JSON object representing group data, handled in Group class
            JSONObject groupData = new JSONObject(group_data);
            System.out.println("Setting group data: " + getGroup(groupID).getName());
            getGroup(groupID).setGroupData(groupData);
            System.out.println("Successfully added group data: " + getGroup(groupID).getName());
        }
    }

    private void parseChatsData() throws IOException {
        conversations.clear();

        // Read chats.json, an array of IDs (ID1_ID2)
        String data = Files.readString(Path.of(chats_json_file));
        JSONArray array = new JSONArray(data);

        // Load each conversation, an array of messages (sender, timestamp, content)
        for (Object obj : array) {
            String chatID = (String) obj;
            String chat_file = chats_folder + "/" + chatID + ".json";

            String messages_ = Files.readString(Path.of(chat_file));

            // Load messages in the conversation
            JSONArray messages = new JSONArray(messages_); // JSON array of messages
            ArrayList<Message> messagesList = new ArrayList<>();
            for (Object message : messages) {
                JSONObject msg = (JSONObject) message;
                Message textMessage = new Message(msg); // Send JSON object to Message constructor
                messagesList.add(textMessage);
            }

            String[] ids = chatID.split("_");
            User user1 = getUser(ids[0]);
            User user2 = getUser(ids[1]);
            Conversation conversation = new Conversation(user1, user2, messagesList);
            conversations.add(conversation);
        }
    }

    public Group getGroup(String id) {
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

    // This will be called to save a group in the database class and files
    public void saveGroup(Group group) throws IOException {
        id_to_group.put(group.getGroupId(), group); // Add group to the map (Current database instance)
        writeGroupData(group);                      // Write the group data to its own file
        writeGroupsIDToFiles();                     // Update the groups.json file with the new group ID
    }

    // Writes the group data to its own file
    private void writeGroupData(Group group) throws IOException {
        FileWriter file = new FileWriter(groups_folder + "/" + group.getGroupId() + ".json");
        file.write(group.getGroupData().toString(2));
        file.close();
    }

    // Writes all groups IDs to groups.json
    // This should be called when adding a group to the database to append its ID to groups.json
    private void writeGroupsIDToFiles() throws IOException {
        JSONArray groups = new JSONArray();
        for (Group group : id_to_group.values()) {
            groups.put(group.getGroupId()); // JSON array of IDs
        }
        try {
            FileWriter file = new FileWriter(groups_json_file);
            file.write(groups.toString(2));
            file.close();
        } catch (IOException e) {
            System.out.println("Database error.");
        }
    }

    public void deleteGroup(String groupId) {
        id_to_group.remove(groupId);
        try {
            writeGroupsIDToFiles(); // Rewrite the groups.json file
            // The group file won't be deleted, but will be deleted from the groups.json file
            // TODO: Delete the group file (optional)
            System.out.println("Group with ID: " + groupId + " deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete group with its reference, in case we need it
    public void deleteGroup(Group group) {
        deleteGroup(group.getGroupId());
    }

    public int getNumberOfUsers() {
        return id_to_user.size();
    }

    public int getNumberOfGroups() {
        return id_to_group.size();
    }

    // This is the method that should be called from the frontend, it will return the conversation between two users
    // If no conversation exists, it will create a new one
    public Conversation getConversation(User user1, User user2) {
        for (Conversation conversation : conversations) {
            if (conversation.getUser1().equals(user1) && conversation.getUser2().equals(user2) ||
                    conversation.getUser1().equals(user2) && conversation.getUser2().equals(user1)) {
                return conversation;
            }
        }
        return createNewConversation(user1, user2);
    }

    private Conversation createNewConversation(User user1, User user2) {
        Conversation conversation = new Conversation(user1, user2);
        conversations.add(conversation);
        return conversation;
    }

    // Update the conversation file
    public void saveConversation(Conversation conversation) {
        String chatID1 = conversation.getUser1().getUserId() + "_" + conversation.getUser2().getUserId();
        String chat_file1 = chats_folder + "/" + chatID1 + ".json";
        String chatFile; // File to be written

        if (Files.exists(Path.of(chat_file1))) {
            chatFile = chat_file1;
        } else { // Switch both users IDs
            String chatID2 = conversation.getUser2().getUserId() + "_" + conversation.getUser1().getUserId();
            String chat_file2 = chats_folder + "/" + chatID2 + ".json";
            if (Files.exists(Path.of(chat_file2))) {
                chatFile = chat_file2;
            } else {
                chatFile = chat_file1; // Create a new file with the name user1_user2
            }
        }
        // Objective: Write in chatFile
        try {
            FileWriter file = new FileWriter(chatFile);
            file.write(conversation.toJSON().toString(2));
            file.close();
        } catch (IOException e) {
            System.out.println("Database error, failed to write to file.");
            e.printStackTrace();
        }
    }
}