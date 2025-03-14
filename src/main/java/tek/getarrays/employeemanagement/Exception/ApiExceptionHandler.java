package tek.getarrays.employeemanagement.Exception;

import io.swagger.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {GeneralException.class})
    public ResponseEntity<ApiExceptions> handleCustomException(GeneralException e) {
        HttpStatus status = e.getHttpStatus();
        //créer une charge utile contenant les détails de l'exception
        ApiExceptions apiExceptions = new ApiExceptions(
                e.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        //Retourner l'entité de réponse
        return new ResponseEntity<>(apiExceptions, status);

    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiExceptions> handleGenericException(Exception e){
//
//        ApiExceptions apiExceptions = new ApiExceptions(
//                "",
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                ZonedDateTime.now(ZoneId.of("Z"))
//        );
//        return new ResponseEntity<>(apiExceptions,HttpStatus.INTERNAL_SERVER_ERROR);
//    }


//    @ExceptionHandler(value = {ApiUnAuthorizedException.class})
//    public ResponseEntity<Object> handleApiUnAuthorized(ApiRequestException e) {
//        //créer une charge utile contenant les détails de l'exception
//        ApiException apiException = new ApiException(
//                e.getMessage(),
//                HttpStatus.UNAUTHORIZED,
//                ZonedDateTime.now(ZoneId.of("Z"))
//        );
//
//        //Retourner l'entité de réponse
//        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
//
//    }

}
