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
    private int lastAssignedId;

    public void handleRegistration() {
    	
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        
        int newUserId = determineNewUserId();
        boolean isVIP = false;

        // Create a new user
        User newUser = new User(newUserId, username, password, firstName, lastName, isVIP);
        userService.registerUser(newUser);
        
        setLastAssignedId(newUserId);

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
    
    private int determineNewUserId() {
        // Read the users from the CSV file and find the highest ID
    	
        int highestId = userService.getHighestUserId();

        // The new ID is one greater than the highest ID
        return highestId + 1;
    }

	public int getLastAssignedId() {
		return lastAssignedId;
	}

	public void setLastAssignedId(int lastAssignedId) {
		this.lastAssignedId = lastAssignedId;
	}
}
