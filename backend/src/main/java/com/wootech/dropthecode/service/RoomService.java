package com.wootech.dropthecode.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;

import com.wootech.dropthecode.domain.chatting.Room;
import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.dto.response.RoomIdResponse;
import com.wootech.dropthecode.repository.RoomRepository;
import com.wootech.dropthecode.service.chat.RedisSubscriber;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final MemberService memberService;
    private final RoomRepository roomRepository;

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final Map<String, ChannelTopic> topics;

    public RoomService(RedisMessageListenerContainer redisMessageListener, RedisSubscriber redisSubscriber, MemberService memberService, RoomRepository roomRepository) {
        this.redisMessageListener = redisMessageListener;
        this.redisSubscriber = redisSubscriber;
        this.memberService = memberService;
        this.roomRepository = roomRepository;
        topics = new HashMap<>();
    }

    @Transactional
    public RoomIdResponse getOrCreate(RoomRequest roomRequest) {
        Room room = roomRepository.findByTeacherIdAndStudentId(roomRequest.getTeacherId(), roomRequest.getStudentId())
                                  .orElseGet(() ->
                                          new Room(
                                                  memberService.findById(roomRequest.getTeacherId()),
                                                  memberService.findById(roomRequest.getStudentId())
                                          )
                                  );

        Room savedRoom = roomRepository.save(room);

        addRedisMessageListener(savedRoom);

        return new RoomIdResponse(savedRoom.getId());
    }

    private void addRedisMessageListener(Room savedRoom) {
        String roomId = "/rooms/" + savedRoom.getId();

        if (!topics.containsKey(roomId)) {
            ChannelTopic topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    @Transactional(readOnly = true)
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                             .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 방입니다."));
    }

    @Transactional(readOnly = true)
    public List<Room> findAllByMemberId(Long id) {
        return roomRepository.findByTeacherIdOrStudentId(id, id);
    }
}
