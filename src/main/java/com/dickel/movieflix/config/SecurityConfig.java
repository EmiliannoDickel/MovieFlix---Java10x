package com.dickel.movieflix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

   @Bean //pede para o spring cuidar para mim da criação e gerenciamento do bean
           //SecutityFilterChain é uma interface do Spring Security que define a cadeia de filtros de segurança que serão aplicados às requisições HTTP.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf(csrf -> csrf.disable()) // desabilita a proteção do spring padrao
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //configura para toda request verificar se e de alguem autenticado
               .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.POST, "/movieflix/auth/register").permitAll().requestMatchers(HttpMethod.POST, "/movieflix/auth/login").permitAll() // aqui libera para todo mundo que acessar a rota de autenticação adiciondas
                       .anyRequest().authenticated()) // aqui pede para qualquer outra requisição estar autenticada
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
    public PasswordEncoder passwordEncoder() { // codifica a senha do usuario.
       return new BCryptPasswordEncoder();
   }






}
