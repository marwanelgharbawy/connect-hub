import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public Database() throws IOException {
        checkExistenceOfDatabase();
        parseUsersData();
    }

    public static Database getInstance() throws IOException {
        if(instance == null)
            instance = new Database();
        return instance;
    }

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
        for(Object obj: array){
            JSONObject jsonObject = (JSONObject) obj;
            String id = jsonObject.getString("id");
            String username = jsonObject.getString("username");
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");

            User new_user = new User(); // then put parameters
            // func1 parse data.json
            // func2 parse posts.json and stories.json
            id_to_user.put(id, new_user);
            email_to_user.put(email, new_user);
        }
    }

    private String getUserFolder(String user_id){
        return users_folder+"/"+user_id;
    }

    /**
    * checks the credentials and return user object or null
    */
    public void signInUser(String email, String password){

    }

    public void signUpUser(String args[]){}

    public void getUserData(String user_id){}

    public void getUserFriends(String user_id){}

    public void getUserContent(String user_id){}

    public void getUserPosts(String user_id){}
    public void getUserPost(String user_id, String post_id){}

    public void getUserStories(String user_id){}
    public void getUserStory(String user_id, String story_id){}

    public static void main(String[] args) throws IOException {
        Database database = Database.getInstance();
    }
}
