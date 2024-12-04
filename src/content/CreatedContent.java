package content;

import utils.Utilities;

import java.time.LocalDateTime;

public abstract class CreatedContent {
    String contentId;// Unique ID for each content
    String authorId; // Reference to the user who created the content
    ContentFields contentFields; //Text with optional images (store image paths in the database).
    LocalDateTime timestamp;// Creation time.
    CreatedContent(String authorId, String text, String imagePath){
        Utilities utilities = new Utilities();
        contentId = utilities.generateId(); // Generate unique ID for content
        this.authorId = authorId;
        contentFields = new ContentFields(text,imagePath);
        // TODO: In the GUI, the user picks the image using file chooser, send the image path as argument
    }
    public String getContentId() {
        return contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ContentFields getContentFields() {
        return contentFields;
    }

}
