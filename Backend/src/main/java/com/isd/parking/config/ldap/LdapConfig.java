package com.isd.parking.config.ldap;

import com.isd.parking.service.ldap.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.isd.parking.*"})
@Profile("default")
@EnableLdapRepositories(basePackages = "com.isd.parking.**")
public class LdapConfig {

    private final Environment env;

    @Autowired
    public LdapConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(env.getRequiredProperty("ldap.partitionSuffix"));

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public UserService ldapClient() {
        return new UserService();
    }
}
