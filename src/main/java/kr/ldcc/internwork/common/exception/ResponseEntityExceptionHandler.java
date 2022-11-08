package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler(InternWorkException.dataDuplicateException.class)
    public ResponseEntity handleDataDuplicateException() {
        return new ResponseEntity(Response.dataDuplicateException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataNotFoundException.class)
    public ResponseEntity handleDataNotFoundException() {
        return new ResponseEntity(Response.dataNotFoundException(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternWorkException.dataUpdateException.class)
    public ResponseEntity handleDataUpdateException() {
        return new ResponseEntity(Response.dataUpdateException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataDeleteException.class)
    public ResponseEntity handleDataDeleteException() {
        return new ResponseEntity(Response.dataDeleteException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataSaveException.class)
    public final ResponseEntity handleDataSaveException() {
        Response response = Response.dataSaveException();
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.canNotMoveException.class)
    public final ResponseEntity handleCanNotMoveException() {
        Response response = Response.canNotMoveException();
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.referentialException.class)
    public final ResponseEntity handleReferentialException() {
        Response response = Response.referentialException();
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }
}
