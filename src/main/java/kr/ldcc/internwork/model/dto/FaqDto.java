package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Faq;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;

public class FaqDto {
    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqList {
        private final Long no;
        private final String categoryName;
        private final String faqTitle;
        private final String registerDate;
        private final String registerUserName;
        private final FaqType faqType;
        private final String noticeDate;

        public FaqList(Faq faq) {
            this.no = faq.getId();
            this.categoryName = faq.getCategory().getCategoryName();
            this.faqTitle = faq.getFaqTitle();
            this.registerDate = faq.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            this.registerUserName = faq.getRegisterUser().getName();
            this.faqType = faq.getFaqType();
            this.noticeDate = faq.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }

    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqDetail { // faq 상세 조회
        private final String categoryName;
        private final String faqTitle;
        private final String registerUserName;
        private final String updateUserName;
        private final String registerDate;
        private final String updateDate;
        private final String noticeDate;
        private final FaqType faqType;
        private final String updateReason;
        private final String content;

        public FaqDetail(Faq faq) {
            this.categoryName = faq.getCategory().getCategoryName();
            this.faqTitle = faq.getFaqTitle();
            this.registerUserName = faq.getRegisterUser().getName();
            this.updateUserName = faq.getUpdateUser() != null ? faq.getUpdateUser().getName() : null;
            this.registerDate = faq.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm"));
            this.updateDate = faq.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm"));
            this.noticeDate = faq.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm"));
            this.faqType = faq.getFaqType();
            this.updateReason = faq.getUpdateReason() != null ? faq.getUpdateReason() : null;
            this.content = faq.getContent();
        }
    }

    public static Page<FaqList> buildFaqPage(Page<Faq> faqPage) {
        return faqPage.map(FaqList::new);
    }

    public static FaqDetail buildFaqDetail(Faq faq) {
        return new FaqDetail(faq);
    }
}
