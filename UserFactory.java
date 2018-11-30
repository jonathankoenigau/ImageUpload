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

public boolean removeFollower(String currentUser,String usertoFollow){
   try{
       File inputFile = new File("follow.txt");
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
boolean successful = tempFile.renameTo(inputFile);
    
    
    return true;
}catch(IOException ioe){
                    System.out.println("Exception occurred:");
                    ioe.printStackTrace();
                    return false;

}
}
}
