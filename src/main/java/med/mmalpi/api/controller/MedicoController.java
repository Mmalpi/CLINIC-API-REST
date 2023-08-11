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

import jakarta.validation.Valid;
import med.mmalpi.api.medico.DatosListadoMedico;
import med.mmalpi.api.medico.DatosRegistroMedico;
import med.mmalpi.api.medico.Medico;
import med.mmalpi.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medico") //endpoint
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping
	public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
		medicoRepository.save(new Medico(datosRegistroMedico));
	}
	
	@GetMapping
	public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
		return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
	}
	
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

