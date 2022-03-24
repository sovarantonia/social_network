package controllers;


import domain.Request;
import domain.Status;
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

import java.util.List;

public class SentRequestsController {
    @FXML
    TableView<Request> sentRequestTable;
    @FXML
    TableColumn<Request, String> toColumn;
    @FXML
    TableColumn<Request, Status> statusColumn;
    @FXML
    Button closeButton;

    private Service service;
    private Stage dialogStage;
    private ObservableList<Request> sentRequest = FXCollections.observableArrayList();

    public void setService(Service service, Stage dialogStage) {
        this.service = service;
        this.dialogStage = dialogStage;
        initialise();
    }

    public void handleCloseButton(ActionEvent actionEvent) {
        dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleCancelButton(ActionEvent actionEvent) {
        Request request = sentRequestTable.getSelectionModel().getSelectedItem();
        if (request == null) {
            MessageAlert.showErrorMessage(null, "Select a friendship request");
        } else {
            service.cancelRequest(request);
            initSentRequests();
        }
    }

    public void initialise() {
        initSentRequests();
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        sentRequestTable.setItems(sentRequest);
    }

    public void initSentRequests() {
        List<Request> sentRequestsList = service.showAllSentRequest();
        sentRequest.setAll(sentRequestsList);
    }
}
