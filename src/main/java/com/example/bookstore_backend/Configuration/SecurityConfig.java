package com.example.bookstore_backend.Configuration;

import com.example.bookstore_backend.util.UnAuthenticationHandler;
import com.example.bookstore_backend.util.AuthProvider;
import com.example.bookstore_backend.util.UnAuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthFilter authFilter;
    @Autowired
    private final UnAuthenticationHandler unAuthenticationHandler;
    @Autowired
    private final UnAuthorizationHandler unAuthorizationHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //设置过滤链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)

                //管理url是否受到保护
            .authorizeHttpRequests((request)->{
                request.requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/v1/user/adminTest", "/v1/user/ban", "/v1/user/unban").hasRole("ADMIN")
                        .requestMatchers("v1/order/orders", "v1/book/books").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "v1/book/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "v1/book/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "v1/book").hasRole("ADMIN")
                        .requestMatchers("v1/image/images/**").permitAll()
                        .anyRequest()
                        .authenticated();
            })

                //管理session会话
                // 无状态创建session 每个用户只能获得一个session否则阻止登录
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  //session策略配置为无状态，意味着服务器不会创建session来存储认证信息 基于JWT的认证机制
                                .sessionFixation().migrateSession()
                                .maximumSessions(1)   //每个用户限制最多拥有一个会话
                                .maxSessionsPreventsLogin(true)) //达到会话上限之后阻止新会话

                //自定义authenticationProvider
            .authenticationProvider(authenticationProvider())

                //自定义过滤链 在UsernamePasswordAuthenticationFilter过滤链之前
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                // 认证失败、鉴权失败时 对response写入返回值
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(unAuthenticationHandler)
                            .accessDeniedHandler(unAuthorizationHandler));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthProvider();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new AuthService(userRepository);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
