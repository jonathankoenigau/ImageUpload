import java.io.File; 
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;
import java.nio.*;

// Displaying file property and move file
class ImageFactory
{ 
    public static String moveImage(String filePath, String username) { 
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
                File newFile = new File(f2.getAbsolutePath() + "\\" + f.getName());

                if (oldFile.renameTo(newFile)){
                    System.out.println("The file was moved succesfully.");
                    // This specific URL was found here:
                    // https://stackoverflow.com/a/8088561
                    return newFile.toURI().toString();
                }
                else{
                    System.out.println("The file was not moved.");
                    return "error.png";
                }

            } 
        } 
        
        return "error.png";
    } 

    public static File[] getFilePaths(String username){
        //folderName can be replaced with username
        File folder = new File(username);
        if (!folder.exists()){
            System.out.println("Doesn't exist.");
        }

        //File folder = new File("/Users/nurulhaque/CreatUser/userA");
        File[] listOfFiles = folder.listFiles();

        int size = listOfFiles.length;
        System.out.println("Number of files: " + size);

        if (size == 0){
            System.out.println("Folder is empty.");
        } else{

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println("File Name: " + file.getName());
                    System.out.println("Absolute path:" + file.getAbsolutePath());
                } 
            }
        }
        
        return listOfFiles;
    }
} 