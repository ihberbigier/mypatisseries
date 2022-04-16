package br.com.bb.my_patisseries.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import br.com.bb.my_patisseries.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenServie {

	@Value("${myptisseries.security.user.expiration}")
	private String expiration;
	
	@Value("${mypatisseries.security.user.gen.token}")
	private String pass;
	
	
	public String getToken(Authentication auth) {
		User userLogged = (User) auth.getPrincipal();
		Date now = new Date();
		Date expiationDate = new Date(now.getTime()+ Integer.valueOf(expiration));
		
		return Jwts.builder()
				.setIssuer("API My patisseries")
				.setSubject(userLogged.getUsername())
				.setIssuedAt(expiationDate)
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, pass)
				.compact();
		
	}


	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.pass).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	
	public String getIdUser(String token) {
		 Claims claim = Jwts.parser().setSigningKey(this.pass).parseClaimsJws(token).getBody();
		 return  claim.getSubject();
		
	}
}
