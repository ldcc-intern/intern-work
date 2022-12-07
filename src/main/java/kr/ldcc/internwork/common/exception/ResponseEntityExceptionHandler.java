package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.common.exception.InternWorkException.*;
import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ExceptionHandler(DataDuplicateException.class)
    public ResponseEntity<Response<Object>> handleDataDuplicateException(DataDuplicateException dataDuplicateException) {
        return new ResponseEntity<>(Response.dataDuplicateException(dataDuplicateException.getExceptionCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Response<Object>> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
        return new ResponseEntity<>(Response.dataNotFoundException(dataNotFoundException.getExceptionCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataUpdateException.class)
    public ResponseEntity<Response<Object>> handleDataUpdateException(DataUpdateException dataUpdateException) {
        return new ResponseEntity<>(Response.dataUpdateException(dataUpdateException.getExceptionCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataDeleteException.class)
    public ResponseEntity<Response<Object>> handleDataDeleteException(DataDeleteException dataDeleteException) {
        return new ResponseEntity<>(Response.dataDeleteException(dataDeleteException.getExceptionCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataOutOfBoundsException.class)
    public ResponseEntity<Response<Object>> handleDataOutOfBoundsException(DataOutOfBoundsException dataOutOfBoundsException) {
        return new ResponseEntity<>(Response.dataOutOfBoundsException(dataOutOfBoundsException.getExceptionCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EnumNullPointerException.class)
    public ResponseEntity<Response<Object>> handleEnumNullPointerException(EnumNullPointerException enumNullPointerException) {
        return new ResponseEntity<>(Response.enumNullPointerException(enumNullPointerException.getExceptionCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Object>> handleHttpMessageNotReadableException() {
        return new ResponseEntity<>(Response.dataValidationException(ExceptionCode.DATA_VALIDATION_EXCEPTION), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<Object>> handleNullPointerException() {
        return new ResponseEntity<>(Response.nullPointerException(ExceptionCode.NULL_POINTER_EXCEPTION), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataSaveException.class)
    public ResponseEntity<Response<Object>> handleDataSaveException(DataSaveException dataSaveException) {
        return new ResponseEntity<>(Response.dataSaveException(dataSaveException.getExceptionCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CanNotMoveException.class)
    public ResponseEntity<Response<Object>> handleCanNotMoveException(CanNotMoveException canNotMoveException) {
        return new ResponseEntity<>(Response.canNotMoveException(canNotMoveException.getExceptionCode()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReferentialException.class)
    public ResponseEntity<Response<Object>> handleReferentialException(ReferentialException referentialException) {
        return new ResponseEntity<>(Response.referentialException(referentialException.getExceptionCode()), HttpStatus.CONFLICT);
    }
}
