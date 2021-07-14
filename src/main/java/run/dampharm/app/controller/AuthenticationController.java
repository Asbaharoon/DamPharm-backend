package run.dampharm.app.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.model.LoginRequest;
import run.dampharm.app.model.LoginResponse;
import run.dampharm.app.repository.RoleRepository;
import run.dampharm.app.repository.UserRepository;
import run.dampharm.app.secuirty.CurrentUser;
import run.dampharm.app.secuirty.JwtProvider;
import run.dampharm.app.secuirty.UserPrinciple;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);

		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

		return ResponseEntity.ok(new LoginResponse(userPrinciple.getId(), jwt, userPrinciple.getUsername(),
				userPrinciple.getAuthorities()));
	}

	@GetMapping("/me")
	public ResponseEntity getCurrentUser(@CurrentUser UserPrinciple currentUser,
			@RequestHeader(value = "Authorization") String authorization) {
		return ResponseEntity.ok(getCurrentLoggedInUser(currentUser, authorization));
	}

	public LoginResponse getCurrentLoggedInUser(UserPrinciple currentUser, String authorization) {
		LoginResponse res = new LoginResponse(currentUser.getId(), getJwtFromRequest(authorization),
				currentUser.getUsername(), currentUser.getAuthorities());
		log.info("Current logged in user" + currentUser.getId());
		return res;
	}

	private String getJwtFromRequest(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
