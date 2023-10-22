package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        List<Post> posts = new ArrayList<>(); // Create a list to store posts

        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/posts.csv"))) {
            String line;
            // Read and discard the first line (headers)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String content = parts[1];
                    String author = parts[2];
                    int likes = Integer.parseInt(parts[3]);
                    int shares = Integer.parseInt(parts[4]);
                    String dateTime = parts[5];
                    String owner = parts[6];

                    Post post = new Post(id, content, author, likes, shares, dateTime, owner);
                    posts.add(post); // Add the post to the list
                }
            }

            // Manually sort the list by 'likes' in descending order using bubble sort
            int n = posts.size();
            boolean swapped;
            do {
                swapped = false;
                for (int i = 1; i < n; i++) {
                    if (posts.get(i - 1).getLikes() < posts.get(i).getLikes()) {
                        // Swap the elements
                        Post temp = posts.get(i - 1);
                        posts.set(i - 1, posts.get(i));
                        posts.set(i, temp);
                        swapped = true;
                    }
                }
            } while (swapped);

            // Display the sorted posts
            for (Post sortedPost : posts) {
                postListView.getItems().add("Post ID: " + sortedPost.getId() +
                    "\nContent: " + sortedPost.getContent() +
                    "\nAuthor: " + sortedPost.getAuthor() +
                    "\nLikes: " + sortedPost.getLikes() +
                    "\nShares: " + sortedPost.getShares() +
                    "\nDate and Time: " + sortedPost.getDateTime() +
                    "\nOwner: " + sortedPost.getOwner() + "\n");
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

	public void setPostsMenuController(PostsMenuController postsMenuController) {
		// TODO Auto-generated method stub
		
	}

}





