import java.util.Base64;
import java.util.Date;
import java.security.*;
import java.util.UUID;

public class User {
    String userId;
    String email;
    String username;
    String password;
    Date dateOfBirth;
    boolean online;
    private final FriendManagement friendManagement;

    public User(String username, String email, String password, Date dateOfBirth){
        Utilities utilities = new Utilities();
        this.userId = utilities.generateId();
        this.email = email;
        this.username = username;
        this.password = hashPassword(password);
        this.dateOfBirth = dateOfBirth;
        this.online = true;
        this.friendManagement = new FriendManagement(this);
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    private String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm not found");
            return null;
        }
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
        this.password = hashPassword(password);
    }

    public FriendManagement getFriendManagement() {
        return friendManagement;
    }
}
