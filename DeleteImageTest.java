import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the delete image feature and if it
 * correctly deletes the image and tag file.
 *
 * @author  Jonathan Koenig
 */
public class DeleteImageTest
{
    String username;
    File testFilePath;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // Create user and folder
        username = "ImageDeleteTest";
        File folder = new File(username);
        
        int loopNum = 1;
        while(folder.exists())
        {
            username = username + loopNum;
            loopNum++;
            folder = new File(username);
        }
        
        folder.mkdir();
        
        // Create an empty image
        File testFile = new File("testDelete.jpg");
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!testFile.exists()) {
            try {
                testFile.createNewFile();
            }
            catch (IOException e) {
                System.err.println("IOException at setup:");
                e.printStackTrace();
            }
        }
        
        // Upload empty image
        testFilePath = ImageFactory.moveImage(testFile.getPath(),username, new String[]{"test"});
    }
    
    // Not working for some reason
    @Test
    public void deleteImage()
    {
        // Results from searching in user folder before delete
        File[] beforeDelete = Backend.getUserImages(username);
        
        // Check if there is a file
        assertEquals(1, beforeDelete.length);
        
        // Delete image
        // The first index of beforeDelete is the uploaded image
        Backend.deleteImage(testFilePath);
        
        // Results from searching in user folder after delete
        File[] afterDelete = Backend.getUserImages(username);
        
        // Check if there isn't a file
        assertEquals(0, afterDelete.length);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // Remove Directory if empty
        File folder = new File(username);
        folder.delete();
    }
}
