package com.akashit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_COURSES")
@Data
public class CoursesEntity {

	@Id
	@GeneratedValue
	private Integer CoursesId;
	public String courseName;

}
