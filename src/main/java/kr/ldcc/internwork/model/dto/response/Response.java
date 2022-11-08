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
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.SUCCESS.getCode());
        response.setMessage(ExceptionCode.SUCCESS.getMessage());
        return response;
    }

    public static <T> Response<T> dataUpdateException() {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(new InternWorkException.dataUpdateException().getMessage());
    }

    public static <T> Response<T> dataDeleteException() {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(new InternWorkException.dataDeleteException().getMessage());
    }

    public static <T> Response<T> dataNotFoundException() {
        return new Response<T>()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMessage(new InternWorkException.dataNotFoundException().getMessage());
    }

    public static <T> Response<T> dataDuplicateException() {
        return new Response<T>()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(new InternWorkException.dataDuplicateException().getMessage());
    }
}
