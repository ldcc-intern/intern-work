package kr.ldcc.internwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Response<T> ok() {
        return new Response<T>()
                .setCode(ExceptionCode.SUCCESS.getCode())
                .setMessage(ExceptionCode.SUCCESS.getMessage());
    }

    public static <T> Response<T> dataDuplicateException2(InternWorkException.dataDuplicateException exception) {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(exception.getMessage());
    }

    public static <T> Response<T> dataNotFoundException(InternWorkException.dataNotFoundException exception) {
        return new Response<T>()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMessage(exception.getMessage());
    }

    public static <T> Response<T> dataUpdateException(InternWorkException.dataUpdateException exception) {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(exception.getMessage());
    }

    public static <T> Response<T> dataDeleteException(InternWorkException.dataDeleteException exception) {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(exception.getMessage());
    }

    public static <T> Response<T> dataDuplicateException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.DATA_DUPLICATE_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.DATA_DUPLICATE_EXCEPTION.getMessage());
        log.info("[dataDuplicateException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }

    public static <T> Response<T> dataSaveException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.DATA_SAVE_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.DATA_SAVE_EXCEPTION.getMessage());
        log.info("[dataSaveException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }

    public static <T> Response<T> canNotMoveException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.CAN_NOT_MOVE_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.CAN_NOT_MOVE_EXCEPTION.getMessage());
        log.info("[canNotMoveException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }

    public static <T> Response<T> referentialException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.REFERENTIAL_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.REFERENTIAL_EXCEPTION.getMessage());
        log.info("[referentialException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }
}
