package kr.ldcc.internwork.model.dto.request;

import kr.ldcc.internwork.common.types.MenuType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MenuRequest {
    private String title;
    @NotNull
    private MenuType state;
    private Long userId;
    private Long parentId;
    private Integer orderId;
}
