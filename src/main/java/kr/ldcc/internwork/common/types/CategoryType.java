package kr.ldcc.internwork.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    ON("ON"), OFF("OFF");
    private final String value;
}
