package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import org.springframework.http.ResponseEntity;

public interface SubjectService {
    ResponseEntity<Object> createSubject(String authorization, SubjectCreateDTO data);

    ResponseEntity<Object> getSubject(String authorization, Long id);
}
