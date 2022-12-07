package kr.ldcc.internwork.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeType {
    OPEN("OPEN"), CLOSE("CLOSE"), RESERVATION("RESERVATION");
    private final String value;
}
