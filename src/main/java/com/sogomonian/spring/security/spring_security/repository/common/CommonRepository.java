package com.sogomonian.spring.security.spring_security.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<Entity> extends JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {
}
