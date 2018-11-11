import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
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
                // If the username field isn't blank, the passwords match, and
                // the password is valid..
                // TO ADD: If user doesn't already exist, if username
                //         is too long, etc.
                if(!usernameField.getText().equals("") &&
                    passwordField.getText().equals(passwordTwoField.getText())
                    && Backend.isValid(passwordField.getText()))
                {
                    // Send Username and Password to a method that will store
                    // the data.
                    Backend.addUser(usernameField.getText(), passwordField.getText());
                    
                    messageLabel.setText("The account was successfully created!");
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
        
        // *** Temporary Login Code ***
        
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
        
        Button logInButton = new Button("Login");
        logInButton.setOnAction(
            (ActionEvent event) ->
            {
                // Change this to check if the account exists
                if(!usernameField.getText().equals(""))
                {
                    currentUser = usernameField.getText();
                    start(mainStage);
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
        
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(
            (ActionEvent event) ->
            {
                // A method to check if the file can be uploaded
                // (ex. If the file exists, if the file is too large)
                // and then uploads the file.
                // Return a boolean so this method knows if the file
                // successfully uploaded or not.
                
                // If it uploaded, go to the page where the image was uploaded.
                // This should go to the copied image in the user's folder
                if(!fileLabel.getText().equals("None"))
                {
                    // This method doesn't work with a full file path,
                    // so for now just use a test image.
                    // imagePage(mainStage, fileLabel.getText(), username);
                    String imagePath = Backend.moveImage(fileLabel.getText(), currentUser);
                    imagePage(mainStage, imagePath, currentUser);
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
    public void imagePage (Stage mainStage, String imagePath, String userString)
    {
        mainStage.setTitle("Image");
        
        // Initial Setup
        ScrollPane scroll = basicSetup(mainStage);
        VBox root = (VBox) scroll.getContent();
        
        Scene mainScene = new Scene(scroll, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
        // Maybe adding an image should be it's own method?
        // This method could also make all the images clickable so it would
        // go to that image's imagePage.
        ImageView image = new ImageView();
        image.setImage(new Image(imagePath));
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
        
        // Have a check to see if the user actually exists
        
        // If the user exists
        // Use a for loop to show all the images this user uploaded
        
        // If the user doesn't exist
        // Show some text saying the user doesn't exist
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
            // User Label
            Label userLabel = new Label("Welcome, " + currentUser + "!");
            headerRow.getChildren().add(userLabel);
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
