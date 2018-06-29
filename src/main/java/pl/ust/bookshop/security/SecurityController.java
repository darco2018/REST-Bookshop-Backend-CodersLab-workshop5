package pl.ust.bookshop.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	@RequestMapping("/")
	public String hello() {
		return "Hello";
	}
	
	@RequestMapping("/notprotected")
	public String notProtected() {
		return "Im not protected";
	}
	
	
	@RequestMapping("/protectedByUserRole")
	public String protectedByUserRole() {
		return "Hello User!. I'm protected By UserRole";
	}
	
	
	@RequestMapping("/protectedByAdminRole")
	public String protectedByAdminRole() {
		return "Hello Admin and User! Im protected By AdminRole";
	}
	
	
	
	

}
