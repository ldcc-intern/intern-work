package kr.ldcc.internwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

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
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.SUCCESS.getCode());
        response.setMessage(ExceptionCode.SUCCESS.getMessage());
        return response;
    }

    public static <T> Response<T> dataNotFoundException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
        log.info("[dataNotFoundException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }

    public static <T> Response<T> dataDuplicateException() {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.DATA_DUPLICATE_EXCEPTION.getCode());
        response.setMessage(ExceptionCode.DATA_DUPLICATE_EXCEPTION.getMessage());
        log.info("[dataDuplicateException] code : {}, message : {}", response.getCode(), response.getMessage());
        return response;
    }
}
