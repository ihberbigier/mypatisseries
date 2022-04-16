package br.com.bb.my_patisseries.config.security;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bb.my_patisseries.model.dto.LoginDTO;
import br.com.bb.my_patisseries.model.dto.TokenDTO;

@RestController
@RequestMapping("/auth")
@Profile("PRO")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenServie tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid LoginDTO loginDTO) throws AuthenticationException {
		Authentication auth = authenticationManager.authenticate(loginDTO.toAuth());
		String token = tokenService.getToken(auth);
		
		return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
	
	}

}
