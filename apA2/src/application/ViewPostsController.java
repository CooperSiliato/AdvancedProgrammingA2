package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Post;
import main.PostManager;
import javafx.scene.control.ListView;

public class ViewPostsController {
    @FXML
    private ListView<String> postListView;

    public ViewPostsController() {
        new PostManager();
    }

    public void setDashboardController(DashboardController dashboardController) {
    }
    
    


    public void initializePosts() {
        // Clear existing items in the ListView
        postListView.getItems().clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/posts.csv"))) {
            String line;
            // Read and discard the first line (headers)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String content = parts[1];
                    String author = parts[2];
                    int likes = Integer.parseInt(parts[3]);
                    int shares = Integer.parseInt(parts[4]);
                    String dateTime = parts[5];

                    Post post = new Post(id, content, author, likes, shares, dateTime);

                    // Add the post to the ListView
                    postListView.getItems().add("Post ID: " + post.getId() +
                        "\nContent: " + post.getContent() +
                        "\nAuthor: " + post.getAuthor() +
                        "\nLikes: " + post.getLikes() +
                        "\nShares: " + post.getShares() +
                        "\nDate and Time: " + post.getDateTime() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent root = loader.load();
            Scene dashboardScene = new Scene(root);
            Stage primaryStage = (Stage) postListView.getScene().getWindow();
            primaryStage.setScene(dashboardScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





