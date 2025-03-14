package tek.getarrays.employeemanagement.Exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends GeneralException{

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
