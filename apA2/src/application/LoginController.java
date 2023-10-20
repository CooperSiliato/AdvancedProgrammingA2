package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User authenticatedUser = userService.authenticate(username, password);

        if (authenticatedUser != null) {
            try {
    		    FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
    		    Parent root = loader.load();
    		    DashboardController dashboardController = loader.getController();
                dashboardController.setWelcomeMessage(authenticatedUser);
                

    		    // Create a new scene and set it on the stage
    		    Scene dashboardScene = new Scene(root);
    		    Stage stage = (Stage) usernameField.getScene().getWindow();
    		    stage.setScene(dashboardScene);
    		} catch (Exception e) {
    		    e.printStackTrace();
    		}
         
        } else {
            // Display an error alert
        	showAlert("Error","Incorrect Username or Password");
        }
    }

    
    public void navigateToRegistrationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationView.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it on the stage
            Scene registrationScene = new Scene(root);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(registrationScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void handleExit() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close(); // Close the application
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

