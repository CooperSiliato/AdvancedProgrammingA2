package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        // Create a User object before each test
        user = new User(1, "testUser", "password123", "John", "Doe", true);
    }

    @Test
    public void testGettersAndSetters() {
        // Test the getters and setters for each property
        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertTrue(user.isVIP());

        // Update user properties
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setVIP(false);

        // Check if the properties were updated
        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertFalse(user.isVIP());
    }

}

