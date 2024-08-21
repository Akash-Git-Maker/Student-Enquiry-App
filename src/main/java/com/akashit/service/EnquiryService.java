package com.akashit.service;

import java.util.List;

import com.akashit.binding.DashboardResponse;
import com.akashit.binding.EnquiryForm;
import com.akashit.binding.EnquirySearchCriteria;
import com.akashit.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public DashboardResponse getDashboardResponse(Integer userId);
	
	public List <String> getCourses();
	
	public List <String> getEnqStatuses();
	
	public boolean saveEnquiry (EnquiryForm form);
	
	public List <StudentEnqEntity> getAllEnqEnqiries();
	
	public List <StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId);
	
	
	
	

}
