package api.payload;

public class AuthPayload {
	 private String username;
	    private String password;

	    // Required default constructor
	    public AuthPayload() {
	    }

	    // Parameterized constructor
	    public AuthPayload(String username, String password) {
	        this.username = username;
	        this.password = password;
	    }

	    // Required getters and setters
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
	
}
