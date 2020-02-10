package ru.galuzin.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import ru.galuzin.store.service.UserSecurityService;

import java.security.SecureRandom;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SALT = "rRkt4)leT8";

    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/checkAdmin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/book/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/book/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/customer/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/order/*").permitAll()
                .anyRequest().authenticated()
        ;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService)/*.passwordEncoder(passwordEncoder())*/;
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}
