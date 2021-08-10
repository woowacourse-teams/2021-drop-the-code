package com.wootech.dropthecode.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class TeacherPaginationResponse {
    /**
     * 리뷰어 목록
     */
    private final List<TeacherProfileResponse> teacherProfiles;

    /**
     * 총 페이지 수
     */
    private final Integer pageCount;

    public TeacherPaginationResponse(List<TeacherProfileResponse> teacherProfiles, Integer pageCount) {
        this.teacherProfiles = teacherProfiles;
        this.pageCount = pageCount;
    }
}
