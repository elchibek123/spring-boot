package java15.instagram.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java15.instagram.model.entity.User;
import java15.instagram.exception.AuthenticationException;
import java15.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.security.jwt.secret_key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration}")
    private Long expiration;

    public static final String CLAIM_ID = "id";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";

    private final UserRepository userRepository;

    public JwtToken createToken(User user) {
        ZonedDateTime issuedAt = ZonedDateTime.now();
        ZonedDateTime expiresAt = issuedAt.plusSeconds(expiration);

        String token = JWT.create()
                .withClaim(CLAIM_EMAIL, user.getEmail())
                .withClaim(CLAIM_ID, user.getId())
                .withClaim(CLAIM_ROLE, user.getRole().getAuthority())
                .withIssuedAt(issuedAt.toInstant())
                .withExpiresAt(expiresAt.toInstant())
                .sign(getAlgorithm());
        return new JwtToken(token, issuedAt, expiresAt);
    }

    public User verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
            DecodedJWT verify = jwtVerifier.verify(token);

            String email = verify.getClaim(CLAIM_EMAIL).asString();
            return userRepository.findByEmailOrThrow(email);
        } catch (JWTVerificationException e) {
            throw new AuthenticationException("Invalid or expired token", e);
        } catch (NoSuchElementException e) {
            throw new AuthenticationException("User not found for the provided token", e);
        }
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
