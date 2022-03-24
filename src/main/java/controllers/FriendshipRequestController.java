package controllers;

import domain.Request;
import domain.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;

import java.util.List;

public class FriendshipRequestController {
    @FXML
    TableView<Request> requestTable;
    @FXML
    TableColumn<Request, String> usernameColumn;
    @FXML
    TableColumn<Request, Status> statusColumn;
    @FXML
    Button closeButton;

    private Service service;
    private ObservableList<Request> receivedRequest = FXCollections.observableArrayList();
    private Stage dialogStage;

    public void setService(Service service, Stage dialogStage){
        this.service = service;
        this.dialogStage = dialogStage;
        initialise();
    }

    public void initialise(){
        initRequests();
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestTable.setItems(receivedRequest);
    }

    public void initRequests(){
        List<Request> requests = service.showAllRequests();
        receivedRequest.setAll(requests);
    }

    public void handleCloseButton(ActionEvent actionEvent){
        dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleAcceptButton(ActionEvent actionEvent){
        Request request = requestTable.getSelectionModel().getSelectedItem();
        if(request == null){
            MessageAlert.showErrorMessage(null, "Select a request");
        }else{
            service.addAFriend(request);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Request",
                    "User was added in your friend list");
            service.removeRequest(request);
            initRequests();
        }

    }

    public void handleDeleteButton(ActionEvent actionEvent){
        Request request = requestTable.getSelectionModel().getSelectedItem();
        if(request == null){
            MessageAlert.showErrorMessage(null, "Select a request");
        }else{
            service.removeRequest(request);
            initRequests();
        }

    }
}
