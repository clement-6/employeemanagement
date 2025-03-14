package tek.getarrays.employeemanagement.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
public abstract class GeneralException extends RuntimeException {
    private final HttpStatus httpStatus;

    protected GeneralException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }



}
