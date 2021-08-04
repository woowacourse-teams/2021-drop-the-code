package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Language;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherLanguage;
import com.wootech.dropthecode.repository.bridge.TeacherLanguageRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherLanguageService {

    private final TeacherLanguageRepository teacherLanguageRepository;

    public TeacherLanguageService(TeacherLanguageRepository teacherLanguageRepository) {
        this.teacherLanguageRepository = teacherLanguageRepository;
    }

    public void saveAllWithTeacher(List<Language> languages, TeacherProfile teacher) {
        List<TeacherLanguage> teacherLanguages = languages.stream()
                                                          .map(language -> new TeacherLanguage(teacher, language))
                                                          .collect(Collectors.toList());
        teacherLanguageRepository.saveAll(teacherLanguages);
    }

    @Transactional
    public void deleteAllWithTeacher(TeacherProfile teacher) {
        teacherLanguageRepository.deleteByTeacherProfile(teacher);
    }
}
