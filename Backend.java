import java.io.File; 

/**
 * The Backend is what Website directly calls to interact and get
 * information about files.
 * 
 * It uses methods from UserFactory and ImageFactory while also slightly
 * modifying the output for the desired result.
 *
 * @author Joseph Collins
 *         Nurul Haque
 *         Jonathan Koenig
 */
public class Backend
{
    /**
     * addUser calls UserFactory.addRecord to add the given user to the database.
     * 
     * @param   username The user to be added to the database.
     *          password The password for the user.
     *          
     * @return boolean. If true, the user was successfully added.
     */
    public static boolean addUser (String username, String password) {
        return UserFactory.addRecord(username, password);
    }

    /**
     * isPasswordValid checks if the given password is correctly formatted
     * 
     * This includes being less than 50 characters long, not having
     * any spaces, and not having more than 5 numbers.
     * 
     * @param   password The password that needs to be checked.
     *                   This is already assumed to not be empty.
     *                      
     * @return  boolean - If true, the password is valid.
     */
    public static boolean isPasswordValid(String password) {
        if (password.length() > 50) { 
            return false;
        } else {    
            char c;
            int count = 1; 
            for (int i = 0; i < password.length(); i++) {
                c = password.charAt(i);
                // If the character is a space
                if (c == ' '){        
                    return false;
                }
                else if (Character.isDigit(c)) {
                    count++;
                    if (count > 6)  {   
                        return false;
                    }   
                }
            }
        }
        return true;
    }
    
    /**
     * isUsernameValid checks if the given username is correctly formatted
     * 
     * This includes being less than 50 characters long, not having
     * any spaces, not having more than 5 numbers, and only having
     * letters and numbers.
     * 
     * @param   username The username that needs to be checked.
     *                   This is already assumed to not be empty.
     *          
     * @return  boolean - If true, the username is valid.
     */
    public static boolean isUsernameValid(String username) {
        if (username.length() > 50) { 
            return false;
        } else {    
            char c;
            int count = 1; 
            for (int i = 0; i < username.length(); i++) {
                c = username.charAt(i);
                if (!Character.isLetterOrDigit(c)) {        
                    return false;
                }
                // If the character is a space
                else if (c == ' '){        
                    return false;
                }
                else if (Character.isDigit(c)) {
                    count++;
                    if (count > 6)  {   
                        return false;
                    }   
                }
            }
        }
        return true;
    }
    
    

public void checkFile() {
    Scanner scan = new Scanner (new File("UserFactory"));
    Scanner keyboard = new Scanner (System.in);
    String inpUser = keyboard.nextLine();
    String inpPass = keyboard.nextLine();
    
    while (scanner.hasNextLine()) {
    String user = scan.nextLine();
    String pass = scan.nextLine();

	if (inpUser.equals(user) && inpPass.equals(pass)) {
        	System.out.print("Login Successful");
    	} 
	else if (scanner.hasNextLine != true {
        	System.out.print("Error Message");
    }
   }
}

    
    
    /**
     * moveImage calls the ImageFactory.moveImage method. This is used
     * to upload an image for a user.
     * 
     * @param   filepath The filepath to the image that should be moved.
     *          username The user that is uploading the image. The file
     *                   is moved to the folder of this user.
     * 
     * @return String of a formatted file path for the image's new location.
     */
    public static String moveImage(String filepath, String username) {
        return ImageFactory.moveImage(filepath, username);
    }
    
    /**
     * getUserImages calls ImageFactory.getFilePaths to get all the images
     * in username's folder and return a String array of their locations.
     * 
     * @param   username The user that the images are associated with.
     * 
     * @return String[] of formatted file paths for each image.
     */
    public static String[] getUserImages(String username) {
        File[] userFiles = ImageFactory.getFilePaths(username);
        
        String[] userImageURLs = new String[userFiles.length];
        for(int i = 0; i < userFiles.length; i++) {
            userImageURLs[i] = userFiles[i].toURI().toString();
        }
        
        return userImageURLs;
    }
}
