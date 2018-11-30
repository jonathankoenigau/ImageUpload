import java.io.File; 
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    /**
     * logInUser checks if the username and password combination exists
     * in the database.
     * 
     * @param   username The user who is being logged in.
     *          password The password for the user who is being logged in.
     *          
     * @return  boolean - If true, the username and password combination exists
     *                    and the user is logged in. If false, either the
     *                    database file doesn't exist or the username and
     *                    password combination doesn't exist.
     */
    public static boolean logInUser(String username, String password) {
        Scanner scan;
        try {
            scan = new Scanner (new File("Database.txt"));
        }
        catch(FileNotFoundException e) {
            System.err.println("File Doesn't Exist");
            return false;
        }
        
        while (scan.hasNextLine()) {
            String tmp = scan.nextLine();
            int spaceIndex = tmp.indexOf(' ');
            String user = tmp.substring(0, spaceIndex);
            String pass = tmp.substring(spaceIndex + 1, tmp.length());
            
            System.out.println("**" + user + ", " + pass + "**");

            if (username.equals(user) && password.equals(pass)) {
                System.out.println("Login Successful");
                scan.close();
                return true;
            } 
        }
        System.out.println("Login Failed");
        scan.close();
        return false;
    }
    
    /**
     * moveImage calls the ImageFactory.moveImage method. This is used
     * to upload an image for a user.
     * 
     * @param   filepath The filepath to the image that should be moved.
     *          username The user that is uploading the image. The file
     *                   is moved to the folder of this user.
     * 
     * @return File for the image's new location.
     */
    public static File moveImage(String filepath, String username, String[] tags) {
        return ImageFactory.moveImage(filepath, username, tags);
    }

    /**
     * getUserImages calls ImageFactory.getFilePaths to get all the images
     * in username's folder and return a String array of their locations.
     * 
     * @param   username The user that the images are associated with.
     * 
     * @return File[] of file paths for each image.
     */
    public static File[] getUserImages(String username) {
        File[] userFiles = ImageFactory.getFilePaths(username);

        String[] userImageURLs = new String[userFiles.length];
        for(int i = 0; i < userFiles.length; i++) {
            userImageURLs[i] = userFiles[i].toURI().toString();
        }

        return userFiles;
    }
    
    /**
     * getFollowerImages calls ImageFactory.getFollowerFilePaths to get all 
     * the images from the users that username is following and return a 
     * File array of their locations.
     * 
     * @param   username The user that is requesting their follower's images.
     * 
     * @return File[] of file paths for each image.
     */
    public static File[] getFollowerImages(String username) {
        File[] followFiles = ImageFactory.getFollowerFilePaths(username);

        String[] followImageURLs = new String[followFiles.length];
        for(int i = 0; i < followFiles.length; i++) {
            followImageURLs[i] = followFiles[i].toURI().toString();
        }

        return followFiles;
    }
    
    /**
     * getFollowerImages calls ImageFactory.getFollowerFilePaths to get all 
     * the images from the users that username is following and return a 
     * File array of their locations.
     * 
     * @param   username The user that is requesting their follower's images.
     * 
     * @return File[] of file paths for each image.
     */
    public static File[] searchForImages(String[] searchTags) {
        File[] searchFiles = ImageFactory.searchForImages(searchTags);

        String[] searchImageURLs = new String[searchFiles.length];
        for(int i = 0; i < searchFiles.length; i++) {
            searchImageURLs[i] = searchFiles[i].toURI().toString();
        }

        return searchFiles;
    }

    /**
     * deleteImage calls ImageFactory.deleteImage to delete the given image
     * and its corresponding tag file.
     * 
     * @param   imagePath The image file that will be deleted and the name
     *                    used to delete the same named .txt file.
     */
    public static void deleteImage(File imagePath) {
        ImageFactory.deleteImage(imagePath.getAbsolutePath());
    }
    
    /**
     * addFollower calls UserFactory.addFollower() to make currentUser
     * follow userToFollow.
     * 
     * @param   currentUser The user current logged in.
     *          userToFollow The user that will be followed
     *          
     * @return Returns true if follower successfully added, returns false if failed.      
     */
    public static boolean addFollower(String currentUser, String userToFollow) {
        return UserFactory.addFollower(currentUser, userToFollow);
    }
}