package com.sogomonian.spring.security.spring_security.repository;

import com.sogomonian.spring.security.spring_security.model.Questions;
import com.sogomonian.spring.security.spring_security.repository.common.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends CommonRepository<Questions> {
}
