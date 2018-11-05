
/**
 * Write a description of class Backend here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Backend
{
    // instance variables - replace the example below with your own
    private UserFactory userFactory;

    /**
     * Constructor for objects of class Backend
     */
    public Backend()
    {
        userFactory = new UserFactory();
    }

    public void addUser (String username, String password, String confirmPassword) {
        userFactory.openFile();
        userFactory.addRecord(username, password, confirmPassword);
        userFactory.closeFile();
    }
}
