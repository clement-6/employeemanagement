package tek.getarrays.employeemanagement.Exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GeneralException{

    public UserNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }

}
