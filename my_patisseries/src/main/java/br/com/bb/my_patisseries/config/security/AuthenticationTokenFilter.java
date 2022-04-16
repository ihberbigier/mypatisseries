package br.com.bb.my_patisseries.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.bb.my_patisseries.model.User;
import br.com.bb.my_patisseries.repository.UserRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private TokenServie tokenServie;
	
	private UserRepository userRepository;
	
	public AuthenticationTokenFilter(TokenServie tokenServie,UserRepository userRepository) {
		this.tokenServie = tokenServie;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToke(request);
		boolean tokenValidated = tokenServie.validToken(token);
		if(tokenValidated) {
			this.clientAuthenticate(token);
		}
		filterChain.doFilter(request, response);
		
	}

	private String getToke(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer")) {
			return null;
		}
		return token.substring(7,token.length());
	}
	
	private void clientAuthenticate(String token) {
		String username = tokenServie.getIdUser(token);
		Optional<User> user = userRepository.findById(username);
		if(user.isPresent()) {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
	}
	
}
