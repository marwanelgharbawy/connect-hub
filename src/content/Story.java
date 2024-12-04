package content;
import java.time.LocalDateTime;

public class Story extends CreatedContent{
    public Story(String authorId, ContentFields contentFields) {
        super(authorId, contentFields);
    }
    // Check if the story is due in order to delete it
    boolean isDue(){
        return (LocalDateTime.now().getHour() - super.timestamp.getHour() >= 24);
    }
}
