package it.goodgamegroup.up.security;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test").permitAll()
                .antMatchers("/encode").permitAll()
                .antMatchers("/timestamp").permitAll()
                //API mapping privileges
                .antMatchers(HttpMethod.DELETE , "/users*").hasAuthority(Constant.ADMIN)
                .antMatchers(HttpMethod.PUT , "/users*").hasAuthority(Constant.ADMIN)
                .antMatchers(HttpMethod.GET , "/users*").hasAnyAuthority(Constant.ADMIN , Constant.USER)
                .antMatchers(HttpMethod.DELETE , "/permissions*").hasAuthority(Constant.ADMIN)
                .antMatchers( HttpMethod.GET ,"/permissions*").hasAnyAuthority(Constant.ADMIN , Constant.USER)
                .antMatchers( HttpMethod.PUT ,"/permissions*").hasAnyAuthority(Constant.ADMIN , Constant.USER)
                .antMatchers(HttpMethod.POST , "/reports*").hasAuthority(Constant.ADMIN)
                .antMatchers("/add-user").hasAuthority(Constant.ADMIN)
                .antMatchers("/permission-type").hasAuthority(Constant.ADMIN)
                .antMatchers("/").authenticated().anyRequest().permitAll()
                .and().formLogin()
                .and().httpBasic();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}