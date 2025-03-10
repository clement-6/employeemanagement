package tek.getarrays.employeemanagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequest(ApiRequestException e){
        //créer une charge utile contenant les détails de l'exception
           ApiException apiException = new ApiException(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    ZonedDateTime.now(ZoneId.of("Z"))
            );

            //Retourner l'entité de réponse
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }


}
