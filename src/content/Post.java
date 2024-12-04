package content;

import org.json.JSONObject;

public class Post extends CreatedContent{
    Post(String authorId, String text, String imagePath) {
        super(authorId, text, imagePath);
    }

    Post(JSONObject data){
        super(data);
    }
}
