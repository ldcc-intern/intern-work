package kr.ldcc.internwork.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    SUCCESS(200, "성공");
    private final int code;
    private final String message;
}
