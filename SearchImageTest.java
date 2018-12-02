import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the search image feature.
 *
 * @author  Jonathan Koenig
 */
public class SearchImageTest
{
    String[] usernames;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // Create user and folder
        usernames = new String[]{"UserA", "UserB", "UserC"};
        int i = 0;
        for(String username : usernames)
        {
            i++;
            System.out.println(username + Backend.addUser(username, "Test"));
            
            // Create an empty image
            File testFile = new File("testSearch.jpg");
            /* This logic is to create the file if the
             * file is not already present
             */
            if(!testFile.exists()) {
                try {
                    testFile.createNewFile();
                    // Upload image with tags "TestTag" and i
                    Backend.moveImage(testFile.getAbsolutePath(), username, new String[]{"TestTag", "" + i});
                }
                catch (IOException e) {
                    System.err.println("IOException at setup:");
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void searchImage()
    {
        // Get only image from UserA
        File[] beforeDelete = Backend.getUserImages(usernames[0]);
        // This should only have 1 image
        assertEquals(1, beforeDelete.length);
        
        // Search for UserA's file with "1"
        File[] userASearch = Backend.searchForImages(new String[]{"1"});
        // This search should only get 1 image
        assertEquals(1, userASearch.length);
        
        // The files found should be the same
        assertTrue(beforeDelete[0].getAbsolutePath().equals(userASearch[0].getAbsolutePath()));
        
        // Search for all 3 images using "TestTag"
        File[] fullSearch = Backend.searchForImages(new String[]{"TestTag"});
        
        // Check if 3 files were found
        assertEquals(3, fullSearch.length);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // Remove user folders
        for(String username : usernames)
        {
            Backend.deleteImage(new File(username + "\\testSearch.jpg"));
            
            File follow = new File(username + "\\follow.txt");
            follow.delete();
            
            File folder = new File(username);
            folder.delete();
        }
        
        // Remove generated database file
        File database = new File("Database.txt");
        database.delete();
    }
}
