package uz.pdp.shippingservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import uz.pdp.shippingservice.config.jwtConfig.AuthEntryPointJwt;
import uz.pdp.shippingservice.config.jwtConfig.JwtFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtAuthFilter;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtAuthFilter,
                                 AuthEntryPointJwt unauthorizedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private final String[] WHITE_LIST = new String[]{
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/images/**",
            "webapp/**",
            "/v1/user/**",
            "/v1/user/get-token-by-refresh-token",
            "/v1/advertising/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
//                .cors()
//                .configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOriginPatterns(List.of("*"));
//                    configuration.setAllowedMethods(List.of("GET", "POST",  "PUT", "DELETE", "PATCH"));
//                    configuration.setAllowedHeaders(List.of("*"));
//                    configuration.setAllowCredentials(true);
//                    return configuration;
//                })
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(WHITE_LIST).permitAll()
                                .requestMatchers(HttpMethod.POST ,WHITE_LIST).permitAll()
                                .requestMatchers(HttpMethod.GET ,WHITE_LIST).permitAll()
                                .requestMatchers("/gs-guide-websocket/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
