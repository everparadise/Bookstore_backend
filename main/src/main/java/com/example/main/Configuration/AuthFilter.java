package com.example.main.Configuration;

import com.example.main.util.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//过滤器
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public AuthFilter(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    //责任链模式 过滤
    //request请求
    //response 生成的响应
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String username;
        final String jwtToken;

        //判断如果请求头空或不是bearer token直接传入下一个filter
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        //从请求头中提取token
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);
        System.out.println("filter authenticate");
        //检查上下文是否进行过验证
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwtToken, userDetails)){
                //用来更新上下文
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, jwtToken, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("security Context Holder set authenticate");
            }
        }
        //责任链模式，传入下一个过滤器
        filterChain.doFilter(request, response);
    }


}
