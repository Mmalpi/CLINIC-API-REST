package med.mmalpi.api.controller;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import med.mmalpi.api.medico.DatosActualizarMedico;
import med.mmalpi.api.medico.DatosListadoMedico;
import med.mmalpi.api.medico.DatosRegistroMedico;
import med.mmalpi.api.medico.Medico;
import med.mmalpi.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medico") //endpoint
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping("/guardarMedico")
	public ResponseEntity<Medico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
		Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
		//return 201 created
		// URL where to find the resource 
		URI url = uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(medico);
	}
	
	@GetMapping("/listado")
	public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
		//return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
		
		//customQuery								find by "custom" 
		return ResponseEntity.ok(medicoRepository.findByStatusTrue(paginacion).map(DatosListadoMedico::new));
	}
	
	@PutMapping("/actualizacion")
	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
	public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosAcutalizarMedico) {
		Medico medico = medicoRepository.getReferenceById(datosAcutalizarMedico.id());
		medico.actualizarDatos(datosAcutalizarMedico);
		return ResponseEntity.ok(medico);
	}
	
	//logic delete
	@DeleteMapping("/{id}") //dinamic variable
	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
	public ResponseEntity eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.desactivarMedico();
		return ResponseEntity.noContent().build();
	} 
	
	//perma delete
//	@DeleteMapping("/{id}") //dinamic variable
//	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
//	public void eliminarMedico(@PathVariable Long id) {
//		Medico medico = medicoRepository.getReferenceById(id);
//		medicoRepository.delete(medico);
//	}
	
	@GetMapping("/{id}") //dinamic variable
	public ResponseEntity<Medico>  retornaMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		return ResponseEntity.ok(medico);
	} 
	
}

