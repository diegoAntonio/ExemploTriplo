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
		HttpSecurity config = http;
		config = configurarResources(config);
		config = configurarResourcesHandling(config);
		config = configurarFormularioLogin(config);
		config.authorizeRequests()
				.anyRequest().authenticated()
				.and();
	}
	
	
	/**
	 * Metodo que define o formulario de login<br />
	 * <strong> .formLogin</strong> Altera o fluxo para modificar os parametros do formulario de login.<br />
	 * <strong> .usernameParameter</strong> Define o name do input.<br />
	 * <strong> .passwordParameter</strong> Define o password do input.<br />
	 * <strong> .defaultSuccessUrl</strong> Define o url padrao apos sucesso.<br />
	 * <strong> .logoutSuccessUrl</strong> Define o url padrao apos logout com sucesso.<br />
	 * <strong> .failureUrl</strong> Define a pagina do em caso de falha do login.<br />
	 * <strong> .loginPage</strong> Define a pagina de login de acesso.<br /><br />
	 * <strong> .logout</strong> Altera para o fluxo de logout.<br />
	 * <strong> .invalidateHttpSession</strong> Define a invalidade da sessao.<br />
	 * <strong> .logoutSuccessUrl</strong> Define a pagina de sucesso do logout.<br />
	 * @param http - {@link HttpSecurity} eh o objeto que sera mapeado as rotas.
	 * @return {@link HttpSecurity} retorno atualizado.
	 * @throws Exception - Qualquer error durante as autorizacoes.
	 */
	private HttpSecurity configurarFormularioLogin(HttpSecurity http) throws Exception{
		return http
				.formLogin().defaultSuccessUrl("/index.xhtml").usernameParameter("username")
				.failureUrl("/login.xhtml").loginPage("/login.xhtml").passwordParameter("password")
				.permitAll().and()
				.logout().invalidateHttpSession(true).logoutSuccessUrl("/login.xhtml").permitAll().and();
	}
	
	/**
	 * Metodo que define os recursos utilizado nas paginas.
	 * 
	 * @param http - {@link HttpSecurity} eh o objeto que sera mapeado as rotas.
	 * @return {@link HttpSecurity} retorno atualizado.
	 * @throws Exception - Qualquer error durante as autorizacoes.
	 */
	private HttpSecurity configurarResources(HttpSecurity http) throws Exception {
		return  http
//	            .antMatcher("/**")
////	            .authorizeRequests()
////	            .anyRequest()
//	            .access("authenticated").and()
//				.permitAll().anyRequest().authenticated()
//				.and()
				.authorizeRequests().antMatchers("/javax.faces.resource/**").permitAll()
				.and()
				.authorizeRequests().antMatchers("/resource/**").permitAll()
				.and();
	}
	
	/**
	 * @param http - {@link HttpSecurity} eh o objeto que sera mapeado as rotas.
	 * @return {@link HttpSecurity} retorno atualizado.
	 * @throws Exception - Qualquer error durante as autorizacoes.
	 */
	private HttpSecurity configurarResourcesHandling(HttpSecurity http) throws Exception {
		return http
				.exceptionHandling().accessDeniedPage("/error-404.xhtml")
				.and();
	}
	
}	
