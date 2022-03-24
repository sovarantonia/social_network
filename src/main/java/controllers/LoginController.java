package controllers;

import com.example.begin1.HelloApplication;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;


import java.io.IOException;

public class LoginController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label loginStatus;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }


    public void handleSignupButton(ActionEvent actionEvent) throws IOException{
        showSignupDialog();
    }


    public void handleLoginButton(ActionEvent actionEvent) throws IOException{
        if (usernameField.getText().isEmpty() && passwordField.getText().isEmpty()) {
            loginStatus.setText("Enter username and password");
        } else {
            loginValidation();
        }
    }

    public void loginValidation() throws IOException{
        User user = service.getUser(usernameField.getText());
        if (user == null) {
            loginStatus.setText("User does not exist");
        } else if (!user.getPassword().equals(passwordField.getText())) {
                loginStatus.setText("Wrong password");
            }
        else{
            showMainPageDialog(user);
        }

    }

    public void showSignupDialog() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));

        AnchorPane root = fxmlLoader.load();

        Stage signupDialog = new Stage();
        signupDialog.setTitle("Create an account");

        Scene scene = new Scene(root);

        signupDialog.setScene(scene);

        RegisterController registerController = fxmlLoader.getController();

        registerController.setService(service, signupDialog);
        signupDialog.show();
    }

    public void showMainPageDialog(User currentUser) throws IOException{
        service.setCurrentUser(currentUser);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-page.fxml"));

        AnchorPane root = fxmlLoader.load();

        Stage mainPageDialog = new Stage();
        mainPageDialog.setTitle("Main page");
        Scene scene = new Scene(root);

        mainPageDialog.setScene(scene);

        MainPageController mainPageController = fxmlLoader.getController();

        mainPageController.setService(service, mainPageDialog);

        mainPageDialog.show();

    }



}
