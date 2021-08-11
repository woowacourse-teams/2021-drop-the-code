package com.wootech.dropthecode.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomRequest {
    /**
     * 학생 id
     */
    @NotNull
    private Long studentId;
    /**
     * 선생님 id
     */
    @NotNull
    private Long teacherId;

    public RoomRequest(Long studentId, Long teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }
}
