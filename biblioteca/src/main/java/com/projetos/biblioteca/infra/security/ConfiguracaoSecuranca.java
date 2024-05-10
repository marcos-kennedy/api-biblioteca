package com.projetos.biblioteca.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecuranca {

    @Autowired
    private SecurancaFiltro securancaFiltro;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/autenticacao/cadastrar").permitAll()//depois criar o user admin na mão
            .requestMatchers(HttpMethod.POST, "/emprestimos").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/livros").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/emprestimos/{qtdRegistros}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/livros/{nomeLivro}").hasRole("ADMIN")
            .anyRequest().authenticated()
            )
        .addFilterBefore(securancaFiltro, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 
