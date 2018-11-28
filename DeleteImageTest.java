import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DeleteImageTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DeleteImageTest
{
    String username;
    String testFilePath;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // Create user and folder
        username = "ImageUploadTest";
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
        String[] beforeDelete = Backend.getUserImages(username);
        
        // Check if there is 1 file in the user's folder
        assertEquals(beforeDelete.length, 1);
        
        // Delete image
        // The first index of beforeDelete is the uploaded image
        Backend.deleteImage(testFilePath.substring(6, testFilePath.length()));
        
        // Results from searching in user folder after delete
        String[] afterDelete = Backend.getUserImages(username);
        
        // Check if there are 0 files in the user's folder
        assertEquals(afterDelete.length, 0);
    }
    

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // Delete tag file
        File[] userFiles = ImageFactory.getFilePaths(username);
        String imageTagsFileName = userFiles[0].getAbsolutePath();
        imageTagsFileName = imageTagsFileName.substring(0, imageTagsFileName.lastIndexOf('.')) + ".txt";
        
        File tagFile = new File(imageTagsFileName);
        tagFile.delete();
        
        // Remove Directory if empty
        File folder = new File(username);
        folder.delete();
    }
}
