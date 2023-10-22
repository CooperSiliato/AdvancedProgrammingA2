package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    private int numberOfPostsToRetrieve;
    
    private PostManager postManager;
    
    public void setNumberOfPostsToRetrieve(int numberOfPostsToRetrieve) {
        this.numberOfPostsToRetrieve = numberOfPostsToRetrieve;
    }
    
    public ViewPostsController() {
        new PostManager();
        postManager = new PostManager();
        try {
            postManager.loadPostsFromFile("csvfiles/posts.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDashboardController(DashboardController dashboardController) {
    }
    
    private int postIdToRetrieve = -1; // Initialize with an invalid value

    public void setPostIdToRetrieve(int postId) {
        postIdToRetrieve = postId;
    }
    

    public void initializePosts() {
        // Clear existing items in the ListView
        postListView.getItems().clear();

        List<Post> posts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/posts.csv"))) {
            String line;
            // Read and discard the first line (headers)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String content = parts[1];
                        String author = parts[2];
                        int likes = Integer.parseInt(parts[3]);
                        int shares = Integer.parseInt(parts[4]);
                        String dateTime = parts[5];

                        Post post = new Post(id, content, author, likes, shares, dateTime);
                        posts.add(post); // Add the post to the list
                    } catch (NumberFormatException e) {
                        // Handle the case where a row has invalid integer values
                        System.err.println("Skipped a row with invalid integer values: " + line);
                    }
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

            // Display the top N posts by likes or retrieve a specific post by ID
            if (postIdToRetrieve != -1) {
                // Retrieve a specific post by ID
                Post retrievedPost = posts.stream()
                        .filter(post -> post.getId() == postIdToRetrieve)
                        .findFirst()
                        .orElse(null);

                if (retrievedPost != null) {
                    // Create a string with the post details
                    String postDetails = "Post ID: " + retrievedPost.getId() +
                            "\nContent: " + retrievedPost.getContent() +
                            "\nAuthor: " + retrievedPost.getAuthor() +
                            "\nLikes: " + retrievedPost.getLikes() +
                            "\nShares: " + retrievedPost.getShares() +
                            "\nDate and Time: " + retrievedPost.getDateTime() + "\n";

                    addPostToView(postDetails);
                }
            } else {
                // Display the top N posts by likes
                int displayCount = Math.min(numberOfPostsToRetrieve, posts.size());
                for (int i = 0; i < displayCount; i++) {
                    Post sortedPost = posts.get(i);
                    postListView.getItems().add("Post ID: " + sortedPost.getId() +
                            "\nContent: " + sortedPost.getContent() +
                            "\nAuthor: " + sortedPost.getAuthor() +
                            "\nLikes: " + sortedPost.getLikes() +
                            "\nShares: " + sortedPost.getShares() +
                            "\nDate and Time: " + sortedPost.getDateTime() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void addPostToView(String postDetails) {
        postListView.getItems().clear(); // Clear existing items
        postListView.getItems().add(postDetails); // Add the retrieved post
    }
    
    

    public void removePostById(int postId) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/posts.csv"))){
            String line;
            boolean skipHeader = true; // Flag to skip the first line (header)

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    lines.add(line); // Add the header line back
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    int id = Integer.parseInt(parts[0]);
                    if (id != postId) {
                        // Add this line to the list of lines to keep
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("csvfiles/posts.csv"))){
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
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





