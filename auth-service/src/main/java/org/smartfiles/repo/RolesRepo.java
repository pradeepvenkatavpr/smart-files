package org.smartfiles.repo;

import org.smartfiles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}
