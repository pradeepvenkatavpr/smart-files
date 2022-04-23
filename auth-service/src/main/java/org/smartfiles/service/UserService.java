package org.smartfiles.service;

import java.util.Arrays;

import org.smartfiles.model.Role;
import org.smartfiles.model.User;
import org.smartfiles.repo.RolesRepo;
import org.smartfiles.repo.UserRepo;
import org.smartfiles.utils.CKUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RolesRepo roleRepo;

	public void register(User user) {
		try {
			if(userRepo.findByEmail(user.getEmail()) == null) {
				String pass = CKUtils.generatePassword(10);
				user.setPassword(passwordEncoder.encode(pass));
				Role userRole = roleRepo.findByName("ROLE_USER");
				user.setRoles(Arrays.asList(userRole));
				userRepo.save(user);
			} 
			else {
				throw new Error("You have already registered. Please check your mail.");
			}
		} catch (Exception ex) {
			throw new Error("Error in Registering user");
		}
	}

}
