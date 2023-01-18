package com.revature.Utility;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtility {


    @Value("${jwt.secret}")
    String secret;
    byte[] bytes;
    UserRepository userRepository;

    @Autowired
    public JWTUtility(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createBytes(){
        bytes = Base64
                .getEncoder().encode(Base64.getEncoder()
                .encode(secret.getBytes()));

    }

    public byte[] getBytes() {
        if(bytes==null) {
            createBytes();
        }
        return bytes;
    }

    public String createToken(User user) {
        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(user.getEmail())
                .setSubject(user.getEmail())
                .setIssuer("carrier-pigeon-ultra")
                .claim("email", user.getEmail())
                .claim("user_id",user.getId())
                .setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() +60 * 60 * 1000*2))
                .signWith(new SecretKeySpec(getBytes(),"HmacSHA256"));
        return tokenBuilder.compact();
    }


    public boolean isTokenValid(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        } else {
            return parseToken(token).isPresent();
        }
    }

    private Optional<User> parseToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(bytes)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return userRepository.findById((Integer) claims.get("user_id"));
    }

    public User extractTokenDetails(String token) throws Exception {
        if (! isTokenValid(token)) {
            throw new Exception("Bad token");
        } else {
            return parseToken(token).orElse(null);
        }
    }

}
