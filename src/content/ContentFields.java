package content;

public class ContentFields {
    private String text; // Text field
    private String imagePath;// Image path
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

    public void setText(String text) {
        this.text = text;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
