package service;

import controllers.MessageAlert;
import domain.*;
import javafx.scene.control.Alert;
import repository.Repository;
import validator.FriendshipValidator;
import validator.MessageValidator;
import validator.RequestValidator;
import validator.UserValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Service {
    private final Repository<User, String> repoUser;
    private final Repository<Friendship, Long> repoFriends;
    private final Repository<Request, Long> repoRequest;
    private final Repository<Message, Long> repoMessage;

    private final UserValidator userValidator;
    private final FriendshipValidator friendshipValidator;
    private final RequestValidator requestValidator;
    private final MessageValidator messageValidator;

    private User currentUser;

    public Service(Repository<User, String> repoUser, Repository<Friendship, Long> repoFriends,
                   Repository<Request, Long> repoRequest, Repository<Message, Long> repoMessage,
                   UserValidator userValidator, FriendshipValidator friendshipValidator, RequestValidator requestValidator,
                   MessageValidator messageValidator) {
        this.repoUser = repoUser;
        this.repoFriends = repoFriends;
        this.repoRequest = repoRequest;
        this.repoMessage = repoMessage;
        this.userValidator = userValidator;
        this.friendshipValidator = friendshipValidator;
        this.requestValidator = requestValidator;
        this.messageValidator = messageValidator;
        this.currentUser = null;
    }



    public void addUser(User u) {
        userValidator.validate(u);
        repoUser.add(u);
    }

    public User getUser(String username) {
        return repoUser.getObject(username);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }


    //functii efective

    public List<Friendship> showUsersFriends() {
        Predicate<Friendship> filterByUser = friendship -> friendship.getUsername1().equals(currentUser.getUserName());
        return repoFriends.getAll()
                .stream()
                .filter(filterByUser)
                .collect(Collectors.toList());

    }

    public void addMessage(String username, String text) {
        Message message = new Message(currentUser.getUserName(), username, text, LocalDateTime.now());
        messageValidator.validate(message);
        repoMessage.add(message);
    }


    public List<Message> showMessageHistory(User user) {
        List<Message> exchangedMessages = new ArrayList<>();
        for (Message m : repoMessage.getAll()) {
            if (m.getFrom().equals(currentUser.getUserName()) && m.getTo().equals(user.getUserName())) {
                exchangedMessages.add(m);
            } else if (m.getFrom().equals(user.getUserName()) && m.getTo().equals(currentUser.getUserName())) {
                exchangedMessages.add(m);
            }
        }
        return exchangedMessages;
    }

    public void removeFriendFromFriendList(User u) {
        for (Friendship fr : repoFriends.getAll()) {
            if (fr.getUsername1().equals(u.getUserName()) && fr.getUsername2().equals(currentUser.getUserName())) {
                repoFriends.remove(fr);
            } else {
                if (fr.getUsername1().equals(currentUser.getUserName()) && fr.getUsername2().equals(u.getUserName())) {
                    repoFriends.remove(fr);
                }
            }
        }
    }

    public void sendRequest(User user) {
        boolean ok = true;
        userValidator.validate(user);
        Request request = new Request(currentUser.getUserName(), user.getUserName());

        for (Request r : repoRequest.getAll()) {
            if (r.getTo().equals(user.getUserName()) && r.getFrom().equals(currentUser.getUserName())) {
                ok = false;
                MessageAlert.showErrorMessage(null, "A friendship request was already sent");
            }
        }
        if (ok) {
            for (Friendship fr : repoFriends.getAll()) {
                if (fr.getUsername1().equals(currentUser.getUserName()) && fr.getUsername2().equals(user.getUserName())) {
                    MessageAlert.showErrorMessage(null, "Already friends");
                    ok = false;
                }
            }
        }
        requestValidator.validate(request);
        if (ok) {
            repoRequest.add(request);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Send request", "Request was sent");
        }
    }

    public List<Request> showAllRequests() {
        Predicate<Request> requestsForCurrentUser = request -> request.getTo().equals(currentUser.getUserName());
        return repoRequest.getAll()
                .stream()
                .filter(requestsForCurrentUser)
                .collect(Collectors.toList());
    }

    public List<Request> showAllSentRequest() {
        Predicate<Request> sentRequestsForCurrentUser = request -> request.getFrom().equals(currentUser.getUserName());
        return repoRequest.getAll()
                .stream()
                .filter(sentRequestsForCurrentUser)
                .collect(Collectors.toList());
    }

    public void addAFriend(Request request) {
        Friendship friendship1 = new Friendship(request.getFrom(), request.getTo(), LocalDate.now());
        friendshipValidator.validate(friendship1);
        Friendship friendship2 = new Friendship(request.getTo(), request.getFrom(), LocalDate.now());
        removeRequest(request);
        repoFriends.add(friendship1);
        repoFriends.add(friendship2);
    }


    public void removeRequest(Request request) {
        List<Request> requests = showAllRequests();
        for (Request r : requests) {
            if (r.getId().equals(request.getId())) {
                repoRequest.remove(request);
            }
        }
    }

    public void cancelRequest(Request request) {
        List<Request> requests = showAllSentRequest();
        for(Request r : requests){
            if (r.getId().equals(request.getId())) {
                repoRequest.remove(request);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Cancel request",
                        "Request was cancelled");
            }
        }

    }
}

