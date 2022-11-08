package kr.ldcc.internwork.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class InternWorkException {
    @NoArgsConstructor
    public static class dataDuplicateException extends RuntimeException {
        public dataDuplicateException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class dataNotFoundException extends RuntimeException {
        public dataNotFoundException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class dataUpdateException extends RuntimeException {
        public dataUpdateException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class dataDeleteException extends RuntimeException {
        public dataDeleteException(String message) {
            super(message);
        }
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
}
