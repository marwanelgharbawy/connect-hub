import java.util.UUID;

public class Utilities {

    public Utilities(){}

    public String capitalize(String input){
        String[] words  = input.split(" ");
        StringBuilder output = new StringBuilder(words[0].substring(0, 1).toUpperCase() + words[0].substring(1));
        String word;
        for (int i = 1; i < words.length; i++) {
            word = words[i];
            output.append(" ").append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return output.toString();
    }
    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public boolean validateName(String name){
        return name.matches("^[a-zA-Z][a-zA-Z '-]+$");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+[.][a-z]{2,6}$");
    }

    public static boolean validateUsername(String username){
        return username.matches("^[a-zA-Z][a-zA-Z0-9_]{4,14}$");
    }

}
