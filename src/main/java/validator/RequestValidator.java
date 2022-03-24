package validator;

import domain.Request;

public class RequestValidator {
    public void validate(Request r) throws ValidationException{
        String message = "";
        if(r.getFrom().equals(r.getTo())){
            message += "Users cannot send friendship request to themselves";
        }
        if(message.length() > 0){
            throw new ValidationException(message);
        }
    }
}
