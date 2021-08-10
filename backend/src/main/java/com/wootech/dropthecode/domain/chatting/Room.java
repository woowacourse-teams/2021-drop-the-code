package com.wootech.dropthecode.domain.chatting;

import java.util.List;
import javax.persistence.*;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Room extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_teacher"))
    private Member teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_student"))
    private Member student;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @Builder
    public Room(Member teacher, Member student, List<Message> messages) {
        this.teacher = teacher;
        this.student = student;
        this.messages = messages;
    }
}
