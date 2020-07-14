package ba.sema.springbootvuedemo.configurations;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils
{
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${application.settings.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${application.settings.jwt.expiration-ms}")
    private int jwtExpirationMilliseconds;

    public String generateJwtToken(Authentication authentication)
    {
        SecurityUserDetails securityUserDetails = (SecurityUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                   .setSubject(securityUserDetails.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + jwtExpirationMilliseconds))
                   .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                   .compact();
    }

    public String getUsernameFromJwtToken(String token)
    {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken)
    {
        try
        {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException e)
        {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }
        catch (MalformedJwtException e)
        {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e)
        {
            logger.error("JWT token is expired: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e)
        {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
