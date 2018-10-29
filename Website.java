import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
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
        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);
        
        Scene mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
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
            root.getChildren().add(logInButton);
        }
        else
        {
            // User Label
            Label userLabel = new Label("Welcome, " + currentUser + "!");
            root.getChildren().add(userLabel);
        }
        
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
        root.getChildren().add(createAccountButton);
        
        
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
        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);
        
        Scene mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
        // Home button
        Button homeButton = new Button("Go back home");
        // Button OnClick
        homeButton.setOnAction(
            (ActionEvent event) ->
            {
                start(mainStage);
            }
        );
        // Add Button
        root.getChildren().add(homeButton);
        
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
        VBox root = new VBox();
        root.setPadding( new Insets(16) );
        root.setSpacing(16);
        
        Scene mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        
        // Website Elements
        
        // Home button
        Button homeButton = new Button("Go back home");
        // Button OnClick
        homeButton.setOnAction(
            (ActionEvent event) ->
            {
                start(mainStage);
            }
        );
        // Add Button
        root.getChildren().add(homeButton);
        
        
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
    public void basicSetup (Scene currentScene)
    {
        
    }
}
