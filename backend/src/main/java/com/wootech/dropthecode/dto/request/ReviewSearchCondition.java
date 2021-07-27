package com.wootech.dropthecode.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.wootech.dropthecode.domain.Progress;

public class ReviewSearchCondition {
    /**
     * 리뷰 상태
     */
    private List<Progress> progress = new ArrayList<>();

    /**
     * 선생님 이름
     */
    private String name;

    public ReviewSearchCondition() {
    }

    public ReviewSearchCondition(List<Progress> progress, String name) {
        this.progress = progress;
        this.name = name;
    }

    public List<Progress> getProgress() {
        return progress;
    }

    public void setProgress(List<Progress> progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
