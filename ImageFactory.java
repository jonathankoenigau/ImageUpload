import java.io.File; 
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;
import java.nio.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.DirectoryNotEmptyException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * ImageFactory contains all methods associated with images.
 * 
 * It is used to move images to users' directories and return image
 * paths for displaying them.
 * 
 * @author   Nurul Haque
 */
class ImageFactory
{ 
    /**
     * moveImage moves the given image to the directory of the given user.
     * 
     * This is so the image is successfully uploaded and associated with the user.
     * 
     * @param   filepath The filepath to the image that should be moved.
     *          username The user that is uploading the image. The file
     *                   is moved to the folder of this user.
     * 
     * @return File for the image's new location.
     */
    public static File moveImage(String filePath, String username, String[] tags) { 
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

            // If file exists and is less than 20mb
            if(f.exists() && f.length() < 2e+7) 
            { 
                //more info about users folder
                System.out.println("Is writeable:"+f2.canWrite()); 
                System.out.println("Is readable"+f2.canRead()); 
                System.out.println("Is a directory:"+f2.isDirectory()); 
                System.out.println("File Size in bytes "+f2.length());

                //This is where the move happens
                File oldFile = new File(f.getAbsolutePath());
                File newFile = new File(f2.getAbsolutePath() + "\\" + f.getName());

                if (oldFile.renameTo(newFile)) {
                    System.out.println("The file was moved succesfully.");

                    // After the file is moved successfully, create a file for its tags
                    addTagsForImage(newFile, tags);
                    return newFile;
                    //return newFile.toURI().toString();
                }
                else {
                    System.out.println("The file was not moved.");
                    return new File("error.png");
                }
            } 
            else {
                System.out.println("The file was not moved.");
                return new File("error.png");
            }
        } 

        return new File("error.png");
    } 

    public static void addTagsForImage(File imageFile, String[] tags)
    {
        String imageTagsFileName = imageFile.getAbsolutePath();
        imageTagsFileName = imageTagsFileName.substring(0, imageTagsFileName.lastIndexOf('.')) + ".txt";
        System.out.println(imageTagsFileName);

        try{
            File imageTagsFile =new File(imageTagsFileName);

            /* This logic is to create the file if the
             * file is not already present
             */
            if(!imageTagsFile.exists()){
                imageTagsFile.createNewFile();
                System.out.println("New tag file created");
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(imageTagsFile,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);

            for (String tag : tags){
                bw.write(tag);
                //Makes new line for the next tag if not the last tag
                if(!tag.equals(tags[tags.length - 1]))
                    bw.newLine();
            }

            //Closing BufferedWriter Stream
            bw.close();
            System.out.println("Image successfully tagged.");

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }

    /**
     * getUserImages gets all the images in username's folder and returns
     * a File array of all the images.
     * 
     * @param   username The user that the images are associated with.
     * 
     * @return File[] of image files in the user's folder.
     */
    public static File[] getFilePaths(String username){
        //folderName can be replaced with username
        File folder = new File(username);
        if (!folder.exists()){
            System.out.println("Doesn't exist.");
        }

        // Make sure this only returns image files
        ImageFilter filter = new ImageFilter();
        //File folder = new File("/Users/nurulhaque/CreatUser/userA");
        File[] listOfFiles = folder.listFiles(filter);

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

    public static void deleteImage(String image) 
    { 
        System.out.println("Test:" + image);
        File imageFile = new File(image);
        String imageTagsFileName = imageFile.getAbsolutePath();
        imageTagsFileName = imageTagsFileName.substring(0, imageTagsFileName.lastIndexOf('.')) + ".txt";
        try
        { 
            if(Files.deleteIfExists(Paths.get(image)))
                System.out.println("Deletion successful."); 
            if(Files.deleteIfExists(Paths.get(imageTagsFileName)))
                System.out.println("Deletion successful."); 

        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 

    }

    public static File[] getFollowerFilePaths(String username){
        String fileName = (username + "\\follow.txt");

        try{
            Scanner scan = new Scanner(new File(fileName));

            File[] listOfFiles = new File[0];

            while (scan.hasNext()){
                String line = scan.nextLine().toString();

                File[] userListOfFiles = getFilePaths(line);
                int size = userListOfFiles.length;

                if (size == 0){
                    System.out.println("Folder is empty.");
                } else{

                    for (File file : userListOfFiles) {
                        if (file.isFile()) {

                            System.out.println("Absolute path:" + file.getAbsolutePath());
                        } 
                    }
                }

                // Combine arrays
                File[] combine = new File[listOfFiles.length + userListOfFiles.length];
                int i = 0;
                for (File file : listOfFiles) {
                    combine[i] = file;
                    i++;
                }
                for (File file : userListOfFiles) {
                    combine[i] = file;
                    i++;
                }
                
                listOfFiles = combine;
            }

            scan.close();
            return listOfFiles;
        }catch(Exception e){
            System.out.println("Error");
            return new File[]{new File("error.png")};
        }
    }
} 

class ImageFilter implements FileFilter
{
    @Override
    public boolean accept(File pathname) {
        String filePath = pathname.getAbsolutePath();
        String fileType = filePath.substring(filePath.lastIndexOf('.') + 1, filePath.length());

        if(fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg"))
            return true;
        else
            return false;
    }
}