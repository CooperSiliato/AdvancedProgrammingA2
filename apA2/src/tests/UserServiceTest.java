package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import application.User;
import application.UserService;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class UserServiceTest {
    private UserService userService;
    private User testUser;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        testUser = new User(1, "testUser", "password123", "John", "Doe", false);
    }

    @Test
    public void testAuthenticateValidUser() {
        userService.registerUser(testUser);

        User authenticatedUser = userService.authenticate("testUser", "password123");
        assertNotNull(authenticatedUser);
        assertEquals(testUser.getId(), authenticatedUser.getId());
    }

    @Test
    public void testAuthenticateInvalidUser() {
        User authenticatedUser = userService.authenticate("invalidUser", "wrongPassword");
        assertNull(authenticatedUser);
    }

    @Test
    public void testRegisterUser() {
        userService.registerUser(testUser);

        User registeredUser = userService.authenticate("testUser", "password123");
        assertNotNull(registeredUser);
        assertEquals(testUser.getId(), registeredUser.getId());
    }

    @Test
    public void testGetAllUsers() {
        userService.registerUser(testUser);

        List<User> allUsers = userService.getAllUsers();
        assertTrue(allUsers.contains(testUser));
    }

    @Test
    public void testIsUsernameUnique() {
        userService.registerUser(testUser);

        assertFalse(userService.isUsernameUnique("testUser"));
        assertTrue(userService.isUsernameUnique("newUser"));
    }

    @Test
    public void testUpdateUser() {
        userService.registerUser(testUser);

        User updatedUser = new User(testUser.getId(), "newUsername", "newPassword", "Jane", "Doe", true);
        userService.updateUser(updatedUser);

        User updated = userService.authenticate("newUsername", "newPassword");
        assertNotNull(updated);
        assertEquals(updatedUser.getId(), updated.getId());
    }

    @Test
    public void testUpgradeToVIP() {
        userService.registerUser(testUser);

        userService.upgradeToVIP(testUser);

        User upgradedUser = userService.authenticate("testUser", "password123");
        assertTrue(upgradedUser.isVIP());
    }

    @Test
    public void testIsCurrentUserVIP() {
        userService.registerUser(testUser);

        User authenticatedUser = userService.authenticate("testUser", "password123");
        assertFalse(userService.isCurrentUserVIP());

        userService.upgradeToVIP(authenticatedUser);
        assertTrue(userService.isCurrentUserVIP());
    }

    @Test
    public void testCleanCSVFile(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("test.csv").toFile();
        File outputFile = tempDir.resolve("cleaned_test.csv").toFile();

        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("1,user1,password1,John,Doe,false\n");
            writer.write("\n"); // Empty line
            writer.write("2,user2,password2,Jane,Smith,true\n");
            writer.write("\n"); // Empty line
            writer.write("3,user3,password3,Bob,Johnson,false\n");
        }

        userService.cleanCSVFile(inputFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assertFalse(line.isEmpty());
            }
        }
    }
}

