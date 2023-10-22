package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PostsMenuController {
	
	@FXML
    private Label menuLabel;

    private ViewPostsController postsMenuController;

    public void setPostsMenuController(ViewPostsController viewPostsController) {
        this.postsMenuController = viewPostsController;
    }

    @FXML
    private void retrievePostsByLikes() {
try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPostsView.fxml"));
            Parent root = loader.load();
            ViewPostsController viewPostController = loader.getController();
            viewPostController.setPostsMenuController(this); // Pass the reference
            viewPostController.initializePosts(); // Initialize and display the posts

            Scene viewPostsScene = new Scene(root);
            Stage primaryStage = (Stage) menuLabel.getScene().getWindow();
            primaryStage.setScene(viewPostsScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retrievePostById() {
        // Implement logic to retrieve a post by ID
        // You can access the DashboardController if needed
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
		// TODO Auto-generated method stub
		
	}
}

