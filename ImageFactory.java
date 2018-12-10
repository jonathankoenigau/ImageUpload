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
 *           Jonathan Koenig
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

    /**
     * addTagsForImage uses the tags array to make a tag file for the
     * provided image file.
     * 
     * The tag file is a .txt file with the same name as the given image and
     * in the same directory as the given image.
     * 
     * @param   imageFile The image to add the tag for.
     *          tags The strings to use ass tags.
     */
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
            fw.close();
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
            return new File[0];
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

    /**
     * searchForImages() returns images that users have uploaded that have
     * a tag that matches one of the tags in the given array.
     * 
     * This method checks each user's images in the order Database.txt gives.
     * 
     * @param   tags    The tags that should be used to find a matching image.
     * 
     * @return  A file array of all the matching images.
     */
    public static File[] searchForImages(String[] tags)
    {
        File[] searchFiles = new File[0];
        
        try {
            Scanner sc = new Scanner(new File("Database.txt"));

            // Loop through all users
            while(sc.hasNextLine())
            {
                // Get each user
                String user = sc.nextLine().toString();
                if(!user.equals(""))
                {
                    user = user.substring(0, user.indexOf(" "));

                    // Get user's images
                    File[] listOfFiles = getFilePaths(user);

                    for (File file : listOfFiles) {
                        // Get Tag File
                        String tempTag = file.getAbsolutePath();
                        tempTag = tempTag.substring(0, tempTag.lastIndexOf('.')) + ".txt";

                        File tagFile = new File(tempTag);

                        // Search through tag file
                        boolean tagMatch = false;
                        Scanner tagsc = new Scanner(tagFile);
                        while(tagsc.hasNextLine() && !tagMatch)
                        {
                            String fileTag = tagsc.nextLine().toString();

                            for(String searchTag : tags)
                            {
                                System.out.println("Given: " + searchTag + ", File: " + fileTag);
                                if((searchTag.toLowerCase()).equals(fileTag.toLowerCase()))
                                    tagMatch = true;
                            }
                        }
                        
                        tagsc.close();

                        if(tagMatch)
                            searchFiles = combineFileArrays(searchFiles, new File[]{file});
                    } 
                }
            }

            sc.close();
            return searchFiles;
        }
        catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
            
            return searchFiles;
        }
    }

    /**
     * deleteImage deletes the given image and its corresponding tag file.
     * 
     * @param   imagePath The image file that will be deleted and the name
     *                    used to delete the same named .txt file.
     */
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

    /**
     * getFollowerFilePaths gets all the images from the users 
     * that username is following and return a File array of their locations.
     * 
     * @param   username The user that is requesting their follower's images.
     * 
     * @return File[] of file paths for each image.
     */
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
                File[] combine = combineFileArrays(listOfFiles, userListOfFiles);

                listOfFiles = combine;
            }

            scan.close();
            return listOfFiles;
        }catch(Exception e){
            System.out.println("Error");
            return new File[]{new File("error.png")};
        }
    }

    /**
     * getUserFromImage gets the username of the user who uploaded the
     * given image.
     * 
     * This is done by getting the name of the folder the file is currently in.
     * 
     * @param   imagePath The image file used to find the user.
     * 
     * @return String of the user's username.
     */
    public static String getUserFromImage(String imagePath) {
        // Get the last two indexes of "\"
        int lastIndex = imagePath.lastIndexOf("\\");
        int secondLastIndex = imagePath.lastIndexOf("\\", lastIndex - 1);

        System.out.println(lastIndex);
        System.out.println(secondLastIndex);
        System.out.println(imagePath.substring(secondLastIndex + 1, lastIndex));
        return imagePath.substring(secondLastIndex + 1, lastIndex);
    }

    /**
     * combineFileArrays combines the file arrays arrayOne and arrayTwo
     * and outputs one array.
     * 
     * @param   arrayOne    The first array to combine.
     *          arrayTwo    The second array to combine.
     *          
     * @return  A combined array of both given arrays. This array has
     *          arrayOne's files first, then arrayTwo's.
     */
    private static File[] combineFileArrays(File[] arrayOne, File[] arrayTwo) {
        File[] combine = new File[arrayOne.length + arrayTwo.length];
        int i = 0;
        for (File file : arrayOne) {
            combine[i] = file;
            i++;
        }
        for (File file : arrayTwo) {
            combine[i] = file;
            i++;
        }

        return combine;
    }
} 

/**
 * ImageFilter is a class used to filter out what files are returned using
 * folder.listFiles().
 * 
 * This is mainly used to only obtain image files from a user's folder.
 * 
 * @author  Jonathan Koenig
 */
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