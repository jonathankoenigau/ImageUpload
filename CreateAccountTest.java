import java.io.File;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the create account methods.
 *
 * @author  Jonathan Koenig
 */
public class CreateAccountTest
{
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // Check if database file already exists
        File oldDatabase = new File("Database.txt");
        
        if(oldDatabase.exists())
        {
            oldDatabase.renameTo(new File("originalDatabase.txt"));
        }
        
        // Original Create
        UserFactory.addRecord("a", "password");
        UserFactory.addRecord("b", "password");
        UserFactory.addRecord("c", "password");
    }
    
    @Test
    public void createAccount()
    {
        // Create new users
        assertFalse("User wasn't created since they already exist", UserFactory.addRecord("a", "password"));
        assertFalse("User wasn't created since they already exist", UserFactory.addRecord("b", "password"));
        assertFalse("User wasn't created since they already exist", UserFactory.addRecord("c", "password"));
        
        assertTrue("User was created", UserFactory.addRecord("d", "password"));
        assertTrue("User was created", UserFactory.addRecord("e", "password"));
        assertTrue("User was created", UserFactory.addRecord("f", "password"));
        
        // A user with a special character wouldn't be created
        assertFalse("Username had a special character in it", Backend.isUsernameValid("abc!"));
        
        // A password with a space wouldn't be created
        assertFalse("Password had a space in it", Backend.isPasswordValid("pass word"));
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // Remove test database
        File testDatabase = new File("Database.txt");
        testDatabase.delete();
        
        // Delete folders
        String[] names = {"a", "b", "c", "d", "e", "f"};
        for (String name : names) {
            File folder = new File(name);
            if(folder.exists())
                folder.delete();
        }
        
        // If database file already existed, put it back
        File oldDatabase = new File("originalDatabase.txt");
        
        if(oldDatabase.exists())
        {
            oldDatabase.renameTo(new File("Database.txt"));
        }
    }
}
