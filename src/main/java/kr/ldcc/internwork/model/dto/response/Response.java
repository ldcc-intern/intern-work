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
}
