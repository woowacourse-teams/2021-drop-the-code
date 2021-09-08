package com.wootech.dropthecode.domain.chatting;

import javax.persistence.*;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Chat extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_chat_to_room"))
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_chat_to_sender"))
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_receiver"))
    private Member receiver;

    @Lob
    @Column(nullable = false)
    private String content;

    @Builder
    public Chat(Room room, Member sender, Member receiver, String content) {
        this.room = room;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}
