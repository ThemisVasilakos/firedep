package gr.agileadvisors.firedep.firedep.security;

import gr.agileadvisors.firedep.firedep.security.jwt.CustomJwtAuthenticationFilter;
import gr.agileadvisors.firedep.firedep.security.jwt.JwtAuthenticationEntryPoint;
import gr.agileadvisors.firedep.firedep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final UserService userService;

    @Autowired
    private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http .cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**")
                            .permitAll()
                            .requestMatchers ("/firedep/register","/firedep/login")
                            .permitAll()
                            .requestMatchers(HttpMethod.GET,"/firedep/news","/firedep/news/{id}","/firedep/courses","/firedep/courses/{id}")
                            .permitAll()
                            .requestMatchers (HttpMethod.DELETE,"/firedep/news/{id}","/firedep/courses/{id}")
                            .access(new WebExpressionAuthorizationManager("hasRole('ADMIN')" ))
                            .requestMatchers(HttpMethod.POST,"/firedep/news","/firedep/courses")
                            .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('MODERATOR')"))
                            .requestMatchers(HttpMethod.PUT,"/firedep/news/{id}","/firedep/courses/{id}")
                            .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('MODERATOR')"))
                            .anyRequest().denyAll();
                });

        return http.build();
    }



}
