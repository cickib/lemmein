package com.codecool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/*").authenticated()
                .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/")
                .and().logout().logoutSuccessUrl("/").deleteCookies()
                .and().exceptionHandling();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("leiwand").password("1234").roles("ADMIN");
    }

}
