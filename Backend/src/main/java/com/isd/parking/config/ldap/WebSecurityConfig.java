package com.isd.parking.config.ldap;

import com.isd.parking.model.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password("aRduin1$")
                .roles(String.valueOf(Roles.USER));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        repository.setCookieHttpOnly(false);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/parking").permitAll()
                .antMatchers("/parking").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // ----------  LDAP --------------

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .antMatchers("/login").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                ;

//        http.csrf().
//                disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=users")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:18889/dc=isd,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }*/

}
