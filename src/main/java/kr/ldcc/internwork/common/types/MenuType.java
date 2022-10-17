package kr.ldcc.internwork.common.types;

import kr.ldcc.internwork.common.types.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuType implements BaseEnum<String> {
    ON("ON"), OFF("OFF");
    private final String value;
}
