package validator;

import domain.Message;

public class MessageValidator {
    public void validate(Message m) throws ValidationException{
        String message = "";
        if(m.getTo().equals(m.getFrom())){
            message += "Users cannot send messages to themselves";
        }
        if(m.getMessage().length() == 0){
            message += "Message field cannot be empty";
        }
        if(message.length() > 0){
            throw new ValidationException(message);
        }
    }
}
