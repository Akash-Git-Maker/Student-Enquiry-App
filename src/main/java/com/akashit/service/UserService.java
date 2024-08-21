package com.akashit.service;

import com.akashit.binding.LoginForm;
import com.akashit.binding.SignUpForm;
import com.akashit.binding.UnlockForm;

public interface UserService {
	
	
	public boolean signup (SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public String login (LoginForm form);

	public boolean forgotPwd (String emial);
}
