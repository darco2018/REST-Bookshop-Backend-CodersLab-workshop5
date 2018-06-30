package pl.ust.bookshop.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*@Configuration
@EnableWebSecurity // enables httpbasic and form authentication, renders login page automatically
*/
// remember to Import this class in AppConfig.java
public class ImMemorySecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authMgr) throws Exception {
		
		authMgr.inMemoryAuthentication()
				.withUser("devuser").password("{noop}dev")   // {id}encodedPAssword where {id} is of type bcrypt, sha256, ... noop
				.authorities("ROLE_USER")
				.and()
				.withUser("adminuser").password("{noop}admin")
				.authorities("ROLE_USER", "ROLE_ADMIN");
	}
	
	// authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http
		.authorizeRequests() 
		.antMatchers("/protectedByUserRole*").hasRole("USER")
		.antMatchers("/protectedByAdminRole*").hasRole("ADMIN")
		.antMatchers("/", "/notprotected*").permitAll()
		.and()
		.httpBasic();
		
		/*http
			.authorizeRequests() // 
			//.antMatchers("/").hasRole("USER") // nie wpuszcza tylko na /bookhsop/
			//.antMatchers("/*").hasRole("USER")  //asterisk important niegdzie nie wpuszcza
			.antMatchers("/books*").hasRole("USER")
			//.antMatchers("/bookshop/welcome").hasRole("USER") // wpuszcza!!!
			.antMatchers("/welcome").hasRole("USER") // nie wpuszcza
			.antMatchers("/admin*").hasRole("ADMIN")
			.antMatchers("/").permitAll()
			.and()
			.httpBasic();*/
	}

}
