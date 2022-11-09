package kr.ldcc.internwork.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class InternWorkException {
    @NoArgsConstructor
    public static class dataDuplicateException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataNotFoundException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataUpdateException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataDeleteException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class canNotMoveException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class referentialException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataSaveException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataOutOfBoundsException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class enumNullPointerException extends RuntimeException {
    }
}
