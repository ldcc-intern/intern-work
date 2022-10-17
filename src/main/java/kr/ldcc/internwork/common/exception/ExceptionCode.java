package kr.ldcc.internwork.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    SUCCESS(200, "성공"), DATA_SAVE_EXCEPTION(300, "데이터 저장에 실패하였습니다.");
    private final int code;
    private final String message;
}
