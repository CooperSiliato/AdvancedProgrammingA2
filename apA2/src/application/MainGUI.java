package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.PostManager;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
        // Load the login view from the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));

        // Set up the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Your Application Name");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
       
        
        PostManager postManager = new PostManager();
        UserService userService = new UserService();
        userService.cleanCSVFile("users");
		userService.cleanCSVFile("posts");
		 launch(args);
        try {
            postManager.loadPostsFromFile("csvfiles/posts.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
