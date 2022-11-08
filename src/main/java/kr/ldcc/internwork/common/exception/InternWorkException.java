package kr.ldcc.internwork.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class InternWorkException {
    public static class dataUpdateException extends RuntimeException {
        public dataUpdateException() {
            super("데이터 수정에 실패하였습니다.");
        }

        public dataUpdateException(String message) {
            super(message + " 데이터 수정에 실패하였습니다.");
        }
    }

    public static class dataDeleteException extends RuntimeException {
        public dataDeleteException() {
            super("데이터 삭제에 실패하였습니다.");
        }

        public dataDeleteException(String message) {
            super(message + " 데이터 삭제에 실패하였습니다.");
        }
    }

    public static class dataNotFoundException extends RuntimeException {
        public dataNotFoundException() {
            super("요청하신 데이터가 없습니다.");
        }

        public dataNotFoundException(String message) {
            super(message + " 요청하신 데이터가 없습니다.");
        }
    }

    public static class dataDuplicateException extends RuntimeException {
        public dataDuplicateException() {
            super("중복된 데이터가 있습니다.");
        }

        public dataDuplicateException(String message) {
            super(message + " 중복된 데이터가 있습니다.");
        }
    }

    @NoArgsConstructor
    public static class canNotMoveException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class referentialIntegrityException extends RuntimeException {
    }

    @NoArgsConstructor
    public static class dataSaveException extends RuntimeException{
    }

}
