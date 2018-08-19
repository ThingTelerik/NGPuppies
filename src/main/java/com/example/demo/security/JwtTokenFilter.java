package com.example.demo.security;


import com.example.demo.services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
     private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserServiceImplement userService;

    private static final String header  = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {

           Optional<String> token = getTokenString(request.getHeader(header));

           if (StringUtils.hasText(String.valueOf(token)) && jwtTokenProvider.validateToken(String.valueOf(token))) {
               Integer userId = jwtTokenProvider.getUserIdFromJwt(String.valueOf(token));

               UserDetails userDetails = userService.loadUserById(userId);
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }
       }catch (Exception ex){
           System.out.println(ex.getMessage() + "Not able to set user authentication");
       }
       filterChain.doFilter(request,response);
    }


    private Optional<String> getTokenString(String header) {
        String[] token = header.split(" ");
        if (token.length < 2) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(token[1]);

        }
    }
}