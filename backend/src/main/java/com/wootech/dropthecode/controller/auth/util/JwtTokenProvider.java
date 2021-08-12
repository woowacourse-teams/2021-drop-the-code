package com.wootech.dropthecode.controller.auth.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

import com.wootech.dropthecode.exception.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtTokenProvider {
    @Value("${jwt.access-token.expire-length:10000}")
    private long accessTokenValidityInMilliseconds;
    @Value("${jwt.refresh-token.expire-length:10000}")
    private long refreshTokenValidityInMilliseconds;
    @Value("${jwt.token.secret-key:secret-key}")
    private String secretKey;

    public String createAccessToken(String payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    /**
     * COMMENT
     * 동시에 리프레시 토큰이 생성되는 경우, 가입한 유저의 리프레시 토큰이 우연히 같아질 수 있다.
     * (현재는 빈 문자열로 토큰을 만들기 때문에, 시간에만 영향받기 때문)
     * 유저의 정보를 포함하지 않으면서 동시에 가입하는 유저의 리프레시 토큰에 차이를 두기 위해 의미없는 랜덤 문자열을 생성하여 Payload로 주게 변경
     */
    public String createRefreshToken() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        return createToken(generatedString, refreshTokenValidityInMilliseconds);
    }

    public String createToken(String payload, long expireLength) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(validity)
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }

    public String getPayload(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
