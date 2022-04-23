package org.smartfiles.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.smartfiles.model.Privilege;
import org.smartfiles.model.Role;
import org.smartfiles.model.User;
import org.smartfiles.repo.PrivilegeRepo;
import org.smartfiles.repo.RolesRepo;
import org.smartfiles.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RolesRepo roleRepository;

	@Autowired
	private PrivilegeRepo privilegeRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("onApplicationEvent triggered");
		if (alreadySetup)
			return;
		Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
		Privilege guestPrivilege = createPrivilegeIfNotFound("GUEST_PRIVILEGE");

		List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
		createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
		createRoleIfNotFound("ROLE_GUEST", Arrays.asList(guestPrivilege));

		if (userRepository.findByEmail("sai.charan@winvest-global.com") != null) {
			Role adminRole = roleRepository.findByName("ROLE_ADMIN");
			User user = new User();
			user.setFirstName("Sai Charan");
			user.setLastName("Krishnagiri");
			user.setPassword(passwordEncoder.encode("Passw0rd1"));
			user.setEmail("sai.charan@winvest-global.com");
			user.setRoles(Arrays.asList(adminRole));
			user.setEnabled(true);
			userRepository.save(user);
		}
		if (userRepository.findByEmail("test@winvest-global.com") != null) {
			Role guestRole = roleRepository.findByName("ROLE_GUEST");
			User user = new User();
			user.setFirstName("Test");
			user.setLastName("Winvest");
			user.setPassword(passwordEncoder.encode("Passw0rd1"));
			user.setEmail("test@winvest-global.com");
			user.setRoles(Arrays.asList(guestRole));
			user.setEnabled(true);
			userRepository.save(user);
		}
		alreadySetup = true;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}
}