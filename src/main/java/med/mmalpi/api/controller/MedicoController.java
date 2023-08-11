package med.mmalpi.api.controller;

import java.awt.print.Pageable;
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
	public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
		medicoRepository.save(new Medico(datosRegistroMedico));
	}
	
	@GetMapping("/listado")
	public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
		//return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
		
		//customQuery
		return medicoRepository.findByStatusTrue(paginacion).map(DatosListadoMedico::new);
	}
	
	@PutMapping("/actualizacion")
	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
	public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosAcutalizarMedico) {
		Medico medico = medicoRepository.getReferenceById(datosAcutalizarMedico.id());
		medico.actualizarDatos(datosAcutalizarMedico);
	}
	
	//logic delete
	@DeleteMapping("/{id}") //dinamic variable
	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
	public void eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.desactivarMedico();
	} 
	
	//perma delete
//	@DeleteMapping("/{id}") //dinamic variable
//	@Transactional //necesary for commit the transaction to the db for the whole method and if an error exist, a rollback happens
//	public void eliminarMedico(@PathVariable Long id) {
//		Medico medico = medicoRepository.getReferenceById(id);
//		medicoRepository.delete(medico);
//	}
	
	 // Manejo de excepciones para datos de entrada no válidos personalizado //custom error handler
	//ejemplo // example 
	// "message": "nombre: must not be blank; email: must be a well-formed email address; telefono: must match \"^[0-9]{7,15}$\"; documento: must match \"\\d{4,6}\"; direccion.calle: must not be blank;"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }
}

