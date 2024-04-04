package com.ivan.walletservice.security;

import com.ivan.walletservice.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Custom JWT token filter that intercepts incoming requests to validate JWT tokens and set up authentication.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Filters the incoming request to validate and parse JWT for authentication.
     *
     * @param request     HTTP servlet request.
     * @param response    HTTP servlet response.
     * @param filterChain Filter chain for request handling.
     * @throws ServletException if an error occurs during the servlet processing.
     * @throws IOException      if an input or output error occurs while reading from or writing to the servlet.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJWTFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getLoginFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = createAuthentication(request, userDetails);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    /**
     * Creates an authentication token using user details and request details.
     *
     * @param request     HTTP servlet request.
     * @param userDetails User details for authentication.
     * @return Created UsernamePasswordAuthenticationToken.
     */
    private UsernamePasswordAuthenticationToken createAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    /**
     * Extracts JWT token from the Authorization header in the request.
     *
     * @param request HTTP servlet request.
     * @return Extracted JWT token or null if not present.
     */
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}