package kr.ldcc.internwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class Response<T> {
    private int code;
    private String message;
    private T data;

    public Response(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public static <T> Response<T> ok() {
        return new Response<T>(ExceptionCode.SUCCESS);
    }

    public static <T> Response<T> dataDuplicateException(ExceptionCode exceptionCode) {
        log.info("[dataDuplicateException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataNotFoundException(ExceptionCode exceptionCode) {
        log.info("[dataNotFoundException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataUpdateException(ExceptionCode exceptionCode) {
        log.info("[dataUpdateException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataDeleteException(ExceptionCode exceptionCode) {
        log.info("[dataDeleteException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataOutOfBoundsException(ExceptionCode exceptionCode) {
        log.info("[dataOutOfBoundsException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> enumNullPointerException(ExceptionCode exceptionCode) {
        log.info("[enumNullPointerException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataValidationException(ExceptionCode exceptionCode) {
        log.info("[httpMessageNotReadableException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(ExceptionCode.DATA_VALIDATION_EXCEPTION);
    }

    public static <T> Response<T> nullPointerException(ExceptionCode exceptionCode) {
        log.info("[nullPointerException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<T>(exceptionCode);
    }

    public static <T> Response<T> dataSaveException(ExceptionCode exceptionCode) {
        log.info("[dataSaveException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<>(exceptionCode);
    }

    public static <T> Response<T> canNotMoveException(ExceptionCode exceptionCode) {
        log.info("[canNotMoveException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<>(exceptionCode);
    }

    public static <T> Response<T> referentialException(ExceptionCode exceptionCode) {
        log.info("[referentialException] code : {}, message : {}", exceptionCode.getCode(), exceptionCode.getMessage());
        return new Response<>(exceptionCode);
    }
}
