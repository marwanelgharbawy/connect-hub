package backend;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
        parseUsersData();
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
        for(Object obj: array){
            JSONObject jsonObject = (JSONObject) obj;
            User user = new User(jsonObject);
            id_to_user.put(user.getUserId(), user);
            email_to_user.put(user.getEmail(), user);
        }

        for(String id: id_to_user.keySet()){
            String user_file = users_folder+"/"+id+".json";
            String user_data = Files.readString(Path.of(user_file));
            JSONObject userData = new JSONObject(user_data);
            getUser(id).setUserData(userData);
        }
    }

    /**
    * checks the credentials and return user object or null
    */
    public void signInUser(String email, String password){
    }

    public void signUpUser(String args[]){
    }

    public static void main(String[] args) throws IOException {
        Database database = Database.getInstance();
        database.parseUsersData();
    }
}
