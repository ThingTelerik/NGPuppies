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

          String token = getTokenString(request);

           if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
               Long userId = jwtTokenProvider.getUserIdFromJwt(token);

              // UserDetails userDetails = userService.loadUserById(userId);
            //   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

             //  SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }
       }catch (Exception ex){
           System.out.println(ex.getMessage() + "Not able to set user authentication");
       }
        filterChain.doFilter(request, response);
    }



    private String getTokenString(HttpServletRequest request) {
       String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}