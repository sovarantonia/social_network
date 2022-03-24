package validator;

import domain.User;

import java.util.regex.Pattern;

public class UserValidator {
    public void validate(User u) throws ValidationException {
        String message = "";
        Pattern special = Pattern.compile("[!@#$%&*()+=|<>?{}\\[\\]~-]");
        if (u.getUserName().isEmpty()) {
            message += "User name cannot be empty";
        }
        if (u.getFirstName().isEmpty()) {
            message += "First name cannot be empty";
        }
        if (u.getLastName().isEmpty()) {
            message += "Last name cannot be empty";
        }
        if (u.getPassword().isEmpty()) {
            message += "Password cannot be empty";
        }

        if (special.matcher(u.getUserName()).find()) {
            message += "User name cannot have special characters";
        }
        if (special.matcher(u.getFirstName()).find()) {
            message += "First name cannot have special characters";
        }
        if (special.matcher(u.getLastName()).find()) {
            message += "First name cannot have special characters";
        }
        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}
