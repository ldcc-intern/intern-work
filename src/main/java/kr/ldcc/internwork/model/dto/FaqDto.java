package kr.ldcc.internwork.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FaqDto {

    private String Content;
    private String updateReason;
    private LocalDateTime noticeDate;
    private FaqType faqType;
    private String title;


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqListResponse {

        private Long FaqId;
        private String categoryName;
        private String title;
        private LocalDateTime registerDate;
        private User registerUserName;
        private FaqType faqType;
        private LocalDateTime noticeDate;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqDetailResponse {

        private String categoryName;
        private String  title;
        private User registerUser;
        private User updateUser;
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private LocalDateTime noticeDate;
        private FaqType faqType;
        private String updateReason;
        private String content;

    }



}
