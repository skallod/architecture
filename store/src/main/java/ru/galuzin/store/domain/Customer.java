package ru.galuzin.store.domain;


import ru.galuzin.store.domain.security.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "customer",
		indexes = {
				@Index(columnList = "email", name = "email_idx")
		})
public class Customer implements Serializable {

	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "customer_id_seq")
	@SequenceGenerator(name="customer_id_seq",sequenceName="customer_id_seq")
	private Long id;

	@NotBlank
	@Size(max = 255)
	private String name;

	@NotBlank
	@Size(max = 255)
	private String password;

	private Role role;

	private String lastName;

	@NotBlank
	@Column(unique = true)
	@Size(max = 255)
	private String email;

	@Column(unique = true)
	@Size(max = 255)
	private String phone;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
