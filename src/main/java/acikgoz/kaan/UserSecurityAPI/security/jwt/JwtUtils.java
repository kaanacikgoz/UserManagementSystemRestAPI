package acikgoz.kaan.UserSecurityAPI.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.util.Date;

@Component
public class JwtUtils {

    private final String jwtSecret="secret"; // Use a stronger secret in production
    private final Long jwtExpirationMS =86400000L;    //24*60*60*1000(24 hours)

    public String generateJwtToken(UserDetails userDetails){
        // Convert the plain secret key string to a Key object
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        /*The Keys.hmacShaKeyFor method expects the secret key to be a Base64-encoded string,
        but in your case, you are using a plain string ("secret")
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        */

        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setExpiration(new Date(new Date().getTime()+jwtExpirationMS)).
                signWith(key, SignatureAlgorithm.HS512) // Use the Key object here
                .compact();
    }

    public String getEmailFromToken(String token){

        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String token){
        try {

            Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

            // Parse the token and validate it
            Jwts.parserBuilder()
                    .setSigningKey(key)
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
