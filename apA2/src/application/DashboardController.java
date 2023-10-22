package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Analyser;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class DashboardController {
    
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button editProfile;
    
    private User currentUser;
    
    public DashboardController() {
    	new Analyser("Your Analyser Name");
    }
    
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    
    public void setWelcomeMessage(User user) {
        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
    }

    public void editProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfileView.fxml"));
            Parent root = loader.load();
            EditProfileController editProfileController = loader.getController();

            // Pass the current user to the EditProfileController (assuming you have a method to get the current user)
            editProfileController.setCurrentUser(getCurrentUser());


            Scene editProfileScene = new Scene(root);

            // Get the current stage
            Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();

            // Set the EditProfileView as the active scene
            primaryStage.setScene(editProfileScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        


    public void addPost() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPostView.fxml"));
            Parent root = loader.load();
            loader.getController();
  
            Scene addPostScene = new Scene(root);

            // Get the current stage (primary stage) from the DashboardView
            Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();

            // Set the scene on the primary stage
            primaryStage.setScene(addPostScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void viewPosts() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("PostsMenuView.fxml"));
            Parent root = loader.load();
            PostsMenuController postsMenuController = loader.getController();
            postsMenuController.setDashboardController(this); // Pass the reference

            Scene postsMenuScene = new Scene(root);
            Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();
            primaryStage.setScene(postsMenuScene);
       } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void logout() {
    	try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
		    Parent root = loader.load();

		    // Create a new scene and set it on the stage
		    Scene dashboardScene = new Scene(root);
		    Stage stage = (Stage) welcomeLabel.getScene().getWindow();
		    stage.setScene(dashboardScene);
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	
    }
    
    
}
