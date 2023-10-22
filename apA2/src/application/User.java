package application;

public class User {
	private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(int id,String username, String password, String firstName, String lastName) {
        this.username = username;
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.id = id;
    }

    // Getters and setters for user properties
    public String getUsername() {
    	return username;
    }

    public void setUsername(String username) {
    	this.username = username;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}

