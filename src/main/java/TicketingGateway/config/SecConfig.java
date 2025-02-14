package TicketingGateway.config;


import TicketingGateway.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author GesangZeren
 * @project TicketingGateway
 * @date 11/11/2024
 */

/*Configure and implement security mechanism.configure it on port 8282*/

@Configuration
@EnableWebSecurity
public class SecConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/forgot-password","/WEB-INF/views/**","/tickets/reopen/**","/uploads/tickets/**").permitAll()
                        .requestMatchers("/approve-tickets", "/approve-ticket/**", "/decline-ticket/**", "/delete-ticket/**","/ticket-details/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }



}
