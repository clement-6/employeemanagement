package tek.getarrays.employeemanagement.Exception;

import org.springframework.http.HttpStatus;

public class ForbidenException extends GeneralException{

    public ForbidenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
