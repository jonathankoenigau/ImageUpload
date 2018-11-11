import java.io.File; 

/**
 * Write a description of class Backend here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Backend
{
    // instance variables - replace the example below with your own
    private UserFactory userFactory;

    /**
     * Constructor for objects of class Backend
     */
    public Backend()
    {
        userFactory = new UserFactory();
    }

    public static void addUser (String username, String password) {
        UserFactory.addRecord(username, password);
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() > 50) { 
            return false;
        } else {    
            char c;
            int count = 1; 
            for (int i = 0; i < password.length() - 1; i++) {
                c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {        
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                    if (count < 6)  {   
                        return false;
                    }   
                }
            }
        }
        return true;
    }
    
    public static boolean isUsernameValid(String username) {
        if (username.length() > 50) { 
            return false;
        } else {    
            char c;
            int count = 1; 
            for (int i = 0; i < username.length() - 1; i++) {
                c = username.charAt(i);
                if (!Character.isLetterOrDigit(c)) {        
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                    if (count < 6)  {   
                        return false;
                    }   
                }
            }
        }
        return true;
    }
    
    public static String moveImage(String filepath, String username) {
        return ImageFactory.moveImage(filepath, username);
    }
    
    public static String[] getUserImages(String username) {
        File[] userFiles = ImageFactory.getFilePaths(username);
        
        String[] userImageURLs = new String[userFiles.length];
        for(int i = 0; i < userFiles.length; i++) {
            userImageURLs[i] = userFiles[i].toURI().toString();
        }
        
        return userImageURLs;
    }
}
