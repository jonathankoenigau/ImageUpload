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
     * @return boolean. If true, the user was successfully added and a
     * directory for the user was created.
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
}