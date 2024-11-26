package dev.bengi.feedbackservice.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final Claims claims = extractAllClaims(jwt);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Extract role directly from claims
                String role = claims.get("role", String.class);
                log.debug("Raw role from token: {}", role);

                if (role == null || role.isEmpty()) {
                    log.warn("No role found in token claims: {}", claims);
                    throw new AccessDeniedException("No role found in token");
                }

                // Create authority with ROLE_ prefix
                var authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role));
                log.debug("Created authorities: {}", authorities);

                var authToken = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("Authentication set in SecurityContext: {}", authToken);
            }
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token validation failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private java.security.Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}