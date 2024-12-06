package content;

import org.json.JSONObject;
import utils.Utilities;

import java.time.LocalDateTime;

public abstract class CreatedContent {
    String contentId;// Unique ID for each content
    String authorId; // Reference to the user who created the content
    ContentFields contentFields; //Text with optional images (store image paths in the database).
    LocalDateTime timestamp;// Creation time.
    public CreatedContent(String authorId, ContentFields contentFields){
        contentId = Utilities.generateId(); // Generate unique ID for content
        this.authorId = authorId; // Assign userID
        this.contentFields = contentFields;
        this.timestamp = LocalDateTime.now();
        //this.timestamp =  LocalDateTime.now();
        // TODO: In the GUI, the user picks the image using file chooser, send the image path as argument
    }

    CreatedContent(String authorId, JSONObject data){
        this.authorId = authorId;
        contentId = data.getString("id");
        timestamp = Utilities.y_M_d_hh_mmToDate(data.getString("date"));

        String text = data.getString("text");
        String photo = data.getString("photo");
        contentFields = new ContentFields(text, photo);
    }
    
    public void setContentFields(ContentFields contentFields) {
        this.contentFields = contentFields;
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

    public JSONObject toJSONObject(){
        JSONObject data = new JSONObject();
        data.put("id", contentId);

        data.put("text", contentFields.getText());
        data.put("photo", contentFields.getImagePath());

        data.put("date", Utilities.DataTo_y_M_d_hh_mm(timestamp));
        return data;
    }


}
