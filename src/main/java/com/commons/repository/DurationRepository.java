package com.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.commons.entity.Duration;

@Repository
public interface DurationRepository extends JpaRepository<Duration, Long>, JpaSpecificationExecutor<Duration>{

}

