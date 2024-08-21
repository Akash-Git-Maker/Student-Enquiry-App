package com.akashit.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.akashit.entity.CoursesEntity;
import com.akashit.entity.EnqStatusEntity;
import com.akashit.repo.CoursesRepo;
import com.akashit.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private CoursesRepo coursesRepo;
    
    @Autowired
    private EnqStatusRepo enqStatusRepo; 
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Clear existing data
        coursesRepo.deleteAll();
        enqStatusRepo.deleteAll();

        // Create and save CourseEntity instances
        CoursesEntity c1 = new CoursesEntity();
        c1.setCourseName("Java");

        CoursesEntity c2 = new CoursesEntity();
        c2.setCourseName("Python");

        CoursesEntity c3 = new CoursesEntity();
        c3.setCourseName("AWS");
        
       List<CoursesEntity> list = Arrays.asList(c1, c2, c3);
       coursesRepo.saveAll(list);

        // Create and save EnqStatusEntity instances
        EnqStatusEntity s1 = new EnqStatusEntity();
        s1.setStatusName("New");

        EnqStatusEntity s2 = new EnqStatusEntity();
        s2.setStatusName("Enrolled");	

        EnqStatusEntity s3 = new EnqStatusEntity();
        s3.setStatusName("Lost");
        
        List<EnqStatusEntity> list1 = Arrays.asList(s1, s2, s3);
        enqStatusRepo.saveAll(list1);
    }
}
