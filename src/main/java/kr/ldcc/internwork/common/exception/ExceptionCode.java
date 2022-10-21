package kr.ldcc.internwork.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    SUCCESS(200, "성공"),
    DATA_NOT_FOUND_EXCEPTION(300, "요정하신 데이터가 없습니다."),

    DATA_SAVE_EXCEPTION(302, "데이터 저장에 실패하였습니다."),

    DATA_DUPLICATE_EXCEPTION(305, "중복된 데이터가 있습니다.");

    private final int code;
    private final String message;
}
