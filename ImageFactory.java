import java.io.File; 
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;
import java.nio.*;

// Displaying file property and move file
class ImageFactory
{ 
    public static void moveImage(String filePath, String username) { 
        //pass the filename or directory name to File object 
        File f = new File(filePath); 

        //apply File class methods on File object
        //these just give us extra information on our files
        System.out.println("File name :"+f.getName()); 
        System.out.println("Path: "+f.getPath()); 
        System.out.println("Absolute path:" +f.getAbsolutePath()); 
        System.out.println("Parent:"+f.getParent()); 

        //checks to see if it exists
        System.out.println("Exists :"+f.exists()); 
        if(f.exists()) 
        { 
            //more extra information about the file
            System.out.println("Is writeable:"+f.canWrite()); 
            System.out.println("Is readable"+f.canRead()); 
            System.out.println("Is a directory:"+f.isDirectory()); 
            System.out.println("File Size in bytes "+f.length()); 

            //Destination should be username because the folder is named after the user!!!!
            File f2 = new File(username); 

            //More information about the users folder
            System.out.println("File name :"+f2.getName()); 
            System.out.println("Path: "+f2.getPath()); 
            System.out.println("Absolute path:" +f2.getAbsolutePath()); 
            System.out.println("Parent:"+f2.getParent()); 
            System.out.println("Exists :"+f2.exists()); 
            if(f.exists()) 
            { 
                //more info about users folder
                System.out.println("Is writeable:"+f2.canWrite()); 
                System.out.println("Is readable"+f2.canRead()); 
                System.out.println("Is a directory:"+f2.isDirectory()); 
                System.out.println("File Size in bytes "+f2.length());

                //This is where the move happens
                File oldFile = new File(f.getAbsolutePath());

                if (oldFile.renameTo(new File(f2.getAbsolutePath() + "\\" + f.getName()))){
                    System.out.println("The file was moved succesfully.");
                }
                else{
                    System.out.println("The file was not moved.");
                }

            
            } 
        } 
    } 
} 