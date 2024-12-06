package backend;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utils.Picture;

public class Profile {

    Picture profilePhoto;
    Picture coverPhoto;
    private String bio;
    private String profile_img_path;
    private String cover_img_path;

    public Profile(String bio, String profile_img_path, String cover_img_path) throws IOException {
        this.bio = bio;
        this.profile_img_path = profile_img_path;
        this.cover_img_path = cover_img_path;
        this.profilePhoto = new Picture(profile_img_path);
        this.coverPhoto = new Picture(cover_img_path);
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePhoto(String profilePhotoPath) throws IOException {
        this.profile_img_path = profilePhotoPath;
        this.profilePhoto = new Picture(profilePhotoPath);
    }

    public void setCoverPhoto(String coverPhotoPath) throws IOException {
        this.cover_img_path = coverPhotoPath;
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

    public String getProfile_img_path() {
        return profile_img_path;
    }

    public String getCover_img_path() {
        return cover_img_path;
    }

}
