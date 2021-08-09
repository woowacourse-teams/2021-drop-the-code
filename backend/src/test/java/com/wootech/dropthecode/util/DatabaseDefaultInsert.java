package com.wootech.dropthecode.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.bridge.LanguageSkill;
import com.wootech.dropthecode.repository.LanguageRepository;
import com.wootech.dropthecode.repository.SkillRepository;
import com.wootech.dropthecode.repository.bridge.LanguageSkillRepository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Service
@ActiveProfiles("test")
public class DatabaseDefaultInsert {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private LanguageSkillRepository languageSkillRepository;

    @Transactional
    public void insertDefaultLanguageAndSkill() {
        Map<Long, Language> languageMap = insertLanguage()
                .stream()
                .collect(Collectors.toMap(Language::getId, Function.identity()));

        Map<Long, Skill> skillMap = insertSkill()
                .stream()
                .collect(Collectors.toMap(Skill::getId, Function.identity()));

        insertLanguageSkill(languageMap, skillMap);
    }

    private List<Language> insertLanguage() {
        List<Language> languages = Arrays.asList(
                Language.builder().name("java").build(),
                Language.builder().name("javascript").build(),
                Language.builder().name("python").build(),
                Language.builder().name("kotlin").build(),
                Language.builder().name("c").build()
        );
        return languageRepository.saveAll(languages);
    }

    private List<Skill> insertSkill() {
        List<Skill> skills = Arrays.asList(
                Skill.builder().name("spring").build(),
                Skill.builder().name("vue").build(),
                Skill.builder().name("react").build(),
                Skill.builder().name("angular").build(),
                Skill.builder().name("django").build()
        );
        return skillRepository.saveAll(skills);
    }

    private void insertLanguageSkill(Map<Long, Language> languageMap, Map<Long, Skill> skillMap) {
        List<LanguageSkill> languageSkills = Arrays.asList(
                new LanguageSkill(languageMap.get(1L), skillMap.get(1L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(2L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(3L)),
                new LanguageSkill(languageMap.get(2L), skillMap.get(4L)),
                new LanguageSkill(languageMap.get(3L), skillMap.get(5L)),
                new LanguageSkill(languageMap.get(4L), skillMap.get(1L))
        );
        languageSkillRepository.saveAll(languageSkills);
    }
}
