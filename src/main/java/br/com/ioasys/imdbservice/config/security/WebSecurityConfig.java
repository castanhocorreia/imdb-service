package br.com.ioasys.imdbservice.config.security;

import br.com.ioasys.imdbservice.config.security.impl.AuthenticationEntryPointImpl;
import br.com.ioasys.imdbservice.config.security.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String[] WHITE_LIST = {"/auth/**", "/users/**"};

  private final AuthenticationEntryPointImpl authenticationEntryPoint;
  private final UserDetailsServiceImpl userDetailsService;

  public WebSecurityConfig(
      AuthenticationEntryPointImpl authenticationEntryPoint,
      UserDetailsServiceImpl userDetailsService) {
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers(WHITE_LIST)
        .permitAll()
        .antMatchers(HttpMethod.GET, "/users/a")
        .hasRole("USER")
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable();
  }

  @Bean
  GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults("");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
