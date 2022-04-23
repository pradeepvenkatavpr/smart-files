package org.smartfiles.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "ck_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ck_role_id")
	private Long id;

	@Column(name = "ck_role_name")
	private String name;

	@Column(name = "ck_role_desc")
	private String description;

	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;

	@ManyToMany
	@JoinTable(name = "ck_role_privilege", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ck_role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "ck_privilege_id"))
	private Collection<Privilege> privileges;

	private Timestamp created;
	private Timestamp updated;

	public Role() {

	}

	public Role(String name) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
}
