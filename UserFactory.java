import java.io.*;
import java.lang.*;
import java.util.*;
import java.nio.*;

/**
 * UserFactory contains all the methods associated with users.
 * 
 * @author  Nurul Haque
 */
public class UserFactory{
    /**
     * addRecord attempts to add the given username and password to the database.
     * 
     * It will create a database file if it doesn't already exist.
     * 
     * @param   username The user to be added to the database.
     *          password The password for the user.
     *          
     * @return boolean. If true, the user was successfully added, a
     * directory for the user was created, and a follow.txt was added
     * to that directory.
     */
    public static boolean addRecord (String username, String password) {
        String input = username + " " + password;

        //creates folder for user 
        File f = new File(username);
        if (!f.exists()){
            f.mkdir();
            System.out.println ("Directory created!");

            try{
                File file =new File("Database.txt");
                /* This logic is to create the file if the
                 * file is not already present
                 */
                if(!file.exists()){
                    file.createNewFile();
                    System.out.println("New File database.txt created");
                }

                //Here true is to append the content to file
                FileWriter fw = new FileWriter(file,true);
                //BufferedWriter writer give better performance
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(input);
                //Makes new line for the next user
                bw.newLine();
                //Closing BufferedWriter Stream
                bw.close();
                fw.close();

                System.out.println("Data successfully appended at the end of file");

                File follow = new File(username + "\\follow.txt");

                if(!follow.exists()){
                    follow.createNewFile();
                    System.out.println("New File follow.txt created");
                }

                return true;

            }catch(IOException ioe){
                System.out.println("Exception occurred:");
                ioe.printStackTrace();
                return false;
            }
        }
        else {
            System.out.println("Username and Directory already exists. Please enter different username.");
            return false;
        }
    }

    /**
     * addFollower makes currentUser follow userToFollow.
     * 
     * This writes userToFollow into currentUser's follow.txt.
     * 
     * @param   currentUser The user current logged in.
     *          userToFollow The user that will be followed
     *          
     * @return Returns true if follower successfully added, returns false if failed.      
     */
    public static boolean addFollower(String currentUser, String userToFollow){

        try{
            File file = new File(currentUser + "\\follow.txt");

            if(!file.exists()){
                file.createNewFile();
                System.out.println("New File follow.txt created");
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(userToFollow);
            //Makes new line for the next user
            bw.newLine();
            //Closing BufferedWriter Stream
            bw.close();

            System.out.println("Data successfully appended at the end of file");

            return true;

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * removeFollower removes userToFollow from currentUser's follow.txt.
     * 
     * This goes through currentUser's follow.txt and copies all its lines
     * (except the follower to remove) to a temp file, deletes follow.txt
     * and makes the temp file follow.txt.
     * 
     * @param   currentUser The user that wants to remove the follower.
     *          userToFollow    The follower that will be removed.
     *          
     * @return  True if follower was successfully removed, false otherwise.
     */
    public static boolean removeFollower(String currentUser,String usertoFollow){
        try{
            File inputFile = new File(currentUser + "\\follow.txt");
            File tempFile = new File("myTempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = usertoFollow;
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close(); 
            reader.close();

            System.out.println(inputFile.delete());
            boolean successful = tempFile.renameTo(inputFile);

            return true;
        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;

        }
    }

    /**
     * isFollowing checks if username is following otherUser.
     * 
     * It checks username's follow.txt to see if otherUser is one of the lines.
     * 
     * @param   username    The user to check.
     *          otherUser   The user that username is or isn't following.
     *          
     * @return True if username is following otherUser, false otherwise.
     */
    public static boolean isFollowing(String username, String otherUser){
        try{
            File inputFile = new File(username + "\\follow.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(otherUser)) {
                    reader.close();
                    return true;
                }
            }
        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;
        }

        return false;
    }  

    /**
     * checkAdmin checks if the given user is an admin.
     * 
     * This is done by checking if the given user is in admin.txt
     * 
     * @param   user    The user to check.
     * 
     * @return  True if the user is an admin, false otherwise.
     */
    public static boolean checkAdmin(String username){
        File fileName2 = new File("admin.txt");
        
        // Create admin file if it doesn't exist
        if(!fileName2.exists()){
            try {
                fileName2.createNewFile();
                System.out.println("New File admin.txt created");
            }
            catch(IOException e) {
                System.out.println("Error creating admin file");
            }
        }
        else{
            System.out.println("admin.txt already exists.");
        }
        
        int count = 0;
        try{
            Scanner scan = new Scanner(fileName2);

            while (scan.hasNext()){
                String line = scan.nextLine().toLowerCase().toString();
                if (line.equals(username)){
                    System.out.println(line);
                    count++;
                }

            }
        }catch(Exception e){
            System.out.println("Error");

        } 

        if (count > 0) {
            System.out.println("Is admin");
            return true;
        } else{
            System.out.println("Is NOT admin");
            return false;
        }
    }

    /**
     * deleteUser deletes the given user from ImageUpload.
     * 
     * This is done by deleting everything in the user's folder,
     * deleting the user's folder, and removing the user from
     * Database.txt
     * 
     * This should only be called by admins. This check is done
     * in the frontend.
     * 
     * @param   user    The user to delete.
     * 
     * @return  True if the user was successfully deleted, false otherwise.
     */
    public static boolean deleteUser(String user){

        //This part deletes everything in a users folder

        //folderName can be replaced with username
        File folder = new File(user);
        if (!folder.exists()){
            System.out.println("Doesn't exist.");
        }

        
        File[] listOfFiles = folder.listFiles();

        int size = listOfFiles.length;
        System.out.println("Number of files: " + size);

        if (size == 0){
            System.out.println("Folder is empty.");
        } else{

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println("File Name: " + file.getName());
                    //System.out.println("Absolute path:" + file.getAbsolutePath());
                    File fileToBeDeleted = new File (file.getAbsolutePath());
                    try{
                        if (fileToBeDeleted.delete()){
                            System.out.println("File Deleted");
                        }else{
                            System.out.println("File deletion failed.");
                        }
                    }
                    catch(Exception e){
                        System.out.println("Error");
                    }
                } 
            }
        }
        
        folder.delete();

        
        // // this next part deletes the user from database.txt
        Formatter x;
        try{
            x = new Formatter ("temporarySearchFile2.txt");
            x.close();
        }
        catch (Exception e) {
            System.out.println("An error occured.");
        }

       
        try{
            Scanner scan = new Scanner(new File("Database.txt"));
            int count = 0;
            while (scan.hasNext()){
                
                String line2 = scan.nextLine().toString();
                String line2Username = line2.split(" ")[0];
                if (!line2Username.equals(user)){
                    

                    try
                    {
                        FileWriter fw = new FileWriter("temporarySearchFile2.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw);
                        out.println(line2);
                        
                        out.close();
                        bw.close();
                        fw.close();
                        
                        
                    } catch (IOException e) {
                        //exception handling left as an exercise for the reader
                        System.out.println("temporarySearchFile2 has NOT been created."); 
                    }

                    count++;

                    
                }

            }
            
            scan.close();
            
            if(count == 0){
                System.out.println("Nothing found.");
            }
        }catch(Exception e) {
        
        }
        
        File DatabaseFile = new File("Database.txt");
        File deleteDatabaseFile = new File(DatabaseFile.getAbsolutePath());
        deleteDatabaseFile.delete();
        
        File newDatabaseFile = new File("temporarySearchFile2.txt");
        newDatabaseFile.renameTo(new File("Database.txt"));
        
        return true;
    }
}
