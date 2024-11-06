package uz.mohirdev.lesson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.mohirdev.lesson.entities.Role;
import uz.mohirdev.lesson.security.JWTConfigure;
import uz.mohirdev.lesson.security.JWTFilter;
import uz.mohirdev.lesson.security.JwtTokenProvider;
import uz.mohirdev.lesson.service.CustomUserDetailService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailService customUserDetailService;

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(CustomUserDetailService customUserDetailService, JWTFilter jwtFilter) {
        this.customUserDetailService = customUserDetailService;
        this.jwtFilter = jwtFilter;
    }


//    @Override
//    protected void configure(HttpSecurity http)throws Exception{
//        http
//                .csrf()
//                .disable()
//                .headers()
//                .frameOptions()
//                .disable()
//                .and()
//                .authorizeHttpRequests()
//                .antMatchers("/api/posts/paging/**").hasRole("ADMIN")
//                .antMatchers("/api/posts/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/api/posts/paging/**").permitAll
//                .antMatchers("/api/authenticate").permitAll
//                .antMatchers("/api/register").permitAll
//                .anyRequest().authenticated()
//                .and()
//                .HttpBasic()
//                .and()
//                .apply(securityConfigurerAdapter());
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("api/register",
                                        "api/authinticate")
                                .permitAll()
                                .requestMatchers("/api/posts/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/employees/**").hasAnyAuthority("USER")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(customUserDetailService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    private JWTConfigure securityConfigurerAdapter(){
//        return new JWTConfigure(jwtTokenProvider);
//    }
}