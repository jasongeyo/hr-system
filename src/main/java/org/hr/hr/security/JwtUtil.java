package org.hr.hr.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long EXPIRATION_MS = 1000 * 60 * 60; // 1小時

    // 取得金鑰物件
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 製作識別證
    public String generateToken(String username) {
        return Jwts.builder()   //建立token
                .subject(username)  //塞 username
                .issuedAt(new Date())   //塞發行時間
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))   //塞過期時間
                .signWith(getSigningKey())  //壓印章
                .compact()     //產出字串
                ;
    }

    // 從識別證取出使用者名稱
    public Claims extractAllClaims(String token) {
        return Jwts.parser()    //解析token
                .verifyWith(getSigningKey()) //用哪把印章驗 (新版改用 verifyWith)
                .build()
                .parseSignedClaims(token)    // 實際驗證並解開
                .getPayload();               // 取出資料
    }

    // 抓出資訊
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //  驗證識別證
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
