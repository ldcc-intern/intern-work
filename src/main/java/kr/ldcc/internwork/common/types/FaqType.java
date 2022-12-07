package kr.ldcc.internwork.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FaqType {
    SHOW("SHOW"), CLOSE("CLOSE"), RESERVE("RESERVE");
    private final String value;
}
