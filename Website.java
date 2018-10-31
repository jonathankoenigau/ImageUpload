import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.Font;
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
     * mainStage is where the scenes are placed.
     */
    public void start(Stage mainStage)
    {
        mainStage.setTitle("Image Uploading");
        
        // Initial Setup
        VBox root = basicSetup(mainStage);
        
        Scene mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
        
        
        
        // custom code above --------------------------------------------
        mainStage.show();
    }
    
    /**
     * This method sets up the scene for the Create Account page.
     */
    public void createAccountPage (Stage mainStage)
    {
        mainStage.setTitle("Create an Account");
        
        // Initial Setup
        VBox root = basicSetup(mainStage);
        
        Scene mainScene = new Scene(root, width, height);
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
                // If the username field isn't blank and the passwords match.
                // TO ADD: If user doesn't already exist, if username/password
                //         is too long, etc.
                if(!usernameField.getText().equals("") &&
                    passwordField.getText().equals(passwordTwoField.getText()))
                {
                    // Send Username and Password to a method that will store
                    // the data.
                    
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
        
        // custom code above --------------------------------------------
        mainStage.show();
    }
    
    /**
     * This method sets up the scene for the Login page.
     */
    public void logInPage (Stage mainStage)
    {
        mainStage.setTitle("Login");
        
        // Initial Setup
        VBox root = basicSetup(mainStage);
        
        Scene mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
        // *** Temporary Login Code ***
        
        
        TextField usernameField = new TextField();
        root.getChildren().add(usernameField);
        
        Button logInButton = new Button("Login");
        logInButton.setOnAction(
            (ActionEvent event) ->
            {
                if(!usernameField.getText().equals(""))
                {
                    currentUser = usernameField.getText();
                    start(mainStage);
                }
            }
        );
        root.getChildren().add(logInButton);
        
        // custom code above --------------------------------------------
        mainStage.show();
    }
    
    /**
     * This method sets up parts of the website that all pages should share.
     * 
     * This includes the header of the website, upload/account buttons, etc.
     */
    public VBox basicSetup (Stage mainStage)
    {
        // This will hold the entire website
        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);
        
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
        
        return root;
    }
}
