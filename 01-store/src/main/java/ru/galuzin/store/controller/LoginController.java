package ru.galuzin.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.galuzin.store.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		return Collections.singletonMap("token", session!=null?session.getId():null);
	}
	
	@GetMapping("/checkSession")
	public ResponseEntity checkSession() {
		return new ResponseEntity("Session Active!", HttpStatus.OK);
	}

	@GetMapping("/checkAdmin")
	public ResponseEntity checkAdmin() {
		return new ResponseEntity("Session Active!", HttpStatus.OK);
	}

	@PostMapping(value="/user/logout")
	public ResponseEntity logout(HttpSession session){
		SecurityContextHolder.clearContext();
		Optional.ofNullable(session).ifPresent(HttpSession::invalidate);
		return new ResponseEntity("Logout Successfully!", HttpStatus.OK);
	}
}
