package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    // category 생성 Response
    public static Response toCreateCategoryResponse() {
        return Response.ok();
    }

    // category 리스트 조회 Response
    public static Response toGetCategoryListResponse(List<Category> categories) {
        return Response.ok();
    }

    // category 상세 조회 Response
    public static Response toGetCategoryDetailResponse(Category category) {
        return Response.ok();
    }

    // category 수정 Response
    public static Response toUpdateCategoryResponse() {
        return Response.ok();
    }

    // category 삭제 Response
    public static Response toDeleteCategoryResponse() {
        return Response.ok();
    }
}
