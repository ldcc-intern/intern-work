package kr.ldcc.internwork.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    SUCCESS(HttpStatus.OK.value(), "성공"),
    DATA_SAVE_EXCEPTION(302, "데이터 저장에 실패하였습니다."),
    DATA_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND.value(), "요정하신 데이터가 없습니다."),
    DATA_UPDATE_EXCEPTION(HttpStatus.CONFLICT.value(), "데이터 수정에 실패하였습니다."),
    DATA_DELETE_EXCEPTION(HttpStatus.CONFLICT.value(), "데이터 삭제에 실패하였습니다."),
    DATA_DUPLICATE_EXCEPTION(305, "중복된 데이터가 있습니다.");
    private final int code;
    private final String message;
}
