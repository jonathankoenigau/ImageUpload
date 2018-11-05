import java.io.*;
import java.lang.*;
import java.util.*;
import java.nio.*;

public class UserFactory{
    private Formatter x;
    
    public void openFile(){
        try{
            x = new Formatter ("Database.txt");
        }
        catch (Exception e) {
            System.out.println("An error occured.");
        }
    
    }
    
    public void addRecord () {
        Scanner sc = new Scanner (System.in);
        System.out.println ("Enter Username: ");
        String username = sc.next();
        System.out.println ("Enter Password: ");
        String password = sc.next();
        
        x.format("%s%s%s", username, "", password);
        
        File f = new File(username);
        if (!f.exists()){
            f.mkdir();
            System.out.println ("Directory created!");
        }
        else {
            System.err.println("Directory already exists.");
        }
    }
    
    public void closeFile(){
        x.close();
    }
}