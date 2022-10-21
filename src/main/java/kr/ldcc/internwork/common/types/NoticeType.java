package kr.ldcc.internwork.common.types;

import kr.ldcc.internwork.common.types.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeType implements BaseEnum<String> {
    OPEN("OPEN"), CLOSE("CLOSE"), RESERVATION("RESERVATION");
    private final String value;
}
