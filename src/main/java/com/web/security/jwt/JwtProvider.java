package com.web.security.jwt;


import com.web.security.user_principle.HotelUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {
    @Value("${expired}")
    private Long EXPIRED ;

    @Value("${secret_key}")
    private String SECRET_KEY;
    private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(HotelUserDetails userPrincipal){
        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+EXPIRED) )
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
    public Boolean validate(String token){

        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true ;
        }catch (ExpiredJwtException expiredJwtException){
            logger.error("Expired Token {}", expiredJwtException.getMessage());
        }catch (SignatureException signatureException){
            logger.error("Invalid Signature Token {}", signatureException.getMessage());
        }catch (MalformedJwtException malformedJwtException){
            logger.error("Invalid format {}", malformedJwtException.getMessage());
        }catch (UnsupportedJwtException unsupportedJwtException){
            logger.error("UnSupported token {}",unsupportedJwtException.getMessage());
        }
        return false ;

    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
