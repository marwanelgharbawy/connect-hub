package content;

public class ContentFields {
    String text; // Text field
    String imagePath;// Image path
    public ContentFields(String text, String imagePath){
        this.text = text;
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public String getImagePath() {
        return imagePath;
    }
}
