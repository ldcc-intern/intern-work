package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class MenuListResponse {
        private Long id;
        private Long order_id;
        private Long parent_id;
        private String title;

        @Builder
        public MenuListResponse(Long id, Long order_id, Long parent_id, String title) {
            this.id = id;
            this.order_id = order_id;
            this.parent_id = parent_id;
            this.title = title;
        }
    }
}
