package repository.memory;

import domain.User;
import repository.Repository;
import validator.UserValidator;

import java.util.ArrayList;

public class MemoryRepoUser implements Repository<User, String> {

    private final UserValidator validator;
    private final ArrayList<User> users;

    public MemoryRepoUser(UserValidator validator) {
        this.validator = validator;
        this.users = new ArrayList<>();
    }

    @Override
    public void add(User e) {
        if(e == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        validator.validate(e);
        users.add(e);
    }

    @Override
    public void remove(User e) {
        if(e == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        validator.validate(e);
        int index = users.indexOf(e);
        if(index == -1){
            throw new IllegalArgumentException("User does not exist");
        }
        users.remove(index);
    }

    @Override
    public User getObject(String name) {
        for(User u : users){
            if(u.getUserName().equals(name)){
                return u;
            }
        }
        return null;
    }


    @Override
    public ArrayList<User> getAll() {
        return users;
    }
}
