package com.wootech.dropthecode.controller;

import java.util.List;

import com.wootech.dropthecode.dto.response.LanguageSkillsResponse;
import com.wootech.dropthecode.service.LanguageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    /**
     * @title 언어 및 기술 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<LanguageSkillsResponse>> findAll() {
        return ResponseEntity.ok(languageService.findAll());
    }
}
