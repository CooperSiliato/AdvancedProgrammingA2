package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class AddPostController {
    @FXML
    private TextField contentField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField likesField;
    @FXML
    private TextField sharesField;
    @FXML
    private TextField dateTimeField;
    @FXML
    private TextField IDField;

    public void addPost() {
        // Get the values from the input fields
        String id = IDField.getText();
        String content = contentField.getText();
        String author = authorField.getText();
        String likes = likesField.getText();
        String shares = sharesField.getText();
        String dateTime = dateTimeField.getText();

        // Create a CSV line
        String newPost = id + "," + content + "," + author + "," + likes + "," + shares + "," + dateTime;

        // Append the new post to the CSV file
        try (FileWriter writer = new FileWriter("csvfiles/posts.csv", true)) {
            writer.write(newPost + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the input fields after adding the post
        IDField.clear();
        contentField.clear();
        authorField.clear();
        likesField.clear();
        sharesField.clear();
        dateTimeField.clear();
    }
    
    @FXML
    public void backToDashboard() {
        // Load the dashboard FXML and show it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) contentField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
