package com.akashit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.akashit.binding.LoginForm;
import com.akashit.binding.SignUpForm;
import com.akashit.binding.UnlockForm;
import com.akashit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signup(form);
		if (status) {
			model.addAttribute("succMsg", "Account created, Check you email");

		} else {
			model.addAttribute("errMsg", "Choose unique email");
		}
		return "signup";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);

			if (status) {
				model.addAttribute("succMsg", "Your account unlocked Successfully");
			} else {
				model.addAttribute("errMsg", "Given Temporary Pwd is incorrect, check you email");
			}
		} else {
			model.addAttribute("errMsg", "New Pwd and Confirm pwd should be same");

		}
		return "unlock";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		String status = userService.login(loginForm);

		if (status.contains("Success")) {
			// redirect request to dashboard method
			// return "dashboard";
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";

	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}
	

	@PostMapping("/forgotPwd")
	public String forgotPwdP(@RequestParam ("email") String email, Model model) {
		System.out.println(email);
		
		boolean status = userService.forgotPwd(email);
		
		if (status) {
			model.addAttribute("succMsg", "Password sent to your mail");
		}else {
			model.addAttribute("errMsg", "Invalid Email Id");
		}
		return "forgotPwd";
	}

}
