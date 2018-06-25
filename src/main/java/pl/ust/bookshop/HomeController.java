package pl.ust.bookshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	
	@Value("${welcome.message}")
	private String welcomeMessage;
	
	@RequestMapping("/test") // http://localhost:8081/bookshop/test
	public String test() {
		return "Test passed. " + welcomeMessage;
		
	}

}
