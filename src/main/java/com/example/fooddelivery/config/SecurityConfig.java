package com.example.fooddelivery.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import static io.netty.util.CharsetUtil.encoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/delivery/restaurants/**").permitAll()
//                                .requestMatchers("/delivery/deliveryGuy").permitAll()
                                .requestMatchers("/delivery/deliveryGuy/all").permitAll()
                                .requestMatchers("/delivery/deliveryGuy/{restaurantName}/create").permitAll()
                                .requestMatchers("/delivery/customer/**").permitAll()
                                .requestMatchers("/delivery/customer/registration").permitAll()
//                                .requestMatchers("/delivery/deliveryGuy/{userName}").hasRole("ADMIN")
                                .requestMatchers("/delivery/menu/{restaurantName}/createPizza").permitAll()
                                .requestMatchers("/delivery/menu/{restaurantName}/createPasta").permitAll()
                                .requestMatchers("/delivery/menu/{restaurantName}/createSteak").permitAll()
                                .requestMatchers("/delivery/menu/{restaurantName}/createSalad").permitAll()
                                .requestMatchers("/delivery/deliveryGuy/forgotPassword").permitAll()
                                .requestMatchers("/delivery/deliveryGuy/confirm").permitAll()
                                .requestMatchers("/delivery/menu/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "http://localhost:8084/delivery/pizza").hasAnyRole("CUSTOMER")
                                .requestMatchers(HttpMethod.PATCH, "http://localhost:8084/delivery/deliveryGuy/update").hasAnyRole("DELIVERYGUY")


                                .anyRequest().authenticated()
                )
                //.logout((logout) -> logout.logoutSuccessUrl("http://localhost:8083/healthcare/customers/logout"))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
}

