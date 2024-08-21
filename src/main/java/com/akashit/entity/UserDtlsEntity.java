package com.akashit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name= ("USER_DTLS"))
public class UserDtlsEntity {
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String name;
    @Column(unique = true)
    private String email;
    private String pwd;
    private String phno;

    private String accStatus;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentEnqEntity> enquiries;
}
