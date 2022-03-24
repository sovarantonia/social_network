package controllers;


import domain.Message;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;

import java.time.LocalDateTime;
import java.util.List;

public class MessageHistoryController {
    @FXML
    TableView<Message> messageTableView;
    @FXML
    TableColumn<Message, String> fromColumn;
    @FXML
    TableColumn<Message, String> toColumn;
    @FXML
    TableColumn<Message, String> messageColumn;
    @FXML
    TableColumn<Message, LocalDateTime> dateColumn;
    @FXML
    Button closeButton;

    private ObservableList<Message> conversationHistory = FXCollections.observableArrayList();
    private Service service;
    private Stage dialogStage;
    private User user;

    public void setService(Service service, Stage dialogStage, User user){
        this.service = service;
        this.dialogStage = dialogStage;
        this.user = user;
        initialise();
    }

    public void initialise(){
        initConversation();
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        messageTableView.setItems(conversationHistory);
    }

    public void initConversation(){
        List<Message> messages = service.showMessageHistory(user);
        conversationHistory.setAll(messages);
    }

    public void handleCloseButton(ActionEvent actionEvent){
        dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }


}
