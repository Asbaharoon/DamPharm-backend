package run.dampharm.app.secuirty;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import run.dampharm.app.domain.User;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String username;

	private String email;

	private String companyLogo;
	private String companyName;
	private String address;
	private String postalCode;
	private String country;
	private String city;
	private String phone;
	private boolean qr;
	private Long productRiskCategory;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long id, String name, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String address, String city, String logo,
			String country, String postalCode, String phone, String companyName, Long productRiskCategory, boolean qr) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.address = address;
		this.city = city;
		this.companyLogo = logo;
		this.country = country;
		this.postalCode = postalCode;
		this.phone = phone;
		this.companyName = companyName;
		this.productRiskCategory = productRiskCategory;
		this.qr = qr;
	}

	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrinciple(user.getId(), user.getFirstName(), user.getUsername(), user.getEmail(),
				user.getPassword(), authorities, user.getAddress(), user.getCity(), user.getCompanyLogo(),
				user.getCountry(), user.getPostalCode(), user.getPhone(), user.getCompanyName(),
				user.getProductRiskCategory(), user.isQr());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getProductRiskCategory() {
		return productRiskCategory;
	}

	public void setProductRiskCategory(Long productRiskCategory) {
		this.productRiskCategory = productRiskCategory;
	}

	public boolean isQr() {
		return qr;
	}

	public void setQr(boolean qr) {
		this.qr = qr;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserPrinciple user = (UserPrinciple) o;
		return Objects.equals(id, user.id);
	}
}
