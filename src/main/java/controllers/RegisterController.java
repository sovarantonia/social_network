package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;
import validator.ValidationException;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label registerStatus;
    @FXML
    private Button cancelButton;

    private Service userService;

    private Stage dialogStage;

    public void setService(Service userService, Stage dialogStage) {
        this.userService = userService;
        this.dialogStage = dialogStage;
    }


    public void handleCancelButton(ActionEvent actionEvent) {
        dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        if (usernameField.getText().isEmpty() && firstNameField.getText().isEmpty() && lastNameField.getText().isEmpty()
                && passwordField.getText().isEmpty() && confirmPasswordField.getText().isEmpty()) {
            MessageAlert.showErrorMessage(null, "Enter your credentials");
        } else {
            registerValidation();

        }
    }

    public void registerValidation() {
        if (userService.getUser(usernameField.getText()) != null) {
            MessageAlert.showErrorMessage(null, "Username is already taken");
        } else {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                MessageAlert.showErrorMessage(null, "Passwords do no match");
            } else {
                try {
                    User user = new User(usernameField.getText(), firstNameField.getText(),
                            lastNameField.getText(), passwordField.getText());
                    userService.addUser(user);
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Register",
                            "The user is registered");
                    dialogStage.close();

                } catch (ValidationException e) {
                    MessageAlert.showErrorMessage(null, e.getMessage());
                }
            }
        }
    }


}
