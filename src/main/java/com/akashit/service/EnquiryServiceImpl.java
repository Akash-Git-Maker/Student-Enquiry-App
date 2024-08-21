package com.akashit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akashit.binding.DashboardResponse;
import com.akashit.binding.EnquiryForm;
import com.akashit.binding.EnquirySearchCriteria;
import com.akashit.entity.CoursesEntity;
import com.akashit.entity.EnqStatusEntity;
import com.akashit.entity.StudentEnqEntity;
import com.akashit.entity.UserDtlsEntity;
import com.akashit.repo.CoursesRepo;
import com.akashit.repo.EnqStatusRepo;
import com.akashit.repo.StudentEnqRepo;
import com.akashit.repo.UserDtlsRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private CoursesRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private HttpSession session;

	@Override
	public List<StudentEnqEntity> getAllEnqEnqiries() {
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}
		return new ArrayList<>();
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			//filter logic
			
			if (null != criteria.getCourseName()&!"".equals(criteria.getCourseName())){
				
				enquiries = enquiries.stream()
						.filter(e->e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}
			if(null!=criteria.getStatus()&&!"".equals(criteria.getStatus())) {
				enquiries = enquiries.stream()
				.filter(e->e.getEnqStatus().equals(criteria.getStatus()))
				.collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode()&&!"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream()
				.filter(e->e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			
			return enquiries;
		}
		return new ArrayList<>();
	}

	@Override
	public List<String> getCourses() {

		List<CoursesEntity> findAll = courseRepo.findAll();
		List<String> names = new ArrayList();

		for (CoursesEntity entity : findAll) {
			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		// TODO Auto-generated method stub

		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList();

		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());

		}

		return statusList;
	}

	@Transactional
	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		// TODO Auto-generated method stub

		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).orElse(null);
		if (userEntity != null) {
			enqEntity.setUser(userEntity);
			enqRepo.save(enqEntity);
			return true;
		}
		return false;
	}

	@Override
	public DashboardResponse getDashboardResponse(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();

			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = (int) enquiries.stream().filter(e -> "Enrolled".equals(e.getEnqStatus())).count();

			Integer lostCnt = (int) enquiries.stream().filter(e -> "Lost".equals(e.getEnqStatus())).count();

			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}

		return response;

	}

}
