package kr.ldcc.internwork.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class InternWorkException {
    @NoArgsConstructor
    public static class dataUpdateException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataDeleteException extends RuntimeException{

    }

    @NoArgsConstructor
    public static class dataNotFoundException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataDuplicateException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class canNotMoveException extends  RuntimeException{
    }

    @NoArgsConstructor
    public static class referentialIntegrityException extends RuntimeException{
    }

}
