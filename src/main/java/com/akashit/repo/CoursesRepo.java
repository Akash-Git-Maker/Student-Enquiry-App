package com.akashit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akashit.entity.CoursesEntity;

public interface CoursesRepo extends JpaRepository<CoursesEntity, Integer>{

}
