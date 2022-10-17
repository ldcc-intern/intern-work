package kr.ldcc.internwork.common.types;

import kr.ldcc.internwork.common.types.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FaqType implements BaseEnum<String> {
    SHOW("SHOW"), CLOSE("CLOSE"), RESERVE("RESERVE");
    private final String value;
}
