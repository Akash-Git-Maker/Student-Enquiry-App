package com.akashit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.akashit.binding.DashboardResponse;
import com.akashit.binding.EnquiryForm;
import com.akashit.binding.EnquirySearchCriteria;
import com.akashit.entity.StudentEnqEntity;
import com.akashit.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		// logic to fetch data for dashboard

		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse dashboardData = enqService.getDashboardResponse(userId);
		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// get courses for dropdown
		List<String> courses = enqService.getCourses();

		// get enq status for dropdown
		List<String> enqStatuses = enqService.getEnqStatuses();

		// create bindig class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("courseNames", courses);
		model.addAttribute("enqStatusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		System.out.println(formObj);

		boolean status = enqService.saveEnquiry(formObj);
		if (status) {

			model.addAttribute("sccMsg", "Enquiry Added Successfully");
		} else {
			model.addAttribute("errMsg", "Problem Occured please check");
		}
		return "add-enquiry";
	}

	private void initForm(Model model) {
		// Get courses for drop-down
		List<String> courses = enqService.getCourses();

		// Get enquiry statuses for drop-down
		List<String> enqStatuses = enqService.getEnqStatuses();

		// Create binding class object
		EnquiryForm formObj = new EnquiryForm();

		// Set data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
	}

	@GetMapping("/enquiries")
	public String viewEnquiriesPage(@ModelAttribute("searchForm") EnquirySearchCriteria criteria, Model model) {
		
		initForm (model);
		
		// Retrieve all enquiries from the service
		List<StudentEnqEntity> enquiries = enqService.getAllEnqEnqiries();

		// Add search criteria to model
		model.addAttribute("searchForm", new EnquirySearchCriteria());

		// Add enquiries to model
		model.addAttribute("enquiries", enquiries);

		return "view-enquiries";
	}
	
	
	@GetMapping("/filtered-enquiries")
	public String getFilteredEnqs(@RequestParam String course, 
								@RequestParam  String status, 
								@RequestParam  String mode, Model model  ) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(course);
		criteria.setStatus(status);
		criteria.setClassMode(mode);
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);
		
		model.addAttribute("enquiries", filteredEnqs);
		
		return "filtered-enquiries-page";
	}

}










