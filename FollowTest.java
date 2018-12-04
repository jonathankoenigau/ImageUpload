import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the user follow feature.
 *
 * @author  Jonathan Koenig
 */
public class FollowTest
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
        usernames = new String[]{"UserA", "UserB"};
        for(String username : usernames)
        {
            System.out.println(username + Backend.addUser(username, "Test"));
        }
        
        // Create an empty image
        File testFile = new File("testFollowch.jpg");
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!testFile.exists()) {
            try {
                testFile.createNewFile();
                // Upload image with the tag "TestTag"
                Backend.moveImage(testFile.getAbsolutePath(), usernames[1], new String[]{"TestTag"});
            }
            catch (IOException e) {
                System.err.println("IOException at setup:");
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void searchImage()
    {
        // Get only image from UserB
        File[] userBImages = Backend.getUserImages(usernames[1]);
        // This should only have 1 image
        assertEquals(1, userBImages.length);
        
        // Get UserA's follower images before following anyone
        File[] userABefore = Backend.getFollowerImages(usernames[0]);
        // Since UserA hasn't followed someone, the list should be empty
        assertEquals(0, userABefore.length);
        
        // Have UserA follow UserB
        UserFactory.addFollower(usernames[0], usernames[1]);
        
        // Get UserA's follower images after following UserB
        File[] userAAfter = Backend.getFollowerImages(usernames[0]);
        // Since UserA is only following UserB, there should be 1 image
        assertEquals(1, userAAfter.length);
        
        // The file from UserB and the file from UserA's follower images should be the same
        assertTrue(userBImages[0].getAbsolutePath().equals(userAAfter[0].getAbsolutePath()));
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
        Backend.deleteImage(new File(usernames[1] + "\\testSearch.jpg"));
        for(String username : usernames)
        {
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
