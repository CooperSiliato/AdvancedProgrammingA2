package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    private UserService userService = new UserService();

    public void handleRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        

        // Create a new user
        User newUser = new User(username, password, firstName, lastName);
        userService.registerUser(newUser);

        try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
		    Parent root = loader.load();

		    // Create a new scene and set it on the stage
		    Scene dashboardScene = new Scene(root);
		    Stage stage = (Stage) usernameField.getScene().getWindow();
		    stage.setScene(dashboardScene);
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}