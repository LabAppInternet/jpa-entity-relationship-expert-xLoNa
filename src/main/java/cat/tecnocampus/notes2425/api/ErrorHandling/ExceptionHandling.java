package cat.tecnocampus.notes2425.api.ErrorHandling;

import cat.tecnocampus.notes2425.application.exceptions.UserNotFoundException;
import cat.tecnocampus.notes2425.application.exceptions.UserHasNoEditPermission;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String userNotFoundExceptionHandler(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserHasNoEditPermission.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    String userNotOwnerExceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
