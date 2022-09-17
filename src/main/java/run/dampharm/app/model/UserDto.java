package run.dampharm.app.model;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import run.dampharm.app.secuirty.UserPrinciple;

@Data
public class UserDto {
	private Long id;
	private String accessToken;
	private String username;
	private String companyLogo;
	private String companyName;
	private String address;
	private String postalCode;
	private String country;
	private String city;
	private String phone;
	private String email;
	private Long productRiskCategory;
	private boolean qr;
	private String commercialRecord;
	private String taxCard;
	private String taxBillLogo;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDto(Long id, String accessToken, String username,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.accessToken = accessToken;
		this.username = username;
		this.authorities = authorities;
	}

	public UserDto(UserPrinciple principle, String accessToken) {
		this.accessToken = accessToken;
		BeanUtils.copyProperties(principle, this);

	}
}
