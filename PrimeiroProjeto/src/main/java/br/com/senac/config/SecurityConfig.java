package br.com.senac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.senac.security.JWTAuthenticationFilter;
import br.com.senac.security.JWTAuthorizationFilter;
import br.com.senac.security.JWTUtil;
import br.com.senac.servico.AlunoDetailsService;

import org.springframework.http.HttpMethod;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private AlunoDetailsService  userDetailsService;
	
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// definidno que esta liberado
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
			
	};
	
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/cursos/**",
			"/categorias/**"
			
			
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/alunos"
			
	};
	
	//vamos sovreescrever o metodo o do webSecurity para definir as permicoes
	
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable(); // vai acionar o Bean corsConfigurationSource para aplicar as configuracoes do cors
											// alem disso nao vai precisar validar as sessoes
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // o que tiver nessa lista pode liberar se for get 
		.antMatchers(PUBLIC_MATCHERS).permitAll() // o que tiver em PUBLIC_MATCHERS pode liberar
		.anyRequest().authenticated(); // os demais acesso precisa autenticar
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); // indicando o filtro de autenticacao
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService)); // indicando o filtro de autorizacao
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // nao vamos permitir criacao de sessao
	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	
	
	// Nao ter problema de Cors vamos usar a configuracao abaixo
	// assim outras aplicacoes podem acessar
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// iremos utilizar em qualquer classe o encrypton da senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
