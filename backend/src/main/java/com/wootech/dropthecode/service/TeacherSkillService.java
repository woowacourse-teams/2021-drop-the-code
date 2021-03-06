package com.wootech.dropthecode.service;

import java.util.List;
import java.util.stream.Collectors;

import com.wootech.dropthecode.domain.Skill;
import com.wootech.dropthecode.domain.TeacherProfile;
import com.wootech.dropthecode.domain.bridge.TeacherSkill;
import com.wootech.dropthecode.repository.bridge.TeacherSkillRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherSkillService {

    private final TeacherSkillRepository teacherSkillRepository;

    public TeacherSkillService(TeacherSkillRepository teacherSkillRepository) {
        this.teacherSkillRepository = teacherSkillRepository;
    }

    @Transactional
    public void saveAllWithTeacher(List<Skill> skills, TeacherProfile teacher) {
        List<TeacherSkill> teacherSkills = skills.stream()
                                                 .map(skill -> new TeacherSkill(teacher, skill))
                                                 .collect(Collectors.toList());
        teacherSkillRepository.saveAll(teacherSkills);
    }

    @Transactional
    public void deleteAllWithTeacher(TeacherProfile teacher) {
        teacherSkillRepository.deleteByTeacherProfile(teacher);
    }
}
