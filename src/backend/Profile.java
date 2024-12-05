package backend;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utils.Picture;

public class Profile {

    Picture profilePhoto;
    Picture coverPhoto;
    private String bio;
    private final User user;

    public Profile(User user) throws IOException {
        this.user = user;
        BufferedImage grayImage = new BufferedImage(500, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = grayImage.createGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 500, 300);
        graphics.dispose();
        this.profilePhoto = new Picture(grayImage);
        this.coverPhoto = new Picture(grayImage);
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePhoto(String profilePhotoPath) throws IOException {
        this.profilePhoto = new Picture(profilePhotoPath);
    }

    public void setCoverPhoto(String coverPhotoPath) throws IOException {
        this.coverPhoto = new Picture(coverPhotoPath);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public Picture getCoverPhoto() {

        return this.coverPhoto;
    }

    public Picture getProfilePhoto() {
        return this.profilePhoto;
    }

    public String getBio() {
        return this.bio;
    }

    public void changePassword(String newPassword) {
        this.user.setPassword(newPassword);
    }

    public User getUser() {
        return user;
    }
}
