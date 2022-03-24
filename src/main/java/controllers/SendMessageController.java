package controllers;

import domain.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

import java.time.LocalDateTime;

public class SendMessageController {
    @FXML
    Button cancelButton;
    @FXML
    Button sendButton;
    @FXML
    TextField messageField;

    private Service service;
    private Stage dialogStage;
    private String username;

    public void setService(Service service, Stage stage, String username){
        this.service = service;
        this.dialogStage = stage;
        this.username = username;
    }

    public void handleCancelButton(ActionEvent actionEvent){
        dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleSendButton(ActionEvent actionEvent){
        if(messageField.getText().isEmpty()){
            MessageAlert.showErrorMessage(null, "Cannot send an empty message");
        }
        else{
            service.addMessage(username, messageField.getText());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION , "Send message", "Message was sent");
            dialogStage.close();
        }
    }

}
