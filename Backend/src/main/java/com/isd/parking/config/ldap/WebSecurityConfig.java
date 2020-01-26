package com.isd.parking.config.ldap;

import com.isd.parking.model.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * Ldap config
     * */
    @Value("${ldap.enabled}")
    private String ldapEnabled;

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.partitionSuffix}")
    private String ldapBaseDn;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Value("${ldap.principal}")
    private String ldapSecurityPrincipal;

    @Value("${ldap.password}")
    private String ldapPrincipalPassword;

    /*
     * For 'in memory' auth
     * */
    @Value("${http.auth.admin.name}")
    private String admin;

    @Value("${http.auth.admin.pass}")
    private String adminPass;

    @Value("${http.auth.user.name}")
    private String user;

    @Value("${http.auth.user.pass}")
    private String userPass;

    // ----------  LDAP auth --------------

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().fullyAuthenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        if (Boolean.parseBoolean(ldapEnabled)) {
            auth
                    .ldapAuthentication()
                    .userDnPatterns(ldapUserDnPattern)
                    .groupSearchBase("ou=groups")
                    .contextSource()
                    .url("ldap://localhost:18889/dc=isd,dc=com")
                    // .managerDn(ldapSecurityPrincipal)
                    // .managerPassword(ldapPrincipalPassword)
                    .and()
                    .passwordCompare()
                    .passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordAttribute("userPassword");
        } else {
            // use basic HTTP authentication
            auth
                    .inMemoryAuthentication()
                    .withUser(user).password(userPass).roles(String.valueOf(Roles.USER))
                    .and()
                    .withUser(admin).password(adminPass).roles(String.valueOf(Roles.ADMIN));
        }
    }
}
