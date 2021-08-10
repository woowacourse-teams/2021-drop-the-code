package com.wootech.dropthecode.domain.chatting;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.wootech.dropthecode.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Message extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_message_to_room"))
    private Room room;

    @Lob
    @Column(nullable = false)
    private String content;

    @Builder
    public Message(Room room, String content) {
        this.room = room;
        this.content = content;
    }
}
