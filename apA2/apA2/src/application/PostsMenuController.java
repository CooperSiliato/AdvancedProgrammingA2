package application;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


public class PostsMenuController {
	
	@FXML
    private Label menuLabel;
	
	private ViewPostsController viewPostsController;
	
	public void setPostsMenuController(ViewPostsController viewPostsController) {
        this.viewPostsController = viewPostsController;
    }
	
	public PostsMenuController() {
        // Initialize the viewPostsController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPostsView.fxml"));
        try {
            loader.load();
            viewPostsController = loader.getController();
            viewPostsController.setPostsMenuController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	

	@FXML
	private void retrievePostsByLikes() {
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter the Number of Posts");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Please enter the number of posts to retrieve:");

	    Optional<String> result = dialog.showAndWait();
	    
	    result.ifPresent(input -> {
	        try {
	            int numberOfPostsToRetrieve = Integer.parseInt(input);

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPostsView.fxml"));
	            Parent root = loader.load();
	            viewPostsController = loader.getController();
	            viewPostsController.setPostsMenuController(this);
	            viewPostsController.setNumberOfPostsToRetrieve(numberOfPostsToRetrieve);
	            viewPostsController.initializePosts();
	            
	            Scene viewPostsScene = new Scene(root);
	            Stage primaryStage = (Stage) menuLabel.getScene().getWindow();
	            primaryStage.setScene(viewPostsScene);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}

	@FXML
	private void retrievePostById() {
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Post ID");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Please enter the ID of the post you want to retrieve:");

	    Optional<String> result = dialog.showAndWait();

	    result.ifPresent(postIdText -> {
	        try {
	            int postId = Integer.parseInt(postIdText);

	            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPostsView.fxml"));
	            Parent root;
	            try {
	                root = loader.load();
	                viewPostsController = loader.getController();
	                viewPostsController.setPostsMenuController(this);

	                // Set the post ID to retrieve in the ViewPostsController
	                viewPostsController.setPostIdToRetrieve(postId);
	                viewPostsController.initializePosts();

	                Scene viewPostsScene = new Scene(root);
	                Stage primaryStage = (Stage) menuLabel.getScene().getWindow();
	                primaryStage.setScene(viewPostsScene);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (NumberFormatException e) {
	            // Display an error message if the user enters an invalid post ID
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setTitle("Error");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Please enter a valid post ID.");
	            errorAlert.showAndWait();
	        }
	    });
	}
	
	
	
	@FXML
	private void removePostById() {
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Enter Post ID to Remove");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Please enter the ID of the post you want to remove:");

	    Optional<String> result = dialog.showAndWait();

	    result.ifPresent(postIdText -> {
	        // Validate the input using a regular expression
	        if (postIdText.matches("\\d+")) {
	            int postId = Integer.parseInt(postIdText);

	            // Call the removePostById method in ViewPostsController to remove the post
	            viewPostsController.removePostById(postId);

	            // You may want to refresh the view after removing the post
	            viewPostsController.initializePosts();
	            Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Post removed successfully.");
                successAlert.showAndWait();
	        } else {
	            Alert errorAlert = new Alert(AlertType.ERROR);
	            errorAlert.setTitle("Error");
	            errorAlert.setHeaderText(null);
	            errorAlert.setContentText("Please enter a valid post ID.");
	            errorAlert.showAndWait();
	        }
	    });
	}





	

    @FXML
    private void exportPost() {
        // Implement logic to export a post
        // You can access the DashboardController if needed
    }
    
    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent root = loader.load();
            Scene dashboardScene = new Scene(root);
            Stage primaryStage = (Stage) menuLabel.getScene().getWindow();
            primaryStage.setScene(dashboardScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void setDashboardController(DashboardController dashboardController) {
		
	}
}

