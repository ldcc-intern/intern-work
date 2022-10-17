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
        private Long order_id;
        private Long parent_id;
        @Enum(enumClass = MenuType.class)
        private MenuType state;
        private String title;
        private Long user_id;
    }
}
