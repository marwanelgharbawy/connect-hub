package content;

import org.json.JSONObject;

public class Post extends CreatedContent{
    public Post(String authorId,ContentFields contentFields) {
        super(authorId, contentFields);
    }

    public Post(String authorId,JSONObject data){
        super(authorId,data);
    }
}
