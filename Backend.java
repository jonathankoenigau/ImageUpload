
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
    
    public static boolean isValid(String password) {
		if (password.length() > 50) { 
			return false;
		} else {	
	 		char c;
			int count = 1; 
			for (int i = 0; i < password.length() - 1; i++) {
				c = password.charAt(i);
				if (!Character.isLetterOrDigit(c)) {		
					return false;
				} else if (Character.isDigit(c)) {
					count++;
					if (count < 6)	{	
						return false;
					}	
				}
			}
		}
		return true;
	}
}
    
    
    
    
    
    
}
