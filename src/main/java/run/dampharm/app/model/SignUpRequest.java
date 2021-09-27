package run.dampharm.app.model;

import lombok.Data;

@Data
public class SignUpRequest {
	private Long id;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String password;

	private String companyLogo;
	private String companyName;
	private String address;
	private String postalCode;
	private String country;
	private String city;
	private String phone;
	
	private Long productRiskCategory;
	private boolean qr;

}
