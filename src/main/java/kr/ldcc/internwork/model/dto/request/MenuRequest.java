package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.common.types.validation.Enum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuRequest {
    private String title;
    @Enum(enumClass = MenuType.class)
    private MenuType state;
    private Long userId;
    private Long parentId;
    private Integer orderId;
}
