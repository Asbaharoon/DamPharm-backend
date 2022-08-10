package run.dampharm.app.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestPart;

import io.swagger.annotations.ApiOperation;
import run.dampharm.app.domain.Role;
import run.dampharm.app.domain.RoleName;
import run.dampharm.app.domain.User;
import run.dampharm.app.files.AttachmentDTO;
import run.dampharm.app.files.AttachmentService;
import run.dampharm.app.model.SignUpRequest;
import run.dampharm.app.model.UserDto;
import run.dampharm.app.model.WebApiGenericResponse;
import run.dampharm.app.repository.RoleRepository;
import run.dampharm.app.repository.UserRepository;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.JwtProvider;
import run.dampharm.app.secuirty.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	ModelMapper modelMapper;

	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
		modelMapper = new ModelMapper();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {

			return new ResponseEntity<>(
					new WebApiGenericResponse(Boolean.FALSE, "Fail -> Username is already taken!", null),
					HttpStatus.BAD_REQUEST);
		}
		Boolean exists = userRepository.existsByUsername(signUpRequest.getUsername());
		if (exists) {
			return new ResponseEntity<>(
					new WebApiGenericResponse(Boolean.FALSE, "Fail -> Email is already in use!", null),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
//		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
//				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(adminRole);
//		strRoles.forEach(role -> {
//			switch (role) {
//			case "admin":
//				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(adminRole);
//
//				break;
//			case "pm":
//				Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(pmRole);
//
//				break;
//			default:
//				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(userRole);
//			}
//		});
		User user = modelMapper.map(signUpRequest, User.class);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity<>(new WebApiGenericResponse(Boolean.TRUE, "User registered successfully!", null),
				HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@CurrentUser UserPrinciple currentUser, @RequestBody SignUpRequest rq) {
		modelMapper = new ModelMapper();
//		if (userRepository.existsByUsername(rq.getUsername())) {
//
//			return new ResponseEntity<>(
//					new WebApiGenericResponse(Boolean.FALSE, "Fail -> Username is already taken!", null),
//					HttpStatus.BAD_REQUEST);
//		}
//		

		Optional<User> userOpt = userRepository.findById(currentUser.getId());
		User user = null;
		if (userOpt.isPresent()) {
			user = userOpt.get();

			Boolean exists = userRepository.existsByEmail(rq.getEmail());
			if (exists && !user.getEmail().equals(rq.getEmail())) {
				return new ResponseEntity<>(
						new WebApiGenericResponse(Boolean.FALSE, "Fail -> Email is already in use!", null),
						HttpStatus.BAD_REQUEST);
			}

			user.setCompanyName(rq.getCompanyName());
			user.setAddress(rq.getAddress());
			user.setCity(rq.getCity());
			user.setCountry(rq.getCountry());
			user.setPostalCode(rq.getPostalCode());
			user.setPhone(rq.getPhone());
			user.setEmail(rq.getEmail());
			user.setProductRiskCategory(rq.getProductRiskCategory());
			user.setQr(rq.isQr());
			user=userRepository.save(user);
		}

		UserPrinciple princable = UserPrinciple.build(user);
		String jwt = jwtProvider.generateJwtToken(princable);
		return ResponseEntity.ok(new UserDto(princable, jwt));
	}

// 	@PostMapping(value="/logo/update",consumes = "multipart/form-data",produces = "application/json")
	@RequestMapping(value = "/logo/update",headers=("content-type=multipart/*"), method = RequestMethod.POST)
        @ResponseBody
	@ApiOperation("Change logo v4")
	public ResponseEntity<UserDto> uploadAttachment(@CurrentUser UserPrinciple currentUser,
			@RequestPart(name="file") MultipartFile file) {
		AttachmentDTO attachment = attachmentService.convertToDto(attachmentService.upload(file));
		Optional<User> userOpt = userRepository.findById(currentUser.getId());
		User user = null;
		if (userOpt.isPresent()) {
			user = userOpt.get();
			user.setCompanyLogo(attachment.getPath());
			user=userRepository.save(user);
		}

		UserPrinciple princable = UserPrinciple.build(user);
		String jwt = jwtProvider.generateJwtToken(princable);
		return ResponseEntity.ok(new UserDto(princable, jwt));
	}
}
