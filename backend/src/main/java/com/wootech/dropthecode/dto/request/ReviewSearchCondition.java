package com.wootech.dropthecode.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.domain.Progress;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSearchCondition {
    /**
     * 리뷰 상태
     */
    private List<Progress> progress = new ArrayList<>();

    /**
     * 선생님 이름 or 학생 이름
     */
    private String name;

    @Builder
    public ReviewSearchCondition(List<Progress> progress, String name) {
        this.progress = progress;
        this.name = name;
    }
}
