package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class UserService {
    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
        loadUserDataFromCSV();
    }

    private void loadUserDataFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    String username = userData[0];
                    String password = userData[1];
                    String firstName = userData[2];
                    String lastName = userData[3];
                    users.add(new User(username, password, firstName, lastName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Authentication failed
    }

    public void registerUser(User user) {
        // Check if the username is already in use
        if (isUsernameUnique(user.getUsername())) {
            try (FileWriter writer = new FileWriter("csvfiles/users.csv", true)) {
                String userLine = user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName();
                writer.write(userLine + "\n");
                
                users.add(user);
                showAlert("Account Created", "Your account has been created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show a failure alert
            showAlert("Username Taken", "Sorry, the username is already taken. Please choose another username.");
        }
    }

    private boolean isUsernameUnique(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
            	
                return false; // Username is not unique
            }
        }
        return true; // Username is unique
    }
    
    
    public void updateUser(User updatedUser) {
        List<User> updatedUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    String username = userData[0];
                    String password = userData[1];
                    String firstName = userData[2];
                    String lastName = userData[3];

                    if (username.equals(updatedUser.getUsername())) {
                        updatedUsers.add(updatedUser); // Add the updated user
                    } else {
                        updatedUsers.add(new User(username, password, firstName, lastName));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("csvfiles/users.csv")) {
            for (User user : updatedUsers) {
                String userLine = user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName();
                writer.write(userLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
