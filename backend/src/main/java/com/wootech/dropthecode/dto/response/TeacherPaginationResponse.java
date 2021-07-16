package com.wootech.dropthecode.dto.response;

import java.util.List;

public class TeacherPaginationResponse {
    private final List<TeacherProfileResponse> teacherProfiles;

    private final Integer pageCount;

    public TeacherPaginationResponse(List<TeacherProfileResponse> teacherProfiles, Integer pageCount) {
        this.teacherProfiles = teacherProfiles;
        this.pageCount = pageCount;
    }

    public List<TeacherProfileResponse> getTeacherProfiles() {
        return teacherProfiles;
    }

    public Integer getPageCount() {
        return pageCount;
    }
}
