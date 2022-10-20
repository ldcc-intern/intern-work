package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.common.types.validation.Enum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MenuRequest {

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateMenuRequest {
        private String title;
        @Enum(enumClass = MenuType.class)
        private MenuType state;
        private Long userId;
        private Long parentId;
        private Integer orderId;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class updateMenuRequest {
        private String title;
        @Enum(enumClass = MenuType.class)
        private MenuType state;
        private Long userId;
        private Long parentId = 0L;
        private Integer orderId;
    }
}
