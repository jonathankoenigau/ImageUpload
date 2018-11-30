import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.event.*; 

import java.io.*;
import java.util.*;
import java.lang.Integer;
import java.lang.Double;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import nu.xom.*;

/**
 * Class: Website
 * 
 * This class is where the program starts.
 * 
 * It will show a layout similar to a website that will be used to run
 * other methods in the program.
 * 
 * @author Jonathan Koenig
 */
public class Website extends Application
{
    // Window Properties
    private double width = 1280;
    private double height = 720;

    private String currentUser = null;

    // Run this method to start the program
    public static void main(String[] args) 
    {
        try
        {
            launch(args);
        }
        catch (Exception error)
        {
            error.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }

    /**
     * This method will set up the initial scene, AKA the homepage
     * of the website.
     * 
     * @param mainStage This is where the scene is placed.
     */
    public void start(Stage mainStage)
    {
        mainStage.setTitle("Image Uploading");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements

        
        // custom code above --------------------------------------------
        mainStage.show();
    }

    /**
     * This method sets up the scene for the Create Account page.
     * 
     * @param mainStage This is where the scene is placed.
     */
    public void createAccountPage (Stage mainStage)
    {
        mainStage.setTitle("Create an Account");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements

        // A message showing that the account was created or wasn't created
        Label messageLabel = new Label("Initialized");
        messageLabel.setVisible(false);

        root.getChildren().add(messageLabel);

        // Username Row
        HBox usernameRow = new HBox();
        usernameRow.setPadding( new Insets(16) );
        usernameRow.setSpacing(16);

        Label usernameLabel = new Label("Username: ");

        TextField usernameField = new TextField();
        root.getChildren().add(usernameField);

        usernameRow.getChildren().addAll(usernameLabel, usernameField);
        root.getChildren().add(usernameRow);

        // Password Row
        HBox passwordRow = new HBox();
        passwordRow.setPadding( new Insets(16) );
        passwordRow.setSpacing(16);

        Label passwordLabel = new Label("Password: ");

        TextField passwordField = new TextField();
        root.getChildren().add(passwordField);

        passwordRow.getChildren().addAll(passwordLabel, passwordField);
        root.getChildren().add(passwordRow);

        // Confirm Password Row
        HBox passwordTwoRow = new HBox();
        passwordTwoRow.setPadding( new Insets(16) );
        passwordTwoRow.setSpacing(16);

        Label passwordTwoLabel = new Label("Confirm Password: ");

        TextField passwordTwoField = new TextField();
        root.getChildren().add(passwordTwoField);

        passwordTwoRow.getChildren().addAll(passwordTwoLabel, passwordTwoField);
        root.getChildren().add(passwordTwoRow);

        Button logInButton = new Button("Create Account");
        logInButton.setOnAction(
            (ActionEvent event) ->
            {
                // If the username field isn't blank, the passwords match,
                // the password is valid, and the username is valid.
                if(!usernameField.getText().equals("") &&
                passwordField.getText().equals(passwordTwoField.getText())
                && Backend.isPasswordValid(passwordField.getText())
                && Backend.isUsernameValid(usernameField.getText()))
                {
                    // Send Username and Password to a method that will store
                    // the data.
                    boolean succeeded = Backend.addUser(usernameField.getText(), passwordField.getText());

                    if(succeeded)
                    {
                        messageLabel.setText("The account was successfully created!");
                    }
                    else
                    {
                        messageLabel.setText("Account creation failed.");
                        // Include reason why the account wasn't created
                    }
                }
                else
                {
                    messageLabel.setText("Account creation failed.");
                    // Include reason why the account wasn't created
                }

                messageLabel.setVisible(true);
            }
        );
        root.getChildren().add(logInButton);
    }

    /**
     * This method sets up the scene for the Login page.
     * 
     * @param mainStage This is where the scene is placed.
     */
    public void logInPage (Stage mainStage)
    {
        mainStage.setTitle("Login");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements
        
        Label loginFailedLabel = new Label("Login Failed.");
        loginFailedLabel.setVisible(false);
        root.getChildren().add(loginFailedLabel);

        // Username Row
        HBox usernameRow = new HBox();
        usernameRow.setPadding( new Insets(16) );
        usernameRow.setSpacing(16);

        Label usernameLabel = new Label("Username: ");

        TextField usernameField = new TextField();

        usernameRow.getChildren().addAll(usernameLabel, usernameField);
        root.getChildren().add(usernameRow);

        // Password Row
        HBox passwordRow = new HBox();
        passwordRow.setPadding( new Insets(16) );
        passwordRow.setSpacing(16);

        Label passwordLabel = new Label("Password: ");

        TextField passwordField = new TextField();

        passwordRow.getChildren().addAll(passwordLabel, passwordField);
        root.getChildren().add(passwordRow);

        Button logInButton = new Button("Login");
        logInButton.setOnAction(
            (ActionEvent event) ->
            {
                // Change this to check if the account exists
                if(!usernameField.getText().equals("") 
                   && !passwordField.getText().equals(""))
                {
                    if(Backend.logInUser(usernameField.getText(), passwordField.getText()))
                    {
                        currentUser = usernameField.getText();
                        start(mainStage);
                    }
                    else {
                        loginFailedLabel.setVisible(true);
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                }
            }
        );
        root.getChildren().add(logInButton);
    }

    /**
     * This method sets up the scene for the Upload Image page.
     * 
     * @param mainStage This is where the scene is placed.
     */
    public void uploadImagePage (Stage mainStage)
    {
        mainStage.setTitle("Upload Image");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements

        Label fileLabel = new Label("None");
        root.getChildren().add(fileLabel);

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        ExtensionFilter imageFilter = new ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.jpeg");

        fileChooser.getExtensionFilters().add(imageFilter);

        Button fileButton = new Button("File");
        fileButton.setOnAction(
            (ActionEvent event) ->
            {
                File imageFileTemp = fileChooser.showOpenDialog(mainStage);
                if (imageFileTemp != null)
                {
                    fileLabel.setText(imageFileTemp.getPath());
                }
            }
        );

        root.getChildren().add(fileButton);
        
        // Tag Row
        HBox tagRow = new HBox();
        tagRow.setPadding( new Insets(16) );
        tagRow.setSpacing(16);

        Label tagLabel = new Label("Tags: ");

        TextField tagField = new TextField();

        tagRow.getChildren().addAll(tagLabel, tagField);
        root.getChildren().add(tagRow);

        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(
            (ActionEvent event) ->
            {
                // If a file was chosen to upload
                if(!fileLabel.getText().equals("None"))
                {
                    String[] tags = tagField.getText().split(" ");
                    File imagePath = Backend.moveImage(fileLabel.getText(), currentUser, tags);
                    imagePage(mainStage, imagePath);
                }
            }
        );
        // Add Button
        root.getChildren().add(uploadButton);
    }

    /**
     * This method sets up the scene for the Image page.
     * 
     * @param mainStage This is where the scene is placed.
     * @param imagePath This is used to get the image that should be shown on 
     * the page.
     * This can be changed if a better way to show the image is found.
     * @param userString This is the user that uploaded the image.
     * This can be changed if the user can be obtained from the path.
     */
    public void imagePage (Stage mainStage, File imagePath)
    {
        mainStage.setTitle("Image");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements
        
        // User who uploaded image
        String userString = ImageFactory.getUserFromImage(imagePath.getAbsolutePath());
        
        // If the image was uploaded by the logged in user, add a delete button
        if(currentUser.equals(userString))
        {
            Button deleteButton = new Button("Delete Image");
            deleteButton.setOnAction(
                (ActionEvent event) ->
                {
                    // Ask if the user really wants to delete the image
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Delete Image");
                    alert.setContentText("Are you sure you want to delete this image?");
                    Optional<ButtonType> result = alert.showAndWait();
                    // If the user presses OK
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Backend.deleteImage(imagePath);
                        userPage(mainStage, userString);
                    }
                }
            );
            root.getChildren().add(deleteButton);
        }
        
        ImageView image = new ImageView();
        // This specific URL was found here:
        // https://stackoverflow.com/a/8088561
        image.setImage(new Image(imagePath.toURI().toString()));
        root.getChildren().add(image);

        // Uploaded By Row
        HBox uploadedByRow = new HBox();
        uploadedByRow.setPadding( new Insets(16) );
        uploadedByRow.setSpacing(16);

        Label uploadedByLabel = new Label("Uploaded By: ");
        uploadedByRow.getChildren().add(uploadedByLabel);
        
        Button uploadedByButton = new Button(userString);
        uploadedByButton.setOnAction(
            (ActionEvent event) ->
            {
                userPage(mainStage, userString);
            }
        );
        uploadedByRow.getChildren().add(uploadedByButton);

        root.getChildren().add(uploadedByRow);
    }

    /**
     * This method set up the scene for a User's page.
     * 
     * @param mainStage This is where the scene is placed.
     * @param userString This is the user's name, which will be used to check 
     * if the user exists and used to display any image they uploaded.
     */
    public void userPage (Stage mainStage, String userString)
    {
        mainStage.setTitle(userString);

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements
        Label userHeaderLabel = new Label(userString);
        root.getChildren().add(userHeaderLabel);
        
        if(currentUser != null && !currentUser.equals(userString))
        {
            Button followButton = new Button("Follow");
            followButton.setOnAction(
                (ActionEvent event) ->
                    {
                        // Returns true if succeeded, returns false if failed
                        // Maybe do something with that
                        Backend.addFollower(currentUser, userString);
                    }
                );
            root.getChildren().add(followButton);
        }

        // If the user exists
        // Use a for loop to show all the images this user uploaded
        File[] userImages = Backend.getUserImages(userString);
        int image = 0;
        // Loop for rows
        // Source for method for rounding up:
        // https://stackoverflow.com/a/4540700
        for(int i = 0; i < (int) Math.ceil(userImages.length / 4.0); i++)
        {
            // Loop for each image
            HBox row = new HBox();
            row.setPadding( new Insets(16) );
            row.setSpacing(16);

            for(int j = 0; j < 4; j++)
            {
                if(!(image >= userImages.length))
                {
                    row.getChildren().add(clickableImage(mainStage, userImages[image]));
                    image++;
                }
            }
            
            root.getChildren().add(row);
        }

        // If the user doesn't exist
        // Show some text saying the user doesn't exist
    }
    
    /**
     * This method set up the scene for a search page.
     * 
     * @param mainStage This is where the scene is placed.
     * @param searchTags These are the tags that the user searched.
     * The images displayed are ones that include one of those tags.
     */
    public void searchPage (Stage mainStage, String[] searchTags)
    {
        mainStage.setTitle("Search");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements
        
        // Maybe make the search bar keep the tags that was searched for?
        
        Label searchHeaderLabel = new Label();
        root.getChildren().add(searchHeaderLabel);
        
        String searchName = "Search: ";
        
        for (String tag : searchTags)
        {
            searchName += tag;
            
            // If this isn't the last tag
            if(!(tag == searchTags[searchTags.length - 1]))
                searchName += ", ";
        }
        
        searchHeaderLabel.setText(searchName);
        
        // Get all matching images
        File[] searchImages = Backend.searchForImages(searchTags);
        int image = 0;
        // Loop for rows
        // Source for method for rounding up:
        // https://stackoverflow.com/a/4540700
        for(int i = 0; i < (int) Math.ceil(searchImages.length / 4.0); i++)
        {
            // Loop for each image
            HBox row = new HBox();
            row.setPadding( new Insets(16) );
            row.setSpacing(16);

            for(int j = 0; j < 4; j++)
            {
                if(!(image >= searchImages.length))
                {
                    // TODO: Get user from image file
                    row.getChildren().add(clickableImage(mainStage, searchImages[image]));
                    image++;
                }
            }
            
            root.getChildren().add(row);
        }
    }
    
    /**
     * This method set up the scene for the user's follow page.
     * 
     * @param mainStage This is where the scene is placed.
     */
    public void followPage (Stage mainStage)
    {
        mainStage.setTitle("Following");

        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();

        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);

        // Website Elements
        Label followHeaderLabel = new Label("Following");
        root.getChildren().add(followHeaderLabel);

        // If the user is following anyone
        // Use a for loop to show the images from the users
        // the current user is following
        File[] followImages = Backend.getFollowerImages(currentUser);
        int image = 0;
        // Loop for rows
        // Source for method for rounding up:
        // https://stackoverflow.com/a/4540700
        for(int i = 0; i < (int) Math.ceil(followImages.length / 4.0); i++)
        {
            // Loop for each image
            HBox row = new HBox();
            row.setPadding( new Insets(16) );
            row.setSpacing(16);

            for(int j = 0; j < 4; j++)
            {
                if(!(image >= followImages.length))
                {
                    row.getChildren().add(clickableImage(mainStage, followImages[image]));
                    image++;
                }
            }
            
            root.getChildren().add(row);
        }

        // If the user isn't following anyone
        // Show some text saying the user isn't following anyone
    }

