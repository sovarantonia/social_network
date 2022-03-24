package validator;

import domain.Friendship;

public class FriendshipValidator {
    public void validate(Friendship f) throws ValidationException{
        String message = "";
        if(f.getUsername1().equals(f.getUsername2())){
            message += "Users cannot befriend themselves";
        }
        if(message.length() > 0){
            throw new ValidationException(message);
        }
    }
}
