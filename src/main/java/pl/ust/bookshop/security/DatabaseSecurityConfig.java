package pl.ust.bookshop.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.ust.bookshop.user.Role;

@Configuration
@EnableWebSecurity // enables httpbasic and form authentication, renders login page automatically
// remember to Import this class in AppConfig.java
public class DatabaseSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception{
		
		authenticationMgr
			.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, user_role from user_roles where username=?");
		/* the two lines above necessary when changing default table values
		    RENAME TABLE authorities TO user_roles;
			ALTER TABLE user_roles CHANGE authority user_role varchar(255);*/
	}
	
	
			
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.authorizeRequests()
		.antMatchers("/**/lib/**").hasRole(Role.LIBRARIAN.value())
		.antMatchers("/**/admin/**").hasRole(Role.ADMIN.value())
		
		.antMatchers("/protectedByDeveloperAdminRole*").hasRole(Role.DEVELOPER.value())
		.antMatchers("/protectedByDeveloperAdminRole*").hasRole(Role.ADMIN.value())
		.antMatchers("/protectedByUserRole*").hasRole(Role.USER.value())
		.antMatchers("/**","/notprotected*", "/welcome").permitAll() 
		/*.and()
			.formLogin().loginPage("/login").permitAll() //TODO provide custom login page
		.and()
			.logout().permitAll()*/
		.and()
			.httpBasic();
	}
	// Spring Boot relies on Spring Security’s content-negotiation strategy to determine whether to use httpBasic or formLogin

}
