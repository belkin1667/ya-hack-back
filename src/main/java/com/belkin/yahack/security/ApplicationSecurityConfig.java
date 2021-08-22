package com.belkin.yahack.security;

import javax.crypto.SecretKey;

import com.belkin.yahack.api.ExceptionHandlerFilter;
import com.belkin.yahack.security.api.JwtAuthenticationFilter;
import com.belkin.yahack.security.jwt.JwtConfig;
import com.belkin.yahack.security.api.JwtTokenVerifier;
import com.belkin.yahack.security.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.belkin.yahack.security.ApplicationUserRole.AUTHOR;
import static com.belkin.yahack.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final ApplicationUserService applicationUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // todo: can it be disabled?
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
                    .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                    .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html", "/swagger-resources/configuration/security", "/null/swagger-resources/**").permitAll()
                    .antMatchers("/auth/check/user").hasRole(USER.name())
                    .antMatchers("/auth/check/author").hasRole(AUTHOR.name())
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/mock/**").permitAll()
                    .antMatchers("/edit/**").hasRole(AUTHOR.name())
                    .antMatchers("/i/edit").hasRole(AUTHOR.name())
                    .antMatchers("/i/**").permitAll()
                    .antMatchers("/**").hasRole(USER.name())
                    .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
