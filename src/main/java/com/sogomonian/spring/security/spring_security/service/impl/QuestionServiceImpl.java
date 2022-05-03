package com.sogomonian.spring.security.spring_security.service.impl;

import com.sogomonian.spring.security.spring_security.repository.QuestionsRepository;
import com.sogomonian.spring.security.spring_security.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionsRepository questionsRepository;

    @Autowired
    public QuestionServiceImpl(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    @Override
    public String findAll() {
        return questionsRepository.findAll().get(0).toString();
    }

}
