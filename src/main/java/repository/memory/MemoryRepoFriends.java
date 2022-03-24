package repository.memory;


import domain.Friendship;
import repository.Repository;
import validator.FriendshipValidator;

import java.util.ArrayList;

public class MemoryRepoFriends implements Repository<Friendship, Long> {
    private final ArrayList<Friendship> friendships;
    private final FriendshipValidator friendshipValidator;

    public MemoryRepoFriends(FriendshipValidator friendshipValidator) {
        this.friendships = new ArrayList<>();
        this.friendshipValidator = friendshipValidator;
    }

    @Override
    public void add(Friendship e) {
        if(e == null){
            throw new IllegalArgumentException("Friendship cannot be null");
        }
        friendshipValidator.validate(e);
        friendships.add(e);
    }

    @Override
    public void remove(Friendship e) {
        if(e == null){
            throw new IllegalArgumentException("Friendship cannot be null");
        }
        friendshipValidator.validate(e);
        int index = friendships.indexOf(e);
        if(index == -1){
            throw new IllegalArgumentException("Friendship does not exist");
        }
        friendships.remove(index);
    }

    @Override
    public Friendship getObject(Long id) {
        for(Friendship fr : friendships){
            if(fr.getId().equals(id)){
                return fr;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Friendship> getAll() {
        return friendships;
    }

}
