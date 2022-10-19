package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.entity.Menu;
import kr.ldcc.internwork.model.entity.User;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuDto {
    private Long id;
    private Integer orderId;
    private Menu parent;
    private MenuType state;
    private String title;
    private User registerUser;
    private User updateUser;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class MenuIdResponse {
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
    public static class MenuDetailResponse {
        private Long id;
        private Integer orderId;
        private LocalDateTime updateDate;
        private LocalDateTime registerDate;
        private Long parentId;
        private MenuType state;
        private String title;
        private String registerUser;
        private String updateUser;
        private String main;
        private String small;
    }
}
