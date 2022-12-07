package kr.ldcc.internwork.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuType {
    ON("ON"), OFF("OFF");
    private final String value;
}
