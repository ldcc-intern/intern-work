package kr.ldcc.internwork.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class InternWorkException {
    public static class dataUpdateException extends RuntimeException {
        public dataUpdateException() {
            super(ExceptionCode.DATA_UPDATE_EXCEPTION.getMessage());
        }

        public dataUpdateException(String message) {
            super(message);
        }
    }

    public static class dataDeleteException extends RuntimeException {
        public dataDeleteException() {
            super(ExceptionCode.DATA_DELETE_EXCEPTION.getMessage());
        }

        public dataDeleteException(String message) {
            super(message);
        }
    }

    public static class dataNotFoundException extends RuntimeException {
        public dataNotFoundException() {
            super(ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
        }

        public dataNotFoundException(String message) {
            super(message);
        }
    }

    public static class dataDuplicateException extends RuntimeException {
        public dataDuplicateException() {
            super(ExceptionCode.DATA_DUPLICATE_EXCEPTION.getMessage());
        }

        public dataDuplicateException(String message) {
            super(message);
        }
    }

    @NoArgsConstructor
    public static class canNotMoveException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class referentialIntegrityException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataSaveException extends RuntimeException {
    }
}
