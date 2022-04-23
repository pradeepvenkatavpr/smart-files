package org.smartfiles.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ck_privilege")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ck_privilege_id")
	private Long id;

	@Column(name = "ck_privilege_name")
	private String name;

	@Column(name = "ck_privilege_desc")
	private String description;

	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;

	public Privilege() {

	}

	public Privilege(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
