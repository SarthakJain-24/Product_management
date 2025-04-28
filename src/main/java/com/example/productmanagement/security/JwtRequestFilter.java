package com.example.productmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.productmanagement.exception.PMMessage;
import com.example.productmanagement.exception.ProductException;

import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                    FilterChain chain) throws ServletException, IOException {
	        final String header = request.getHeader("Authorization");
	        String username = null, jwt = null;
	        if (header != null && header.startsWith("Bearer ")) {
	            jwt = header.substring(7);
	            try { username = jwtUtil.extractUsername(jwt);
	            } catch (ExpiredJwtException e) {
	            	logger.error("Token expired");
	                throw new ProductException(PMMessage.TOKEN_EXPIRED);
	            }
	            catch (Exception e) {
	            	logger.error("Invalid Token");
	                throw new ProductException(PMMessage.INVALID_JWT);
	            }
	        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}