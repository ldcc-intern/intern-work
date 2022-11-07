package kr.ldcc.internwork.common.exception;

import kr.ldcc.internwork.model.dto.response.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(InternWorkException.dataUpdateException.class)
    Response handleDataUpdateException(InternWorkException.dataUpdateException exception) {
        return new Response().setCode(3003).setMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InternWorkException.dataDeleteException.class)
    Response handleDataDeleteException(InternWorkException.dataDeleteException exception) {
        return new Response().setCode(3004).setMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InternWorkException.dataNotFoundException.class)
    Response handleDataNotFoundException(InternWorkException.dataNotFoundException exception) {
        return new Response().setCode(3000).setMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InternWorkException.dataDuplicateException.class)
    Response handleDataDuplicateException(InternWorkException.dataDuplicateException exception) {
        return new Response().setCode(3005).setMessage(exception.getMessage());
    }
}
