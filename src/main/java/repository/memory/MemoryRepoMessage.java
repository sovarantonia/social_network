package repository.memory;

import domain.Message;
import repository.Repository;
import validator.MessageValidator;

import java.util.ArrayList;

public class MemoryRepoMessage implements Repository<Message, Long> {
    private final ArrayList<Message> messages;
    private final MessageValidator messageValidator;

    public MemoryRepoMessage(MessageValidator messageValidator){
        this.messageValidator = messageValidator;
        this.messages = new ArrayList<>();
    }

    @Override
    public void add(Message e) {
        if(e == null){
            throw new IllegalArgumentException("Message cannot be null");
        }
        messageValidator.validate(e);
        messages.add(e);
    }

    @Override
    public void remove(Message e) {
        if(e == null){
            throw new IllegalArgumentException("Message cannot be null");
        }
        messageValidator.validate(e);
        int index = messages.indexOf(e);
        if(index == -1){
            throw new IllegalArgumentException("Message does not exist");
        }
        messages.remove(index);
    }

    @Override
    public Message getObject(Long field) {

        for(Message m : messages){
            if(m.getId().equals(field)){
                return m;
            }
        }
        return null;
    }


    @Override
    public ArrayList<Message> getAll() {
        return messages;
    }
}
