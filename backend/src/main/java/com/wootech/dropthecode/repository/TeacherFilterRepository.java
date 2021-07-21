package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.TeacherProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherFilterRepository {

    Page<TeacherProfile> findAll(String languageName, List<String> skills, int career, Pageable pageable);
}
