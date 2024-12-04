package content;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

public class Story extends CreatedContent{
    Story(String authorId, String text, String imagePath) {
        super(authorId, text, imagePath);
    }
    // Check if the story is due in order to delete it
    boolean isDue(){
//        return (LocalDateTime.now().getHour() - super.timestamp.getHour() >= 24);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(super.timestamp, now);
        return duration.toDays() > 0;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject data =  super.toJSONObject();
        data.put("expired", isDue());
        return data;
    }
}
