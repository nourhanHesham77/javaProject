package Users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that validates user logins against a file of stored usernames and passwords.
 */
public class LoginValidator {

    private final String filePath;

    /**
     * Constructs a new LoginValidator object with the given file path.
     *
     * @param filePath the path to the file of stored usernames and passwords
     */
    public LoginValidator(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Validates a user's login credentials against the stored usernames and passwords.
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if the username and password match a stored username and password, false otherwise
     */
    public boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];
                if (storedUsername.equals(username.toLowerCase()) && storedPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Do nothing - the login validation will fail if there is an exception reading the file
        }
        return false;
    }
}