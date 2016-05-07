package de.medhelfer.security;

import de.medhelfer.data.User;
import de.medhelfer.data.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class AuthenticationService {

    // TODO replace by savely stored key
    private static final Key KEY = MacProvider.generateKey();

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // TODO configure globally

    @Inject
    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Parse user and data from a token, where it was previously stored and signed.
     *
     * @param token JWS Token without prefix
     * @return {@link Authentication} parsed from the token
     * @throws UnsupportedJwtException  if the {@code claimsJws} argument does not represent an Claims JWS
     * @throws MalformedJwtException    if the {@code claimsJws} string is not a valid JWS
     * @throws SignatureException       if the {@code claimsJws} JWS signature validation fails
     * @throws ExpiredJwtException      if the specified JWT is a Claims JWT and the Claims has an expiration time
     *                                  before the time this method is invoked.
     * @throws IllegalArgumentException if the {@code claimsJws} string is {@code null} or empty or only whitespace
     */
    public Authentication parseToken(String token) {
        final Claims body = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Collection<String> roles = body.get("roles", Collection.class);
        for (String role : roles) {
            authorities.add(() -> role);
        }
        final String username = body.getSubject();

        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return username;
            }
        };
    }

    public String createToken(String username, String password) {
        User user = userService.findByUsername(username);

        if (user == null ||
                !passwordEncoder.matches(password, user.getPassword()))
            return null;

        Claims extendedClaims = Jwts.claims();
        extendedClaims.put("roles", user.getRoles());

        return Jwts.builder()
                .setClaims(extendedClaims)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(12))))
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }
}
