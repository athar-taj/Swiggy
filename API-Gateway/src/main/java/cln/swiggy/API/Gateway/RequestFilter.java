package cln.swiggy.API.Gateway;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    private TokenUtils tokenUtils;

    private static final List<String> SKIP_TOKEN_PATHS = Arrays.asList(
            "/api/users/register",
            "/api/users/login",
            "/api/users/verify-otp",
            "/api/users/validate-token",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/v3/api-docs/swagger-config",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/swagger-resources",
            "/swagger-resources/",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        boolean shouldSkip = SKIP_TOKEN_PATHS.stream()
                .anyMatch(skipPath ->
                        path.startsWith(skipPath) ||
                                path.equals(skipPath.substring(0, skipPath.length() - 1)));

        if (shouldSkip) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            Claims claims = tokenUtils.validateToken(token, SECRET);

            if (claims != null) {
                String phoneNo = claims.getSubject();
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Invalid or expired token");
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("No token provided");
        }
    }
}
