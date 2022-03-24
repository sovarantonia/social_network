package controllers;

import com.example.begin1.HelloApplication;
import domain.Friendship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;
import validator.ValidationException;


import java.io.IOException;
import java.time.LocalDate;

import java.util.List;


public class MainPageController {
    @FXML
    TableView<Friendship> userFriendList;
    @FXML
    TableColumn<Friendship, String> usernameColumn;
    @FXML
    TableColumn<Friendship, LocalDate> dateColumn;
    @FXML
    Button logoutButton;
    @FXML
    TextField searchBarField;
    @FXML
    Label welcomeLabel;

    private Service userService;
    private Stage dialogStage;
    private final ObservableList<Friendship> allUsersFriends = FXCollections.observableArrayList();


    public void setService(Service userService, Stage dialogStage) {
        this.userService = userService;
        this.dialogStage = dialogStage;
        initialise();
    }

    public void initialise() {
        welcomeLabel.setText("Welcome " + userService.getCurrentUser() + "!");
        initFriendList();
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Friendship, String>("username2"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Friendship, LocalDate>("friendshipDate"));
    }

    public void initFriendList() {
        List<Friendship> friendships = userService.showUsersFriends();
        userFriendList.setItems(allUsersFriends);
        allUsersFriends.setAll(friendships);
        FilteredList<Friendship> filteredList = new FilteredList<>(allUsersFriends, x -> true);
        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(friendship -> newValue.isEmpty() || newValue.isBlank()
                    || friendship.getUsername2().startsWith(newValue));

        });
        SortedList<Friendship> friendshipSortedList = new SortedList<>(filteredList);
        friendshipSortedList.comparatorProperty().bind(userFriendList.comparatorProperty());
        userFriendList.setItems(friendshipSortedList);
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        dialogStage = (Stage) logoutButton.getScene().getWindow();
        dialogStage.close();
    }

    public void handleSendAMessage(ActionEvent actionEvent) throws IOException {
        Friendship friendship = userFriendList.getSelectionModel().getSelectedItem();
        if (friendship == null) {
            MessageAlert.showErrorMessage(null, "Select a friend");
        } else {
            try {
                String username = friendship.getUsername2();
                showSendMessageDialog(username);
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }

    }

    public void handleFriendshipRequests(ActionEvent actionEvent) throws IOException {
        showFriendshipRequestDialog();
        initFriendList();
    }

    public void handleConversationHistory(ActionEvent actionEvent) throws IOException {
        Friendship friendship = userFriendList.getSelectionModel().getSelectedItem();
        if (friendship == null) {
            MessageAlert.showErrorMessage(null, "Select a friend");
        } else {
            showMessageHistoryDialog(friendship.getUsername2());
        }

    }

    public void handleRemoveFriend(ActionEvent actionEvent) {
        Friendship friendship = userFriendList.getSelectionModel().getSelectedItem();
        if (friendship == null) {
            MessageAlert.showErrorMessage(null, "Select a user");
        } else {
            userService.removeFriendFromFriendList(userService.getUser(friendship.getUsername2()));
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Remove a friend",
                    "User was removed");
            initFriendList();
        }

    }

    public void handleSendFriendshipRequest(ActionEvent actionEvent) throws IOException {
        showSendFriendshipRequest();
    }

    public void handleRefreshButton(ActionEvent actionEvent){
        initFriendList();
    }


    public void showSendMessageDialog(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("send-message.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage sendMessageDialog = new Stage();
        sendMessageDialog.setTitle("Send a message to " + username);

        Scene scene = new Scene(root);

        sendMessageDialog.setScene(scene);
        SendMessageController sendMessageController = fxmlLoader.getController();

        sendMessageController.setService(userService, sendMessageDialog, username);

        sendMessageDialog.show();
    }


    public void showMessageHistoryDialog(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("message-history.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage messageHistoryDialog = new Stage();
        messageHistoryDialog.setTitle(username);

        Scene scene = new Scene(root);
        messageHistoryDialog.setScene(scene);

        MessageHistoryController messageHistoryController = fxmlLoader.getController();
        messageHistoryController.setService(userService, messageHistoryDialog, userService.getUser(username));

        messageHistoryDialog.show();

    }

    public void showFriendshipRequestDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("requests.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage friendshipRequestsDialog = new Stage();

        Scene scene = new Scene(root);
        friendshipRequestsDialog.setScene(scene);
        friendshipRequestsDialog.setTitle("Friendship requests");

        FriendshipRequestController friendshipRequestController = fxmlLoader.getController();
        friendshipRequestController.setService(userService, friendshipRequestsDialog);

        friendshipRequestsDialog.show();
    }

    public void showSendFriendshipRequest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("send-request.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage sendFriendshipRequestsDialog = new Stage();
        sendFriendshipRequestsDialog.setTitle("Send a friend request");
        Scene scene = new Scene(root);

        sendFriendshipRequestsDialog.setScene(scene);

        SendRequestController sendRequestController = fxmlLoader.getController();
        sendRequestController.setService(userService, sendFriendshipRequestsDialog);

        sendFriendshipRequestsDialog.show();
    }

}
