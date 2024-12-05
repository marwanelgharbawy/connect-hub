package backend;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class Database {
    private static Database instance;
    private final String database_folder = "database";
    private final String users_folder = database_folder+"/users";
    private final String users_json_file =  database_folder+"/users.json";

    private User currentUser;
    private Map<String, User> id_to_user = new HashMap<>();
    private Map<String, User> email_to_user = new HashMap<>();

    private Database() throws IOException {
        checkExistenceOfDatabase();
    }

    public static Database getInstance() throws IOException {
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public User getUser(String user_id){return id_to_user.get(user_id);}

    /**
     * check the existence of database folder, users folder and users.json
     * */
    private void checkExistenceOfDatabase() throws IOException {
        if(!new File(database_folder).exists()){
            new File(users_folder).mkdirs();

            FileWriter file = new FileWriter(users_json_file);
            file.write(new JSONArray().toString());
            file.close();
        }
    }

    private void parseUsersData() throws IOException {
        String data = Files.readString(Path.of(users_json_file));
        JSONArray array = new JSONArray(data);
        System.out.println("Users data loaded successfully from file");

        for(Object obj: array){
            JSONObject jsonObject = (JSONObject) obj;
            User user = new User(jsonObject);
            id_to_user.put(user.getUserId(), user);
            email_to_user.put(user.getEmail(), user);
            System.out.println("Successfully added user: "+user.getUsername());
        }

        for(String id: id_to_user.keySet()){
            String user_file = users_folder+"/"+id+".json";
            String user_data = Files.readString(Path.of(user_file));
            JSONObject userData = new JSONObject(user_data);
            System.out.println("Setting user data: "+getUser(id).getUsername());
            getUser(id).setUserData(userData);
            System.out.println("Successfully added user data: "+getUser(id).getUsername());
        }
    }

    /**
    * checks the credentials and return user object or null
    */
    public void signInUser(String email, String password){
    }

    // Sign up user method, returns a string with the error message, or null if it's successful
    public String signUpUser(String username, String email, String password, LocalDate dateOfBirth) {

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

        // TODO: Write user data to its file

        // Create a file for the user with its id
        try {
            FileWriter file = new FileWriter(users_folder + "/" + newUser.getUserId() + ".json");
            file.write(newUser.getUserData().toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error writing to file";
        }

        // TODO: Add user to users.json file

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
            e.printStackTrace();
            return "Error writing to file";
        }


        return null;
    }

    public void getUserData(String user_id){}

    public void getUserFriends(String user_id){}

    public void getUserContent(String user_id){}

    public void getUserPosts(String user_id){}
    public void getUserPost(String user_id, String post_id){}

    public void getUserStories(String user_id){}
    public void getUserStory(String user_id, String story_id){}

    public static void main(String[] args) throws IOException {
        System.out.println(LocalDate.now());
        Database database = Database.getInstance();
        System.out.println("Database loaded successfully");
        database.parseUsersData();
        System.out.println("Users loaded successfully");

        // Test sign up user
        String errorMessage = database.signUpUser("u104", "u104@gmail.com", "password", LocalDate.now());
        if (errorMessage == null) {
            System.out.println("User created successfully");
        } else {
            System.out.println(errorMessage);
        }
    }
}
