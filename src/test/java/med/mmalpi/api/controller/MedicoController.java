package med.mmalpi.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.mmalpi.api.medico.DatosRegistroMedico;

@RestController
@RequestMapping("/medicos") //endpoint
public class MedicoController {

	@PostMapping
	public void registrarMedico(@RequestBody DatosRegistroMedico datosRegistroMedico) {
		System.out.println("El request llega corretametne");
		
		
	}
	
}
