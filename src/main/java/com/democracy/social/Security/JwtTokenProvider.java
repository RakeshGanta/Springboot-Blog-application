package com.democracy.social.Security;

import com.democracy.social.exceptions.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String JwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date currentDate =new Date();
        Date expireDate=new Date(currentDate.getTime()+jwtExpiration);

        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,JwtSecretKey)
                .compact();
        return token;

    }
    public String getUsernameFromJwt(String token){

        Claims claims=Jwts.parser().setSigningKey(JwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Expired Jwt token");
        } catch (UnsupportedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported Jwt token");
        } catch (MalformedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        } catch (SignatureException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }
    }
}
