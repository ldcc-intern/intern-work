package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler(InternWorkException.dataUpdateException.class)
    ResponseEntity handleDataUpdateException(InternWorkException.dataUpdateException exception) {
        return new ResponseEntity(Response.dataUpdateException(exception), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataDeleteException.class)
    ResponseEntity handleDataDeleteException(InternWorkException.dataDeleteException exception) {
        return new ResponseEntity(Response.dataDeleteException(exception), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataNotFoundException.class)
    ResponseEntity handleDataNotFoundException(InternWorkException.dataNotFoundException exception) {
        return new ResponseEntity(Response.dataNotFoundException(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternWorkException.dataDuplicateException.class)
    ResponseEntity handleDataDuplicateException(InternWorkException.dataDuplicateException exception) {
        return new ResponseEntity(Response.dataDuplicateException2(exception), HttpStatus.CONFLICT);
    }
}
