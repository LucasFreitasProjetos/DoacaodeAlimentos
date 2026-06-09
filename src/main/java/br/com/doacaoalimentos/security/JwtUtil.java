package br.com.doacaoalimentos.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    private static final String SECRET_KEY = "minha_chave_secreta_super_longa_para_jwt_autenticacao_2026";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas em ms
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Gera um token JWT para um usuário
     */
    public static String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida um token JWT e retorna o email do usuário
     */
    public static String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai o email do token
     */
    public static String getEmailFromToken(String token) {
        return validateToken(token);
    }
}
