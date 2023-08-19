package med.mmalpi.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.mmalpi.api.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		//obtener token de los headers
		var authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			var token = authHeader.replace("Bearer", "");
			var subject = tokenService.getSubject(token);
			if (subject != null) {
				var usuario = usuarioRepository.findByLoginName(subject);
				var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());//forced login
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}
