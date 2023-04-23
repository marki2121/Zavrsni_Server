package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.service.SubjectService;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
}
