package fly.kasane.banana.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class JwtTokenUtil implements Serializable {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLES = "roles";

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 解析token，从token中获取信息
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
        Claims clamis;
        try {
            clamis = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        }catch (Exception exception){
            exception.printStackTrace();;
            clamis = null;
        }
        return clamis;
    }
}
