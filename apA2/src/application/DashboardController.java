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
    
    private Analyser analyser;
    
    public DashboardController() {
    	analyser = new Analyser("Your Analyser Name");
    }

    
    public void setWelcomeMessage(User user) {
        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
    }

    public void editProfile() {
    	
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
    
//    @FXML
//    public void viewLikedPosts() {
//        try {
//        	
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPostsView.fxml"));
//            Parent root = loader.load();
//            ViewPostsController viewPostController = loader.getController();
//            viewPostController.setDashboardController(this); // Pass the reference
//            viewPostController.initializePosts(); // Initialize and display the posts
//
//            Scene viewPostsScene = new Scene(root);
//            Stage primaryStage = (Stage) welcomeLabel.getScene().getWindow();
//            primaryStage.setScene6(viewPostsScene);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    
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
