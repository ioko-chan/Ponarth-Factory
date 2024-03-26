package com.solomennicova.AuthTemplate.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.solomennicova.AuthTemplate.Dto.Authentication.PayloadDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.TokensDto;
import com.solomennicova.AuthTemplate.Entity.Role;
import com.solomennicova.AuthTemplate.Entity.User;
import com.solomennicova.AuthTemplate.Exception.IncorrectTokenException;
import com.solomennicova.AuthTemplate.Exception.UserDeletedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class AuthProvider {

    @Value("${jwt_secret}")
    private String secretKey;

    private final UserDetailsServiceImpl userDetailsService;

    public AuthProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public TokensDto generateTokens(User user) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        String accessToken = JWT.create()
                .withIssuer("AuthTemplate")
                .withSubject("User")
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).toList())
                .sign(Algorithm.HMAC256(secretKey));

        return new TokensDto(accessToken);
    }

    private DecodedJWT decodeToken(String token) throws IncorrectTokenException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("AuthTemplate")
                .withSubject("User")
                .build();
        try {
            return verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new IncorrectTokenException("Token Expired");
        } catch (Exception e) {
            throw new IncorrectTokenException("Invalid Token");
        }
    }

    public PayloadDto getPayload(HttpServletRequest request) throws IncorrectTokenException, UserDeletedException {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        DecodedJWT decodedJWT = decodeToken(token);
        PayloadDto info = new PayloadDto();
        String username = decodedJWT.getClaim("username").asString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!userDetails.isEnabled()){
            throw new UserDeletedException("User deleted");
        }

        info.setUsername(username);
        info.setRoles(decodedJWT.getClaim("roles").asList(String.class));
        return info;
    }

    @Transactional
    public Authentication validateToken(String token) throws IncorrectTokenException, UserDeletedException {
        DecodedJWT decodedJWT = decodeToken(token);
        String username = decodedJWT.getClaim("username").asString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!userDetails.isEnabled()){
            throw new UserDeletedException("User deleted");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

}
