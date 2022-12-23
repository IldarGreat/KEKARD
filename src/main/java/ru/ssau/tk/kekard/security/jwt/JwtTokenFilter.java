package ru.ssau.tk.kekard.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${application.auth.cookie-name}")
    String cookieName;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie[] requestCookies = request.getCookies();
        if (requestCookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : requestCookies) {
            if (cookie.getName().equals(cookieName)) {
                token = cookie.getValue();
            }
        }
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String validationInformation = jwtTokenProvider.validateToken(token);
        if (validationInformation != null) {
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(validationInformation, "", Set.of(new SimpleGrantedAuthority("USER"))));
        }
        filterChain.doFilter(request, response);
    }
}
