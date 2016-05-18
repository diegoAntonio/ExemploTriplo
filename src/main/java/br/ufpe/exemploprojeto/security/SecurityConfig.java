package br.ufpe.exemploprojeto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@ComponentScan(
        basePackages = {
    "br.ufpe.exemploprojeto"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AutenticatorProviderExemplo());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
				.and()
			.authorizeRequests()
				.antMatchers("/resource/**")
				.permitAll().anyRequest().authenticated()
				.and()
			.authorizeRequests()
				.and()  
			.exceptionHandling()
				.accessDeniedPage("/error-404.xhtml")
				.and()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.failureUrl("/login.xhtml").loginPage("/login.xhtml").permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/login.xhtml").permitAll()
//				.and()
//			.formLogin().permitAll()
			;
	}
	
	private void configurarResources(HttpSecurity http) throws Exception {
		
	}
	
}	
