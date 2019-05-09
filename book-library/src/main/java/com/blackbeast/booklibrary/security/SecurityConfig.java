package com.blackbeast.booklibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/books").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/books/hires/**").hasAnyAuthority("ADMIN")
                .antMatchers("/api/**").hasAnyAuthority("DEV")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/books")
                .and().exceptionHandling().accessDeniedPage("/access-denied")
                .and().httpBasic();

        //httpSecurity.headers().frameOptions().disable();
        //httpSecurity.csrf().ignoringAntMatchers("/h2-console/**");
    }

    @Autowired
    public void securityUsers(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USER u WHERE u.USERNAME = ?")
                .authoritiesByUsernameQuery("SELECT USERNAME, NAME FROM USER u JOIN ROLE r ON u.ID = r.USER_ID WHERE u.USERNAME = ?");
    }
}
