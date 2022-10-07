package com.winterhold.utility;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtToken {

    private final String SECRET_KEY = "aku-mencintaimu-walaupun-kamu-selalu-menyakiti-aku-berkali-kali";
    private final String AUDIENCE = "WinterholdMobile";

    //Claims nama lain dari Payload (Note : Info Payload dalam class Claims di Java)
    private Claims getClaims(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signatureAndClass = parser.parseClaimsJws(token);
        Claims claims = signatureAndClass.getBody();
        return claims;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        String username = claims.get("username", String.class);
        return username;
    }

    /*
        subject : bisa mengenai tujuan apa saja mengenai tujuan requaest ke REST API kita dari client-side lain.
        username : username pengguna client-sidenya(user yang login di mobile misalnya)
        secretKey : secret key yang diberikan oleh aplikasi client-side lainna.
        role : role akan kita cari dari database berdasarkan usernamenya.
        audience : identitas pihak aplikasi client-side yang merequest ,dalam hal ini BasiliskMobile
    */
    public String generateToken(String subject,
                                String username,
                                String secretKey,
                                String audience) {
        JwtBuilder builder = Jwts.builder();
        builder = builder.setSubject(subject)
                .claim("username", username)
                .setIssuer("http://localhost:7700")
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        String token = builder.compact();
        return token;
    }

    //mem-validasi apakah token benar atau gak
    public Boolean validateToken(String token, UserDetails userDetails){
        Claims claims = getClaims(token);
        String username = getUsername(token);
        String audience = claims.getAudience();
        Boolean isMatch = (username.equals(userDetails.getUsername()) && audience.equals(AUDIENCE));
        return isMatch;
    }
}
