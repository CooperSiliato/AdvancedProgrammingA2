package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class EditProfileController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    private DashboardController dashboardController;
    private User user;

    public void setUserData(User user) {
        this.user = user;
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void saveChanges() {
        String newFirstName = firstNameField.getText();
        String newLastName = lastNameField.getText();

        // Update user information
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);

        // Save the updated information to the CSV file
        UserService userService = new UserService();
        userService.updateUser(user);

        // Return to the dashboard
        dashboardController.setWelcomeMessage(user);
        dashboardController.editProfile();
    }

    public void cancel() {
        // Return to the dashboard without saving changes
        dashboardController.editProfile();
    }
}
