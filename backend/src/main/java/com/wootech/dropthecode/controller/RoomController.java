package com.wootech.dropthecode.controller;

import javax.validation.Valid;

import com.wootech.dropthecode.dto.request.RoomRequest;
import com.wootech.dropthecode.service.RoomService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public ResponseEntity<Void> createOrGet(@RequestBody @Valid RoomRequest roomRequest) {
        Long id = roomService.getOrCreate(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .header("Location", "/rooms/" + id)
                             .build();
    }
}
