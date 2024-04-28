package uz.pdp.shippingservice.config.jwtConfig;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.exception.RefreshTokeNotFound;
import uz.pdp.shippingservice.exception.TimeExceededException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.repository.UserRepository;

import java.util.Date;
import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class JwtGenerate {
    private final UserRepository userRepository;

    private  final String JWT_ACCESS_KEY = "404E635266556A586E327235753878F413F4428472B4B6250645367566B5970";
    private  final String JWT_REFRESH_KEY = "404E635266556A586E327235753878F413F4428472B4B6250645lll367566B5970";
    private  final long accessTokenLiveTime = 1000 * 60 * 60 * 3;//*100000;
    private  final long reFreshTokenLiveTime = 1000 * 60 * 60 * 5;//* 60 * 60 * 24;

    public  synchronized String generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_KEY)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenLiveTime))
                .claim(AUTHORITIES, userEntity.getAuthorities())
                .compact();
    }

    public  synchronized String generateRefreshToken(UserEntity userEntity) {
        return REFRESH_TOKEN + Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_KEY)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + reFreshTokenLiveTime))
                .compact();
    }
    public  synchronized boolean isValidAccessToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(JWT_ACCESS_KEY).parseClaimsJws(token).getBody();
            return body != null;
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
//            throw new TimeExceededException(TOKEN_TIME_OUT);
            e.printStackTrace();
        }
        return false;
    }

    public  synchronized Claims isValidRefreshToken(String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_REFRESH_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new TimeExceededException(REFRESH_TOKEN_TIME_OUT);
        }
    }

    public String checkRefreshTokenValidAndGetAccessToken(HttpServletRequest request) throws Exception {
        String requestHeader = request.getHeader(AUTHORIZATION);
        if (requestHeader == null || !requestHeader.startsWith(REFRESH_TOKEN)) {
            throw new RefreshTokeNotFound(REFRESH_TOKEN_NOT_FOUND);
        }
        String token = requestHeader.replace(REFRESH_TOKEN, "");
        Claims claims = isValidRefreshToken(token);
        if (claims == null) {
            throw new Exception();
        }
        UserEntity userEntity = userRepository.findByUserName(claims.getSubject()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return generateAccessToken(userEntity);
    }

    public    String getUserNameFromAccessToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(JWT_ACCESS_KEY).parseClaimsJws(token).getBody();
            return body.getSubject();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new TimeExceededException(TOKEN_TIME_OUT);
        }
    }

    public   String getUserNameFromRefreshToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(JWT_REFRESH_KEY).parseClaimsJws(token).getBody();
            return body.getSubject();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            throw new TimeExceededException(REFRESH_TOKEN_TIME_OUT);
        }
    }
}
