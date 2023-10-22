package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class UserService {
    private List<User> users;
    
    private static User currentUser;

    public UserService() {
        users = new ArrayList<>();
        loadUserDataFromCSV();
    }

    private void loadUserDataFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 5) {
                	int id = Integer.parseInt(userData[0]);
                    String username = userData[1];
                    String password = userData[2];
                    String firstName = userData[3];
                    String lastName = userData[4];
                    users.add(new User(id, username, password, firstName, lastName));
                    
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            	currentUser = user;
                return user;
            }
        }
        return null; // Authentication failed
    }

    public void registerUser(User user) {
        // Check if the username is already in use
    	int newID = getHighestUserId() + 1;
        if (isUsernameUnique(user.getUsername())) {
            try (FileWriter writer = new FileWriter("csvfiles/users.csv", true)) {
                String userLine = "\n" + newID + "," + user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName();
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

    public boolean isUsernameUnique(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
            	
                return false; // Username is not unique
            }
        }
        return true; // Username is unique
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public void updateUser(User updatedUser) {
        int updatedUserId = updatedUser.getId();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == updatedUserId) {
                // Update the user in the list
                users.set(i, updatedUser);
                // Save the updated users list to the CSV file
                saveUsersToCSV();
                return;
            }
        }
    }

    private void saveUsersToCSV() {
        try (FileWriter writer = new FileWriter("csvfiles/users.csv")) {
        	
            for (User user : users) {
                String userLine = user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName();
                writer.write(userLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getHighestUserId() {
        int highestId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 1) {
                    int userId = Integer.parseInt(userData[0]);
                    if (userId > highestId) {
                        highestId = userId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highestId;
    }
    
    public void cleanCSVFile(String fileName) {
        try {
            String inputFilePath = "csvfiles/" + fileName + ".csv";
            String outputFilePath = "csvfiles/cleaned_" + fileName + ".csv";
            
            removeLineBreaksFromCSV(inputFilePath, outputFilePath);
            // After cleaning, replace the original file with the cleaned one
            java.nio.file.Files.move(java.nio.file.Paths.get(outputFilePath), java.nio.file.Paths.get(inputFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Line breaks removed from CSV file.");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    public void removeLineBreaksFromCSV(String inputFilePath, String outputFilePath) throws java.io.IOException {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFilePath));
             java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(outputFilePath))) {
            java.lang.String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.newLine();
                }
            }
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
