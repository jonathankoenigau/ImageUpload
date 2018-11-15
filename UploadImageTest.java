import java.io.File; 
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the uploading image feature and if it
 * correctly moves the file.
 *
 * @author  Jonathan Koenig
 */
public class UploadImageTest
{
    String username;
    File image;

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
        
        // Check if test image exists
        image = new File("UploadTestImage.png");
        
        if(!image.exists())
        {
            System.err.println("Test image not found, going to backup");
            image = new File("Error.png");
        }
    }
    
    @Test
    public void uploadImage()
    {
        // Get original image URI
        String originalFileName = image.getName();
        long originalFileSize = image.length();
        
        // File type check is done with the file opener, so that 
        // doesn't need to be tested
        
        // Move image
        ImageFactory.moveImage(image.getPath(),username, new String[]{"test"});
        
        // Get image from folder
        File[] userFiles = ImageFactory.getFilePaths(username);
        
        // Compare original image to moved image
        assertEquals(originalFileName,userFiles[0].getName());
        assertEquals(originalFileSize,userFiles[0].length());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // Move file back
        File[] userFiles = ImageFactory.getFilePaths(username);
        userFiles[0].renameTo(image);
        
        // Remove Directory if empty
        File folder = new File(username);
        folder.delete();
    }
}
