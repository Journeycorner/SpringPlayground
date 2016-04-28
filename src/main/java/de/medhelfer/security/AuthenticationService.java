package de.medhelfer.security;

import de.medhelfer.data.User;
import de.medhelfer.web.UsernamePassword;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.security.Key;

@Service
public class AuthenticationService {

    // TODO replace by savely stored key
    private static Key KEY = MacProvider.generateKey();

    private EntityManager em;

    @Autowired
    public AuthenticationService(EntityManager em) {
        this.em = em;
    }

    /*
    public SessionUser parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        @SuppressWarnings("unchecked")
        Collection<String> roles = body.get("roles", Collection.class);
        for (String role : roles) {
            authorities.add(() -> role);
        }

        // build SessionUser object from token values
        return new SessionUser(
                body.get("sub", String.class),
                "null",
                true,
                true,
                true,
                true,
                authorities,
                body.get("id", Number.class).longValue(),
                body.get("firstname", String.class),
                body.get("lastname", String.class),
                body.get("email", String.class),
                body.get("tfaSetupDone", Boolean.class),
                body.get("timeZone", String.class)
    }*/

    public String createToken(UsernamePassword usernamePassword) {
        User singleResult = em.createNamedQuery(User.QUERY_FIND_BY_USERNAME, User.class)
                .setParameter(User.PARAM_USERNAME, usernamePassword.getUsername())
                .getSingleResult();

        // TODO verify password

        Claims extendedClaims = Jwts.claims();
        extendedClaims.put("roles", singleResult.getRoles());

        return Jwts.builder()
                .setClaims(extendedClaims)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(12))))
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(singleResult.getUsername())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }
}
