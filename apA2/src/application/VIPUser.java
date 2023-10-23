package application;

public class VIPUser extends User {
    public VIPUser(int id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName, true); // VIP users are set as VIP
    }

    // Additional methods and properties for VIP users can be added here.
}

