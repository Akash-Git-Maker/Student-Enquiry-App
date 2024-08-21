package com.akashit.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akashit.binding.LoginForm;
import com.akashit.binding.SignUpForm;
import com.akashit.binding.UnlockForm;
import com.akashit.entity.UserDtlsEntity;
import com.akashit.repo.UserDtlsRepo;
import com.akashit.utils.EmailUtils;
import com.akashit.utils.PwdUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;

	@Override
	public boolean forgotPwd(String email) {

		UserDtlsEntity entity = userRepo.findByEmail(email);
		if (entity == null) {
			return false;
		}
		String subject = "Recover Password";
		String body = "Your Password : " + entity.getPwd();

		emailUtils.sendEmail(email, subject, body);
		return true;
	}

	@Override
	public String login(LoginForm form) {

		UserDtlsEntity entity = userRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account is Locked";
		}
		session.setAttribute("userId", entity.getUserId());

		return "Success";
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		UserDtlsEntity entity = userRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {

			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userRepo.save(entity);
			return true;

		} else {
			return false;
		}

	}

	@Override
	public boolean signup(SignUpForm form) {

		UserDtlsEntity user = userRepo.findByEmail(form.getEmail());
		if (user != null) {
			return false;
		}

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		entity.setAccStatus("LOCKED");

		userRepo.save(entity);

		String to = form.getEmail();
		String subject = "Unlock Your Account | Akash-IT";
		StringBuffer body = new StringBuffer();
		body.append("Use the below temporary password to unlock your account:<br/>");
		body.append("Temporary password : " + tempPwd + "<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");

		emailUtils.sendEmail(to, subject, body.toString());
		return true;
	}

}
