package utils;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Utilities {

    public Utilities(){}

    public static String capitalize(String input){
        String[] words  = input.split(" ");
        StringBuilder output = new StringBuilder(words[0].substring(0, 1).toUpperCase() + words[0].substring(1));
        String word;
        for (int i = 1; i < words.length; i++) {
            word = words[i];
            output.append(" ").append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return output.toString();
    }
    public static String generateId(){
        return UUID.randomUUID().toString();
    }

    public static boolean validateName(String name){
        return name.matches("^[a-zA-Z][a-zA-Z '-]+$");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+[.][a-z]{2,6}$");
    }

    public static boolean validateUsername(String username){
        return username.matches("^[a-zA-Z][a-zA-Z0-9_]{3,14}$");
    }


    /**
    * return String of Date with format yyyy-MM-dd
    */
    public static String DateTo_y_M_d(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        return ft.format(date);
    }

    public static String DateTo_y_M_d(LocalDate date){
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(ft);
    }

    /**
     * return String of Date with format yyyy-MM-dd-HH-mm
     */
    public static String DataTo_y_M_d_hh_mm(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd-HH-mm");
        return ft.format(date);
    }

    /**
     * return String of LocalDateTime with format yyyy-MM-dd-HH-mm
     */
    public static String DataTo_y_M_d_hh_mm(LocalDateTime date){
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        return date.format(ft);
    }

    public static LocalDateTime y_M_d_hh_mmToDate(String date_str){
        if(!date_str.isEmpty()){
            String[] date = date_str.split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(date[3]);
            int min = Integer.parseInt(date[4]);
            return LocalDateTime.of(year, month, day, hour, min);
        }
        return null;
    }

    public static LocalDate y_M_dToDate(String date_str){
        if(!date_str.isEmpty()){
            String[] date = date_str.split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            return LocalDate.of(year, month, day);
        }
        return null;

    }

    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm not found");
            return null;
        }
    }

    public static Color HEX2Color(String hex){
        if(hex.length() > 6) throw new IllegalArgumentException("Wrong Color format : " + hex);

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Color(r, g, b);
    }
}
