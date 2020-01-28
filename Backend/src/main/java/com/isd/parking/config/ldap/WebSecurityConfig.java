package com.isd.parking.config.ldap;

import com.isd.parking.model.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
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
        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
        http.cors().and()
                // We don't need CSRF for this cause
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin().and()
                .authorizeRequests()
                // dont authenticate this particular request
                .antMatchers("/login", "/registration", "/parking", "/arduino", "/test").permitAll()
                // all other requests need to be authenticated
                .anyRequest().fullyAuthenticated()
                .and().exceptionHandling()
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

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class CORSFilter implements Filter {

        /**
         * CORS filter for http-request and response
         */
        public CORSFilter() {
            super();
        }

        /**
         * Do Filter on every http-request.
         */
        @Override
        public final void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
            final HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

            // without this header jquery.ajax calls returns 401 even after successful login and SSESSIONID being succesfully stored.
            response.setHeader("Access-Control-Allow-Credentials", "true");

            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Origin, Content-Type, Version");
            response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type");

            final HttpServletRequest request = (HttpServletRequest) req;
            if (!request.getMethod().equals("OPTIONS")) {
                chain.doFilter(req, res);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        /**
         * Destroy method
         */
        @Override
        public void destroy() {
        }

        /**
         * Initialize CORS filter
         */
        @Override
        public void init(FilterConfig arg0) throws ServletException {
        }
    }
}
