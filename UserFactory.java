import java.io.*;
import java.lang.*;
import java.util.*;
import java.nio.*;

public class UserFactory{

    public static void addRecord (String username, String password) {
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
    
            }catch(IOException ioe){
                System.out.println("Exception occurred:");
                ioe.printStackTrace();
            }
        }
        else {
            System.out.println("Username and Directory already exists. Please enter different username.");
        }
    }
}