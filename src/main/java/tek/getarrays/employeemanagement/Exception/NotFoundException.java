package tek.getarrays.employeemanagement.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GeneralException{

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
