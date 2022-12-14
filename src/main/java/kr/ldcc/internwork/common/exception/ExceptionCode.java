package kr.ldcc.internwork.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    SUCCESS(HttpStatus.OK.value(), "성공"),
    DATA_DUPLICATE_EXCEPTION(HttpStatus.CONFLICT.value(), "중복된 데이터가 있습니다."),
    DATA_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND.value(), "요정하신 데이터가 없습니다."),
    DATA_UPDATE_EXCEPTION(HttpStatus.CONFLICT.value(), "데이터 수정에 실패하였습니다."),
    DATA_DELETE_EXCEPTION(HttpStatus.CONFLICT.value(), "데이터 삭제에 실패하였습니다."),
    DATA_OUT_OF_BOUNDS_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터 범위가 초과하였습니다."),
    ENUM_NULL_POINTER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "입력하신 enum이 null입니다."),
    DATA_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "데이터 유효성 오류가 발생하였습니다."),
    NULL_POINTER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러가 발생하였습니다."),
    DATA_SAVE_EXCEPTION(302, "데이터 저장에 실패하였습니다."),
    REFERENTIAL_EXCEPTION(307, "참조하는 데이터가 있습니다."),
    CAN_NOT_MOVE_EXCEPTION(302, "이동할 수 없습니다.");
    private final int code;
    private final String message;
}
