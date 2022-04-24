package org.smartfiles.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ck_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ck_user_id")
	private Long id;

	@Column(name = "ck_user_title")
	private String title;

	@Column(name = "ck_user_first_name")
	private String firstName;

	@Column(name = "ck_user_last_name")
	private String lastName;

	@Column(name = "ck_user_email")
	private String email;

	@Column(name = "ck_user_phone")
	private String phone;

	@Column(name = "ck_user_password")
	private String password;

	@Column(name = "ck_user_enabled")
	private boolean enabled;

	private Timestamp created;
	private Timestamp updated;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ck_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ck_user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ck_role_id"))
	private Collection<Role> roles;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
