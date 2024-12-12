package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Picture {

    BufferedImage image;

    public Picture(String imagePath) throws IOException {
        if(imagePath.isEmpty()){
            BufferedImage grayImage = new BufferedImage(500, 300, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = grayImage.createGraphics();
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(0, 0, 500, 300);
            graphics.dispose();
            this.image = grayImage;
        }
        else
            this.image = constructImage(imagePath);
    }

    public Picture(BufferedImage image){
        this.image = image;
    }

    private BufferedImage constructImage(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    public BufferedImage getImage() {
        return image;
    }

    public Picture setImage(String imagePath) throws IOException {
        this.image = constructImage(imagePath);
        return this;
    }

    // Recommended dimensions based on Facebook
    // Profile picture(in Profile) = 360 x 360, Cover photo = 640 x 360, profile picture(in stories and posts) = ???
    public BufferedImage resizeImage(int width, int height){
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(this.image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public ImageIcon toIcon(){
        return new ImageIcon(this.getImage());
    }

    public void saveImage(String savingPath, String format) throws IOException {
        ImageIO.write(this.getImage(), format, new File(savingPath));
    }

    public BufferedImage cropImage(int x, int y,int width, int height){
        return image.getSubimage(x, y, width, height);
    }

    @Override
    public String toString() {
        return image.getData().toString();
    }

    private BufferedImage resizeWithRespectToRation(int targetWidth, int targetHeight){
        return (BufferedImage) image.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_DEFAULT);
    }

    public BufferedImage reduceToNewsfeed(int size){
        return resizeWithRespectToRation(size, size);
    }
}
