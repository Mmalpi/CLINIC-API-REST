package med.mmalpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.mmalpi.api.usuario.DatosAutenticacionUsuario;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacion) {
		
		Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticacion.login(), datosAutenticacion.clave());
		authenticationManager.authenticate(token);
		return ResponseEntity.ok().build();
	}
}