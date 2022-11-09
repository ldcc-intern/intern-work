package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler(InternWorkException.dataDuplicateException.class)
    public ResponseEntity<Response<Object>> handleDataDuplicateException() {
        return new ResponseEntity<>(Response.dataDuplicateException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataNotFoundException.class)
    public ResponseEntity<Response<Object>> handleDataNotFoundException() {
        return new ResponseEntity<>(Response.dataNotFoundException(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternWorkException.dataUpdateException.class)
    public ResponseEntity<Response<Object>> handleDataUpdateException() {
        return new ResponseEntity<>(Response.dataUpdateException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataDeleteException.class)
    public ResponseEntity<Response<Object>> handleDataDeleteException() {
        return new ResponseEntity<>(Response.dataDeleteException(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.dataOutOfBoundsException.class)
    public ResponseEntity<Response<Object>> handleDataOutOfBoundsException() {
        return new ResponseEntity<>(Response.dataOutOfBoundsException(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternWorkException.enumNullPointerException.class)
    public ResponseEntity<Response<Object>> handleEnumNullPointerException() {
        return new ResponseEntity<>(Response.enumNullPointerException(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Object>> handleHttpMessageNotReadableException(Exception e) {
        return new ResponseEntity<>(Response.dataValidationException(ExceptionCode.DATA_VALIDATION_EXCEPTION.getMessage() + e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<Object>> handleNullPointerException() {
        return new ResponseEntity<>(Response.nullPointerException(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternWorkException.dataSaveException.class)
    public ResponseEntity<Response<Object>> handleDataSaveException() {
        Response<Object> response = Response.dataSaveException();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.canNotMoveException.class)
    public ResponseEntity<Response<Object>> handleCanNotMoveException() {
        Response<Object> response = Response.canNotMoveException();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternWorkException.referentialException.class)
    public ResponseEntity<Response<Object>> handleReferentialException() {
        Response<Object> response = Response.referentialException();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
