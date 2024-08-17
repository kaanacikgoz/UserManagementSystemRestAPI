package acikgoz.kaan.UserSecurityAPI.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final Long jwtExpirationMS =86400000L;    //24*60*60*1000(24 hours)

    public JwtUtils() {
        // Generate a secure key for HS512
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateJwtToken(UserDetails userDetails){
        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setExpiration(new Date(new Date().getTime()+jwtExpirationMS)).
                signWith(secretKey, SignatureAlgorithm.HS512) // Use the Key object here
                .compact();
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String token){
        try {
            // Parse the token and validate it
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }

}
