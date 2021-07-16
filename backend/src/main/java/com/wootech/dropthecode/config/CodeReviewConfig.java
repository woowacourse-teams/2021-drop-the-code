package com.wootech.dropthecode.config;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.repository.SkillRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeReviewConfig {

    private final SkillRepository skillRepository;

    public CodeReviewConfig(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Bean
    public List<String> defaultSkills() {
        return skillRepository.findAll()
                              .stream()
                              .map(Skill::getName)
                              .collect(Collectors.toList());

    }
}
