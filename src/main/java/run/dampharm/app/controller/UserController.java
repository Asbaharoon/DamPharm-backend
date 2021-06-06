package run.dampharm.app.controller;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import run.dampharm.app.domain.Role;
import run.dampharm.app.domain.RoleName;
import run.dampharm.app.domain.User;
import run.dampharm.app.model.UserDto;
import run.dampharm.app.model.WebApiGenericResponse;
import run.dampharm.app.repository.RoleRepository;
import run.dampharm.app.repository.UserRepository;
import run.dampharm.app.secuirty.JwtProvider;


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

    ModelMapper  modelMapper;


	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody UserDto signUpRequest) {
		modelMapper=new ModelMapper();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			
			return new ResponseEntity<>(new WebApiGenericResponse(Boolean.FALSE,"Fail -> Username is already taken!",null),
					HttpStatus.BAD_REQUEST);
		}
		Boolean exists=userRepository.existsByUsername(signUpRequest.getUsername());
		if (exists) {
			return new ResponseEntity<>(new WebApiGenericResponse(Boolean.FALSE,"Fail -> Email is already in use!",null),
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
		User user=modelMapper.map(signUpRequest,User.class);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity<>(new WebApiGenericResponse(Boolean.TRUE,"User registered successfully!",null), HttpStatus.OK);
	}
}
