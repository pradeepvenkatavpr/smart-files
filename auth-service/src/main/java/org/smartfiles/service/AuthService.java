package org.smartfiles.service;

import org.smartfiles.dto.LoginRequest;
import org.smartfiles.dto.LoginResponse;
import org.smartfiles.handler.TokenProvider;
import org.smartfiles.model.User;
import org.smartfiles.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public LoginResponse login(LoginRequest request) {
		LoginResponse response = null;
		try {
			User user = userRepo.findByEmail(request.getEmail());
			if(user != null) {
				if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
					String token = tokenProvider.generateToken(user);
					response = new LoginResponse(token, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
				}
				else {
					throw new Error("Password Incorrect");
				}
			}
			else {
				throw new Error("User not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
