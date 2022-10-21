package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.entity.User;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

public class MenuDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CreateMenuResponse {
        private Long id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class MenuListResponse {
        private Long id;
        private Integer orderId;
        private Long parentId;
        private String title;
        private List<MenuListResponse> children;

        @Builder
        public MenuListResponse(Long id, Integer orderId, Long parentId, String title) {
            this.id = id;
            this.orderId = orderId;
            this.parentId = parentId;
            this.title = title;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetDetailMenuResponse {
        private Long id;
        private String main;
        private String small;
        private String title;
        private MenuType state;
        private String registerUser;
        private String updateUser;
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class UpdateMenuResponse {
        private Long id;
        private Integer orderId;
        private Long parent;
        private MenuType state;
        private String title;
        private User registerUser;
        private User updateUser;
    }
}
