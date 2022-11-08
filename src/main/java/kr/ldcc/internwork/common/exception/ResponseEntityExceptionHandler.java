package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(InternWorkException.dataUpdateException.class)
    ResponseEntity handleDataUpdateException(InternWorkException.dataUpdateException exception) {
        return new ResponseEntity(Response.dataUpdateException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataDeleteException.class)
    ResponseEntity handleDataDeleteException(InternWorkException.dataDeleteException exception) {
        return new ResponseEntity(Response.dataDeleteException(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(InternWorkException.dataNotFoundException.class)
    ResponseEntity handleDataNotFoundException(InternWorkException.dataNotFoundException exception) {
        return new ResponseEntity(Response.dataNotFoundException(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(InternWorkException.dataDuplicateException.class)
    ResponseEntity handleDataDuplicateException(InternWorkException.dataDuplicateException exception) {
        return new ResponseEntity(Response.dataDuplicateException(), HttpStatus.CONFLICT);
    }
}
