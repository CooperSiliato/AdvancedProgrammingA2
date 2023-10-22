package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditProfileController {
    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField newFirstNameField;

    @FXML
    private TextField newLastNameField;

    private UserService userService;
    
    private DashboardController dashboardController;
    
    private User currentUser;
    
    public void setCurrentUser(User user) {
        currentUser = user;
    }

	public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public EditProfileController() {
        userService = new UserService();
    }
    
    public void initialize() {
        if (dashboardController != null && dashboardController.getCurrentUser() != null) {
            User currentUser = dashboardController.getCurrentUser();
            newUsernameField.setText(currentUser.getUsername());
            newPasswordField.setText(currentUser.getPassword());
            newFirstNameField.setText(currentUser.getFirstName());
            newLastNameField.setText(currentUser.getLastName());
        }
    
    

        // Populate the input fields with the user's current information
        if (currentUser != null) {
            newUsernameField.setText(currentUser.getUsername());
            newPasswordField.setText(currentUser.getPassword());
            newFirstNameField.setText(currentUser.getFirstName());
            newLastNameField.setText(currentUser.getLastName());
        }
    }


    public void saveChanges() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String newFirstName = newFirstNameField.getText();
        String newLastName = newLastNameField.getText();
    

        User currentUser = UserService.getCurrentUser();

        if (currentUser != null) {
            // Update the user's information
            currentUser.setUsername(newUsername);
            currentUser.setPassword(newPassword);
            currentUser.setFirstName(newFirstName);
            currentUser.setLastName(newLastName);

            // Save the updated information to the CSV file
            userService.updateUser(currentUser);

            showAlert("Profile Updated", "Your profile information has been updated.");
            backToDashboard();
            
        } else {
            showAlert("Error", "Failed to update profile information.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
    public void backToDashboard() {
        // Load the dashboard FXML and show it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) newUsernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
