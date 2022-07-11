package it.goodgamegroup.up.security;

import it.goodgamegroup.up.configurations.DefaultGroupName;
import it.goodgamegroup.up.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${user.username}")
    private String userUsername;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${user.password}")
    private String userPassword;
    @Value("${admin.password}")
    private String adminPassword;


    @Bean
    public UserDetailsService defaultUsers(){
        UserDetails user = User.builder()
                .username(userUsername)
                .password(passwordEncoder().encode(userPassword))
                .authorities(DefaultGroupName.USER)
                .build();
        UserDetails admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .authorities(DefaultGroupName.USER, DefaultGroupName.ADMIN)
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .and()
                .userDetailsService(defaultUsers());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test").permitAll()
                .antMatchers("/encode").permitAll()
                .antMatchers("/timestamp").permitAll()
                //API mapping privileges
                .antMatchers(HttpMethod.DELETE , "/users*").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers(HttpMethod.PUT , "/users*").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers(HttpMethod.GET , "/users*").hasAnyAuthority(DefaultGroupName.ADMIN , DefaultGroupName.USER)
                .antMatchers(HttpMethod.DELETE , "/permissions*").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers( HttpMethod.GET ,"/permissions*").hasAnyAuthority(DefaultGroupName.ADMIN , DefaultGroupName.USER)
                .antMatchers( HttpMethod.PUT ,"/permissions*").hasAnyAuthority(DefaultGroupName.ADMIN , DefaultGroupName.USER)
                .antMatchers(HttpMethod.POST , "/reports*").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers("/add-user").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers("/permission-type").hasAuthority(DefaultGroupName.ADMIN)
                .antMatchers("/").authenticated().anyRequest().permitAll()
                .and().formLogin()
                .and().httpBasic();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }

}