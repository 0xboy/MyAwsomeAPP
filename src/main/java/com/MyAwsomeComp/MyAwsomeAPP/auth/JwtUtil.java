package com.MyAwsomeComp.MyAwsomeAPP.auth;

import com.MyAwsomeComp.MyAwsomeAPP.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String secret_key = "0xb0y";
    private long accessTokenValidity = 60*60*100;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date tokenCreateTime = new Date();
        Date tokenValidityTime = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidityTime)
                .signWith(SignatureAlgorithm.HS256,secret_key)
                .compact();
    }

    public Claims parseJwtClaims(String token){

        var a = jwtParser.parseClaimsJws(token).getBody();
        return a;
    }

    public Claims resolveClaims(HttpServletRequest req){
        try{
            String token = resolveToken(req);
            if (token != null){
                return parseJwtClaims(token);
            }
            return null;
        }
        catch(ExpiredJwtException ex){
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        }
        catch(Exception ex){
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException{
        try{
            return claims.getExpiration().after(new Date());
        }
        catch (Exception e){
            throw e;
        }

    }

    public String getUsername(Claims claims){
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims){
        return (List<String>) claims.get("roles");
    }

}
