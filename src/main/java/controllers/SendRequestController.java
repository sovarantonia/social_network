package controllers;

import com.example.begin1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;
import validator.ValidationException;

import java.io.IOException;

public class SendRequestController {
    @FXML
    TextField usernameField;
    @FXML
    Button cancelButton;
    @FXML
    Button sendButton;
    @FXML
    Button allSentRequestsButton;

    private Service service;
    private Stage dialogStage;

    public void setService(Service service, Stage dialogStage){
        this.service = service;
        this.dialogStage = dialogStage;
    }

    public void handleCancelButton(ActionEvent actionEvent){
        dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleSentRequestButton(ActionEvent actionEvent) throws IOException{
        showSentFriendshipRequestsDialog();
    }

    public void handleSendFriendshipRequest(ActionEvent actionEvent){
        if(usernameField.getText().isEmpty() || usernameField.getText().isBlank()){
            MessageAlert.showErrorMessage(null, "Enter a username");
        }else if(service.getUser(usernameField.getText()) == null){
                MessageAlert.showErrorMessage(null, "User does not exist");
            }
        else{
            try{
                service.sendRequest(service.getUser(usernameField.getText()));

            }catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }

    }

    public void showSentFriendshipRequestsDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sent-requests.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage friendshipRequestsDialog = new Stage();

        Scene scene = new Scene(root);
        friendshipRequestsDialog.setScene(scene);

        SentRequestsController sentRequestsController = fxmlLoader.getController();
        sentRequestsController.setService(service, friendshipRequestsDialog);

        friendshipRequestsDialog.show();
    }

}
