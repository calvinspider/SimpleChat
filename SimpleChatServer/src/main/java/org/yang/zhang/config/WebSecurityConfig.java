package org.yang.zhang.config;

import org.yang.zhang.auth.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 11:41
 */
@Configuration
@EnableWebSecurity      // 开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true)  //AOP
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginSuccessHandler loginSuccessHandler=new LoginSuccessHandler();
        loginSuccessHandler.setDefaultTargetUrl("/home");
        loginSuccessHandler.setForwardToDestination(true);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error")
                .permitAll().and()
                .logout().permitAll();
    }
}
