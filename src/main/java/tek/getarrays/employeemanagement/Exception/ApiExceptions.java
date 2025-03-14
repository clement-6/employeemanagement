package tek.getarrays.employeemanagement.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApiExceptions {

    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timeStamp;
}
