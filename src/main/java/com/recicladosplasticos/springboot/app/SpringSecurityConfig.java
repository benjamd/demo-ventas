package com.recicladosplasticos.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.recicladosplasticos.springboot.app.auth.handler.LoginSuccessHandler;
import com.recicladosplasticos.springboot.app.models.service.JpaUserDetailsService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 //TODO: Aplicar permisos con @Secured y @PreAuthorize desde el Controller y quitar los antMatchers
		http.authorizeRequests().antMatchers("/css/**","/js/**", "/images/**","/locale/**").permitAll()
		.antMatchers("/cliente/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("USER")
		.antMatchers("/credito/**").hasAnyRole("USER")
		.antMatchers("/presupuesto/**").hasAnyRole("USER")
		.antMatchers("/debito/**").hasAnyRole("USER")
		.antMatchers("/devolucion/**").hasAnyRole("USER")
		.antMatchers("/remito/**").hasAnyRole("USER")
		.antMatchers("/recibo/**").hasAnyRole("USER")
		.antMatchers("/ctacte/**").hasAnyRole("USER")
		.antMatchers("/reporte/**").hasAnyRole("USER")
		.antMatchers("/ventas/**").hasAnyRole("ADMIN")
		.antMatchers("/ventas/**").hasAnyRole("USER")
		.antMatchers("/usuarios/**").hasAnyRole("ADMIN")
		.antMatchers("/puntodeventa	/**").hasAnyRole("USER")
		.antMatchers("/unidades/**").hasAnyRole("USER")
		.antMatchers("/producto/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and().formLogin()
		.successHandler(successHandler)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error/403");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	
		return new BCryptPasswordEncoder();

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		
		builder.userDetailsService(userDetailsService);
		
		
	}
		
		
		

}
