package com.wootech.dropthecode.domain.chatting;

import java.util.ArrayList;
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
    @OrderBy("createdAt desc")
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public Room(Member teacher, Member student) {
        this.teacher = teacher;
        this.student = student;
    }

    public Member getPartner(Long id) {
        if (student.hasSameId(id)) {
            return teacher;
        }
        return student;
    }

    public Chat getLatestChat() {
        return chats.get(0);
    }

    public boolean hasMessage() {
        return !chats.isEmpty();
    }
}
