package com.akashit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akashit.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {
	
}
