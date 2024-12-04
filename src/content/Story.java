package content;
import java.time.LocalDateTime;

public class Story extends CreatedContent{
    Story(String authorId, String text, String imagePath) {
        super(authorId, text, imagePath);
    }
    // Check if the story is due in order to delete it
    boolean isDue(){
        return (LocalDateTime.now().getHour() - super.timestamp.getHour() >= 24);
    }
}
