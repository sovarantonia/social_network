package com.example.begin1;

import controllers.LoginController;
import domain.Friendship;
import domain.Message;
import domain.Request;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import repository.db.DbRepoFriends;
import repository.db.DbRepoMessage;
import repository.db.DbRepoRequest;
import repository.db.DbRepoUser;
import service.Service;
import validator.FriendshipValidator;
import validator.MessageValidator;
import validator.RequestValidator;
import validator.UserValidator;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){
        String url = "jdbc:postgresql://localhost:5432/schita_retea";
        String username = "postgres";
        String password = "050401";

        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        RequestValidator requestValidator = new RequestValidator();
        MessageValidator messageValidator = new MessageValidator();

        Repository<User, String> repoUser = new DbRepoUser(url, username, password, userValidator);
        Repository<Friendship, Long> repoFriends = new DbRepoFriends(url, username, password, friendshipValidator);
        Repository<Request, Long> repoRequest = new DbRepoRequest(url, username, password, requestValidator);
        Repository<Message, Long> repoMessage = new DbRepoMessage(url, username, password, messageValidator);

        Service service = new Service(repoUser, repoFriends, repoRequest, repoMessage, userValidator,
                friendshipValidator, requestValidator, messageValidator);

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }


    public static void main(String[] args) {
        launch();
    }
}