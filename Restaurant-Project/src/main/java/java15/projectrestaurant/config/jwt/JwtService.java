package java15.projectrestaurant.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java15.projectrestaurant.dto.response.JwtToken;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

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
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String token = JWT.create()
                .withClaim(CLAIM_EMAIL, user.getEmail())
                .withClaim(CLAIM_ID, user.getId())
                .withClaim(CLAIM_ROLE, user.getRole().getAuthority())
                .withIssuedAt(zonedDateTime.toInstant())
                .withExpiresAt(zonedDateTime.plusSeconds(expiration).toInstant())
                .sign(getAlgorithm());
        return  new JwtToken(token, zonedDateTime, zonedDateTime.plusSeconds(expiration));
    }

    public User verifyToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        String email = verify.getClaim(CLAIM_EMAIL).asString();
        return userRepository.findByEmailOrThrow(email);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secretKey);
    }
}