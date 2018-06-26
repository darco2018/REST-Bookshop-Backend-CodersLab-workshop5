package pl.ust.bookshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	
	@Value("${welcome.message}") 
	private String welcomeMessage;
	
	@RequestMapping("/welcome") // http://localhost:8080/bookshop/welcome
	public String test() {
		return "Test passed. "+ welcomeMessage;
		
	} 

}
