package org.smartfiles.repo;

import org.smartfiles.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepo extends JpaRepository<Privilege, Integer> {

	Privilege findByName(String name);
}
