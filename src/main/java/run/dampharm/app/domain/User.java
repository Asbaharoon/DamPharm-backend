package run.dampharm.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "first_name")
	private String firstName;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "last_name")
	private String lastName;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "username", unique = true)
	private String username;

	private String email;

	private String companyLogo;
	private String companyName;

	private String address;

	private String postalCode;

	private String country;

	private String city;
	
	private String phone;
	
	private Long productRiskCategory;

	@NotBlank
	@Size(min = 6, max = 100)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}
