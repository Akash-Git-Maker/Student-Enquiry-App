package com.akashit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "STUDENT_ENQ_STATUS")
public class StudentEnqEntity {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enquiryId;

    private String studentName;
    private String studentPhno;
    private String classMode;
    private String courseName;
    private String enqStatus;

    @ManyToOne
    private UserDtlsEntity user;

}
