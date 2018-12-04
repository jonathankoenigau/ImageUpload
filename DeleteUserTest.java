import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the user delete feature for admins.
 *
 * @author  Jonathan Koenig
 */
public class DeleteUserTest
{
    /**
    String admin;
    */
    String user;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // Create user and admin
        user = "UserA";
        Backend.addUser(user, "Test");
        
        /**
        admin = "AdminA";
        Backend.addUser(admin, "Test");
        */
        
        // Give UserA an image
        // Create an empty image
        File testFile = new File("testDelete.jpg");
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!testFile.exists()) {
            try {
                testFile.createNewFile();
                // Upload image with the tag "TestTag"
                Backend.moveImage(testFile.getAbsolutePath(), user, new String[]{"TestTag"});
            }
            catch (IOException e) {
                System.err.println("IOException at setup:");
                e.printStackTrace();
            }
        }
        
        /**
        // Add AdminA to the admin list
        File file =new File("admin.txt");
        /* This logic is to create the file if the
         * file is not already present
         */
        /**
        if(!file.exists()){
            file.createNewFile();
            System.out.println("New File admin.txt created");
        }

        //Here true is to append the content to file
        FileWriter fw = new FileWriter(file,true);
        //BufferedWriter writer give better performance
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(admin);
        //Makes new line for the next user
        bw.newLine();
        //Closing BufferedWriter Stream
        bw.close();
        fw.close();
        */
    }
    
    @Test
    public void searchImage()
    {
        // Get images from UserA
        File[] userABeforeDelete = Backend.getUserImages(user);
        // This should only have 1 image
        assertEquals(1, userABeforeDelete.length);
        
        /**
        // Admin deletes UserA
        Backend.deleteUser(user);
        */
        
        // Get images from UserA
        File[] userAAfterDelete = Backend.getUserImages(user);
        // The user has been deleted, so they won't have any images
        assertEquals(0, userAAfterDelete.length);
        
        // Check if user is in Database.txt
        try
        {
            boolean userAExists = false;
            Scanner sc = new Scanner(new File("Database.txt"));
            while(sc.hasNextLine() && !userAExists)
            {
                if(sc.nextLine().equals(user))
                    userAExists = true;
            }
            
            assertFalse(userAExists);
        }
        catch (IOException e) {
            System.err.println("IOException in test:");
            e.printStackTrace();
        }
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // user was already removed, so just delete the Database.txt
        
        // Remove generated database file
        File database = new File("Database.txt");
        database.delete();
    }
}
