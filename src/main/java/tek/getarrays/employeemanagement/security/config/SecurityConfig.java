package tek.getarrays.employeemanagement.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tek.getarrays.employeemanagement.security.fliter.JwtFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    private static final String[] AUTH_PERMIT = {
            "/auth/**",
            "/user/register",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

//Responsable de l'authentification des users
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
         http.csrf(AbstractHttpConfigurer::disable)//desactive la protection csrf
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(AUTH_PERMIT).permitAll()
                                .antMatchers(HttpHeaders.ALLOW).permitAll()
                                .anyRequest().authenticated()
                )
                 .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)) //Exception pour les routes non autorise
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);//filtrer en recuperanr les requetes et valider le token jwt
                return http.build();
    }


}