    /**
     * clickableImage - Sets up an ImageView of a 200x200 image that can be
     * clicked to go to that image's page.
     * 
     * @param mainStage This is where the ImageView is shown.
     * 
     * @param imageURL This is the url to the image file
     * 
     * @param userString This is the user that uploaded the image. This should
     *        be found automatically in the future
     *        
     * @return ImageView An ImageView with the image in a 200x200 box that
     *         links to the image page for this image.
     */
    public ImageView clickableImage (Stage mainStage, File imagePath)
    {   
        ImageView image = new ImageView();
        // This specific URL was found here:
        // https://stackoverflow.com/a/8088561
        image.setImage(new Image(imagePath.toURI().toString()));
        image.setPreserveRatio(false);
        image.setFitWidth(200);
        image.setFitHeight(200);

        // This adds a mouse click event so that when the image is clicked,
        // it goes to the image's page.
        
        // Source for making an image clickable:
        // https://stackoverflow.com/a/25554726
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                imagePage(mainStage, imagePath);
                event.consume();
            }
        });
        
        return image;
    }

    /**
     * This method sets up parts of the website that all pages should share.
     * 
     * This includes the header of the website, upload/account buttons, etc.
     * 
     * @param mainStage This is where the scene is placed.
     * 
     * @return ScrollPane This returns a ScrollPane holding the header of the
     * website in a VBox. This VBox should be obtained using
     * ScrollPane.getContent().
     */
    public ScrollPane basicSetup (Stage mainStage)
    {
        // This will scroll the entire website
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(width, height);
        scroll.setFitToWidth(true);

        // This will hold the entire website
        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);

        scroll.setContent(root);

        // This will hold the top of the website
        VBox header = new VBox();
        header.setPadding( new Insets(16) );
        header.setSpacing(16);
        header.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(header);

        // Title of the website, could be replaced with an image
        Label title = new Label("Image Upload");
        title.setFont(new Font(36));

        header.getChildren().add(title);

        // *** The row under the title ***
        HBox headerRow = new HBox();
        headerRow.setPadding( new Insets(16) );
        headerRow.setSpacing(16);
        headerRow.setAlignment(Pos.TOP_CENTER);

        // Home Button
        Button homeButton = new Button("Go back home");
        // Button OnClick
        homeButton.setOnAction(
            (ActionEvent event) ->
            {
                start(mainStage);
            }
        );
        // Add Button
        headerRow.getChildren().add(homeButton);

        // If a user is logged in
        if(currentUser != null)
        {
            // Following Button
            Button followButton = new Button("Following");
            // Button OnClick
            followButton.setOnAction(
                (ActionEvent event) ->
                {
                    followPage(mainStage);
                }
            );
            // Add Button
            headerRow.getChildren().add(followButton);
            
            // Upload Image Button
            Button uploadImageButton = new Button("Upload an Image");
            // Button OnClick
            uploadImageButton.setOnAction(
                (ActionEvent event) ->
                {
                    uploadImagePage(mainStage);
                }
            );
            // Add Button
            headerRow.getChildren().add(uploadImageButton);
        }
        
        // Search Field
        TextField searchField = new TextField();
        headerRow.getChildren().add(searchField);
        
        // Search Button
        Button searchButton = new Button("\uD83D\uDD0D");
        // Button OnClick
        searchButton.setOnAction(
            (ActionEvent event) ->
            {
                // Check if searchField isn't empty
                
                // First check if not actually empty
                if(!searchField.getText().equals(""))
                {
                    // Check if it's just spaces
                    boolean notEmpty = false;
                    String[] tags = searchField.getText().split(" ");
                    
                    for(String tag : tags)
                    {
                        // If there is a tag that isn't a space, it's not empty
                        if(!searchField.getText().equals(""))
                            notEmpty = true;
                    }
                    
                    if(notEmpty)
                        searchPage(mainStage, tags);
                }
            }
        );
        // Add Button
        headerRow.getChildren().add(searchButton);

        // If a user isn't logged in
        if(currentUser == null)
        {
            // Login Button
            Button logInButton = new Button("Login");
            // Button OnClick
            logInButton.setOnAction(
                (ActionEvent event) ->
                {
                    logInPage(mainStage);
                }
            );
            // Add Button
            headerRow.getChildren().add(logInButton);
        }
        else
        {
            // User Button
            Button userButton = new Button(currentUser);
            userButton.setOnAction(
                (ActionEvent event) ->
                {
                    userPage(mainStage, currentUser);
                }
            );
            headerRow.getChildren().add(userButton);
        }

        // If a user isn't logged in
        if(currentUser == null)
        {
            // Create an account button
            Button createAccountButton = new Button("Create an account");
            // Button OnClick
            createAccountButton.setOnAction(
                (ActionEvent event) ->
                {
                    createAccountPage(mainStage);
                }
            );
            // Add Button
            headerRow.getChildren().add(createAccountButton);
        }
        else
        {
            // Log out button
            Button logOutButton = new Button("Log Out");
            // Button OnClick
            logOutButton.setOnAction(
                (ActionEvent event) ->
                {
                    currentUser = null;

                    // Goes back to home page, but maybe just send them
                    // back to the page they were looking at.
                    start(mainStage);
                }
            );

            // Add Button
            headerRow.getChildren().add(logOutButton);
        }

        header.getChildren().add(headerRow);

        return scroll;
    }
}
